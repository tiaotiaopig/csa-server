package edu.scu.csaserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.scu.csaserver.domain.Link;
import edu.scu.csaserver.service.LinkService;
import edu.scu.csaserver.mapper.LinkMapper;
import edu.scu.csaserver.utils.KeyNode;
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
        return id == linkMapper.deleteById(id);
    }
}




