package edu.scu.csaserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.scu.csaserver.domain.ServiceNet;
import edu.scu.csaserver.service.ServiceNetService;
import edu.scu.csaserver.mapper.ServiceNetMapper;
import edu.scu.csaserver.domain.vo.Count;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class ServiceNetServiceImpl extends ServiceImpl<ServiceNetMapper, ServiceNet>
implements ServiceNetService{

    @Autowired
    private ServiceNetMapper serviceNetMapper;
    @Override
    public List<Count> portCount() {
        return serviceNetMapper.getByServicePortCount();
    }

    @Override
    public List<Count> safetyCount() {
        return serviceNetMapper.getNodeSafetyCount();
    }
}




