package edu.scu.csaserver.service;

import edu.scu.csaserver.domain.Link;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.scu.csaserver.vo.LinkInfo;

import java.util.List;

/**
 *
 */
public interface LinkService extends IService<Link> {
    /**
     * 根据节点 ids 获取相关的边
     * 我们采用的是 并 运算
     * @param nodes
     * @return
     */
    List<LinkInfo> getLinksByNodeId(List<Integer> nodes);

    Boolean deleteLinkById(Integer id);
}
