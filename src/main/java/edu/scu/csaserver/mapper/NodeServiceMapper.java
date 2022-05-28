package edu.scu.csaserver.mapper;

import edu.scu.csaserver.domain.NodeService;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.scu.csaserver.domain.vo.Count;
import edu.scu.csaserver.domain.vo.ServiceCount;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Entity edu.scu.csaserver.domain.NodeService
 */
@Repository
public interface NodeServiceMapper extends BaseMapper<NodeService> {

    List<ServiceCount> serviceCount();

    List<Count> serviceVulCount();
}




