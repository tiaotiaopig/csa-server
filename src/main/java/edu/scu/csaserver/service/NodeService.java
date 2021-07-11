package edu.scu.csaserver.service;

import edu.scu.csaserver.domain.Node;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface NodeService extends IService<Node> {
    public List<Node> getNodePage(Integer page, Integer limit);
}
