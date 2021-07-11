package edu.scu.csaserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.scu.csaserver.domain.Link;
import edu.scu.csaserver.mapper.NodeMapper;
import edu.scu.csaserver.service.LinkService;
import edu.scu.csaserver.mapper.LinkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 *
 */
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link>
implements LinkService{

    @Autowired
    private LinkMapper linkMapper;

    @Override
    public List<Link> getLinksByNodeId(List<Integer> nodes) {

        List<Link> result = new ArrayList<>();
        List<Link> links = linkMapper.selectList(null);
        for (Link link : links) {
            if (nodes.contains(link.getSourceNodeId()) &&
            nodes.contains(link.getTargetNodeId())) result.add(link);
        }
        return result;
    }
}




