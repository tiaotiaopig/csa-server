package edu.scu.csaserver.service;

import edu.scu.csaserver.domain.NodeService;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.scu.csaserver.domain.vo.Count;
import edu.scu.csaserver.domain.vo.ServiceCount;

import java.util.List;

/**
 * 我们有个实体类也叫 service
 * 导致这个类名起的非常尴尬
 */
public interface NodeServiceService extends IService<NodeService> {

    /**
     * 对节点服务的一些统计数据
     * @return
     */
    List<ServiceCount> serviceCount();

    /**
     * 对所有节点运行服务的漏洞统计
     * @return
     */
    List<Count> serviceVulCount();
}
