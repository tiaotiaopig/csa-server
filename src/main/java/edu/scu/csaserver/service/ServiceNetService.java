package edu.scu.csaserver.service;

import edu.scu.csaserver.domain.ServiceNet;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.scu.csaserver.domain.vo.Count;

import java.util.List;

/**
 *
 */
public interface ServiceNetService extends IService<ServiceNet> {

    public List<Count> portCount();

    List<Count> safetyCount();
}
