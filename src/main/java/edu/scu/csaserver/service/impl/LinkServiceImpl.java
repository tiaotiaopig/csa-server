package edu.scu.csaserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.scu.csaserver.cachedao.GraphCacheDAO;
import edu.scu.csaserver.domain.Link;
import edu.scu.csaserver.domain.SubNetworkLink;
import edu.scu.csaserver.mapper.SubNetworkLinkMapper;
import edu.scu.csaserver.service.LinkService;
import edu.scu.csaserver.mapper.LinkMapper;
import edu.scu.csaserver.utils.LinkPredictionUtil;
import edu.scu.csaserver.domain.vo.LinkInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * mp 自动生成的
 * 主要是连接相关操作
 */
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link>
implements LinkService{

    @Autowired
    private LinkMapper linkMapper;
    @Autowired
    private SubNetworkLinkMapper subNetworkLinkMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private GraphCacheDAO graphCacheDAO;

    @Override
    public List<LinkInfo> getLinksByNodeId(List<Integer> nodes) {

        List<LinkInfo> result = new ArrayList<>();
//        List<Link> links = linkMapper.selectList(null);
//        for (Link link : links) {
//            if (nodes.contains(link.getSourceNodeId()) &&
//            nodes.contains(link.getTargetNodeId())) {
//                LinkInfo linkInfo = new LinkInfo();
//                linkInfo.setSource("节点" + link.getSourceNodeId());
//                linkInfo.setTarget("节点" + link.getTargetNodeId());
//                linkInfo.setLink(link);
//                result.add(linkInfo);
//            }
//        }

        List<Link> related = getRelatedLinks(nodes);
        for (Link link : related) {
            LinkInfo linkInfo = new LinkInfo();
            linkInfo.setSource("节点" + link.getSourceNodeId());
            linkInfo.setTarget("节点" + link.getTargetNodeId());
            linkInfo.setLink(link);
            result.add(linkInfo);
        }
        return result;
    }

    @Override
    public List<Link> getRelatedLinks(List<Integer> nodes) {
        List<Link> result = new ArrayList<>();
        List<Link> links;
        if (Boolean.TRUE.equals(redisTemplate.opsForValue().getOperations().hasKey("links"))) {
            links = (List<Link>) redisTemplate.opsForValue().get("links");
        } else {
            links = linkMapper.selectList(null);
            redisTemplate.opsForValue().set("links", links);
            redisTemplate.expire("links", 300, TimeUnit.SECONDS);
        }
        for (Link link : links) {
            if (nodes.contains(link.getSourceNodeId()) &&
                    nodes.contains(link.getTargetNodeId())) {
                result.add(link);
            }
        }
        return result;
    }

    @Override
    public Boolean deleteLinkById(Integer id) {
        QueryWrapper<SubNetworkLink> query = new QueryWrapper<>();
        query.eq("link_id", id);
        subNetworkLinkMapper.delete(query);
        return 1 == linkMapper.deleteById(id);
    }

    @Override
    public List<LinkInfo> getLinkPage(Integer page, Integer limit) {
        // 获取分页查询结果
        page = (page - 1) * limit;
        List<Link> links = linkMapper.getLinkPage(page, limit);
        // 填充源节点和目标节点
        List<LinkInfo> result = new ArrayList<>(links.size());
        for (Link link : links) {
            LinkInfo linkInfo = new LinkInfo();
            linkInfo.setSource("节点" + link.getSourceNodeId());
            linkInfo.setTarget("节点" + link.getTargetNodeId());
            linkInfo.setLink(link);
            result.add(linkInfo);
        }
        return result;
    }

    @Override
    public Boolean addLink(Link link, Integer subId) {
        if (link != null) {
            if (1 == linkMapper.insert(link)) {
                // 创建子网连接关系
                return 1 == subNetworkLinkMapper.insert(new SubNetworkLink(subId, linkMapper.getNodeAutoIncrement()));
            }
        }
        return false;
    }

    /**
     * 根据方法名和文件名,调用对应的链路预测算法,返回10%预测为存在的边
     * 默认的掩盖比例是50%,现已废弃
     * @param func 要调用的方法名
     * @param filename 拓扑图的名称
     * @return 10%预测为存在的边
     */
    @Override
    @Deprecated
    public List<LinkInfo> linkPredictByFunc(String func, String filename) {
        List<LinkInfo> links;
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        if (hash.hasKey(func, filename)) {
            links = (List<LinkInfo>) hash.get(func, filename);
        } else {
            List<String> exist = LinkPredictionUtil.linkPrediction(func, filename);
            int len = exist.size();
            links = new ArrayList<>(len / 3);
            for (int index = 0; index < len; index += 3) {
                LinkInfo linkInfo = new LinkInfo();
                linkInfo.setSource("节点" + exist.get(index));
                linkInfo.setTarget("节点" + exist.get(index + 1));
                linkInfo.setWeight(exist.get(index + 2));
                links.add(linkInfo);
            }
            hash.put(func, filename, links);
        }
        return links;
    }

    /**
     * 对指定的名称的图,使用指定的掩盖比例进行隐藏,并返回掩盖的边
     * @param filename 图名称
     * @param ratio 掩盖比例
     * @return
     */
    @Override
    public List<LinkInfo> getMasked(String ratio, String filename) {
        List<LinkInfo> maskedLinks;
        if ((maskedLinks = graphCacheDAO.getMaskedLinks(ratio, filename)) == null) {
            maskedLinks = getMaskedByOp(filename, ratio);
            graphCacheDAO.setMaskedLinks(ratio, filename, maskedLinks);
        }
        return maskedLinks;
    }

    private List<LinkInfo> getMaskedByOp(String ratio, String filename) {
        List<LinkInfo> list = new ArrayList<>();
        List<String> masked = LinkPredictionUtil.getMasked(filename, ratio);

        int len = masked.size();
        for (int index = 0; index < len; index += 2) {
            LinkInfo linkInfo = new LinkInfo();
            linkInfo.setSource("节点" + masked.get(index));
            linkInfo.setTarget("节点" + masked.get(index + 1));
            linkInfo.setWeight("0");
            list.add(linkInfo);
        }
        return list;
    }

    @Override
    public Map<String, Object> getPrediction(String filename, String ratio, String funcName) {
        // 不想封装啦，直接用Map得了
        Map<String, Object> predictRes;
        if ((predictRes = graphCacheDAO.getPredictCache(ratio, filename, funcName)) == null) {
            predictRes = getPredictionByOp(filename, ratio, funcName);
            graphCacheDAO.setPredictCache(ratio, filename, funcName, predictRes);
        }
        return predictRes;
    }

    private Map<String, Object> getPredictionByOp(String filename, String ratio, String funcName) {
        Map<String, Object> res = new HashMap<>();
        List<LinkInfo> list = new ArrayList<>();
        List<String> predicted = LinkPredictionUtil.getPrediction(filename, ratio, funcName);
        if (predicted.size() == 0) return null;
        // 因为最后两个元素是auc和ap
        int len = predicted.size() - 2;
        for (int index = 0; index < len; index += 3) {
            LinkInfo linkInfo = new LinkInfo();
            linkInfo.setSource("节点" + predicted.get(index));
            linkInfo.setTarget("节点" + predicted.get(index + 1));
            linkInfo.setWeight(predicted.get(index + 2));
            list.add(linkInfo);
        }
        res.put("links", list);
        res.put("auc", predicted.get(len));
        res.put("ap", predicted.get(len + 1));
        return res;
    }
}
