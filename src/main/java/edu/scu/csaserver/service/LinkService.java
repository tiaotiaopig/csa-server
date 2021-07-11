package edu.scu.csaserver.service;

import edu.scu.csaserver.domain.Link;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface LinkService extends IService<Link> {
    List<Link> getLinksByNodeId(List<Integer> nodes);
}
