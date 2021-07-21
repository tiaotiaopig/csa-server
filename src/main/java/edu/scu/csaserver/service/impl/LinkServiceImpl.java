package edu.scu.csaserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.scu.csaserver.domain.Link;
import edu.scu.csaserver.domain.SubNetworkLink;
import edu.scu.csaserver.mapper.SubNetworkLinkMapper;
import edu.scu.csaserver.service.LinkService;
import edu.scu.csaserver.mapper.LinkMapper;
import edu.scu.csaserver.vo.LinkInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<LinkInfo> getLinksByNodeId(List<Integer> nodes) {

        List<LinkInfo> result = new ArrayList<>();
        List<Link> links = linkMapper.selectList(null);
        for (Link link : links) {
            if (nodes.contains(link.getSourceNodeId()) &&
            nodes.contains(link.getTargetNodeId())) {
                LinkInfo linkInfo = new LinkInfo();
                linkInfo.setSource("节点" + link.getSourceNodeId());
                linkInfo.setTarget("节点" + link.getTargetNodeId());
                linkInfo.setLink(link);
                result.add(linkInfo);
            }
        }
        return result;
    }

    @Override
    public Boolean deleteLinkById(Integer id) {
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
    public Boolean addLink(Link link) {
        if (link != null) {
            if (1 == linkMapper.insert(link)) {
                // 创建子网连接关系
                return 1 == subNetworkLinkMapper.insert(new SubNetworkLink(1, linkMapper.getNodeAutoIncrement()));
            }
        }
        return false;
    }


}




