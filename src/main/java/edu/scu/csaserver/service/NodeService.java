package edu.scu.csaserver.service;

import edu.scu.csaserver.domain.Node;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.scu.csaserver.vo.NodeInfo;
import edu.scu.csaserver.vo.NodeList;

import java.util.List;

/**
 *
 */
public interface NodeService extends IService<Node> {
    List<NodeInfo> getNodePage(Integer page, Integer limit);
}
