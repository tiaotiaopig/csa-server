package edu.scu.csaserver.mapper;

import edu.scu.csaserver.domain.ServiceNet;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.scu.csaserver.vo.Count;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Entity edu.scu.csaserver.domain.ServiceNet
 */
@Repository
public interface ServiceNetMapper extends BaseMapper<ServiceNet> {

    List<Count> getByServicePortCount();

    List<Count> getNodeSafetyCount();

}




