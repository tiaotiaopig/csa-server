package edu.scu.csaserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.scu.csaserver.domain.NodeService;
import edu.scu.csaserver.service.NodeServiceService;
import edu.scu.csaserver.mapper.NodeServiceMapper;
import edu.scu.csaserver.domain.vo.Count;
import edu.scu.csaserver.domain.vo.ServiceCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 节点和服务关系
 */
@Service
public class NodeServiceServiceImpl extends ServiceImpl<NodeServiceMapper, NodeService>
implements NodeServiceService{

    private final NodeServiceMapper nSMapper;

    @Autowired
    public NodeServiceServiceImpl (NodeServiceMapper nSMapper) {
        this.nSMapper = nSMapper;
    }

    @Override
    public List<ServiceCount> serviceCount() {
        return nSMapper.serviceCount();
    }

    @Override
    public List<Count> serviceVulCount() {
        return nSMapper.serviceVulCount();
    }
}




