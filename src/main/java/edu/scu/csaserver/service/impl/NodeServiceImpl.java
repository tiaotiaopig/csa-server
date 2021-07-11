package edu.scu.csaserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.scu.csaserver.domain.Node;
import edu.scu.csaserver.service.NodeService;
import edu.scu.csaserver.mapper.NodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class NodeServiceImpl extends ServiceImpl<NodeMapper, Node>
implements NodeService{

    @Autowired
    private NodeMapper nodeMapper;

    @Override
    public List<Node> getNodePage(Integer page, Integer limit) {
        page = (page - 1) * limit;
        return nodeMapper.getNodePage(page, limit);
    }
}




