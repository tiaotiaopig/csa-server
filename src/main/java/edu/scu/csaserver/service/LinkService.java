package edu.scu.csaserver.service;

import edu.scu.csaserver.domain.Link;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.scu.csaserver.vo.LinkInfo;

import java.util.List;

/**
 *
 */
public interface LinkService extends IService<Link> {
    List<LinkInfo> getLinksByNodeId(List<Integer> nodes);
}
