package edu.scu.csaserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.scu.csaserver.domain.Link;
import edu.scu.csaserver.domain.SubNetworkLink;
import edu.scu.csaserver.mapper.SubNetworkLinkMapper;
import edu.scu.csaserver.service.LinkService;
import edu.scu.csaserver.mapper.LinkMapper;
import edu.scu.csaserver.utils.LinkPredictionUtil;
import edu.scu.csaserver.vo.LinkInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
     *
     * @param func     要调用的方法名
     * @param filename 拓扑图的名称
     * @return 10%预测为存在的边
     */
    @Override
    public List<LinkInfo> linkPredictByFunc(String func, String filename) {
        List<LinkInfo> links;
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        if (hash.hasKey(func, filename)) {
            links = (List<LinkInfo>) hash.get(func, filename);
        } else {
            List<Integer> exist = LinkPredictionUtil.linkPrediction(func, filename);
            int len = exist.size();
            links = new ArrayList<>(len / 2);
            for (int index = 0; index < len; index += 2) {
                LinkInfo linkInfo = new LinkInfo();
                linkInfo.setSource("节点" + exist.get(index));
                linkInfo.setTarget("节点" + exist.get(index + 1));
                links.add(linkInfo);
            }
            hash.put(func, filename, links);
        }
        return links;
    }


}




