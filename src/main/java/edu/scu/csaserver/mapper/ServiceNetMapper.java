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

    /**
     * 同一服务端口的节点统计
     * @return
     */
    List<Count> getByServicePortCount();

    /**
     * 服务安全等级的节点统计
     * @return
     */
    List<Count> getNodeSafetyCount();

}




