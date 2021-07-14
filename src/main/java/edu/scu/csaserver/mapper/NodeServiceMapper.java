package edu.scu.csaserver.mapper;

import edu.scu.csaserver.domain.NodeService;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.scu.csaserver.vo.ServiceCount;
import org.apache.ibatis.annotations.MapKey;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Entity edu.scu.csaserver.domain.NodeService
 */
@Repository
public interface NodeServiceMapper extends BaseMapper<NodeService> {
    public List<ServiceCount> serviceCount();
}




