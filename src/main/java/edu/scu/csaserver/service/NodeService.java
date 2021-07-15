package edu.scu.csaserver.service;

import edu.scu.csaserver.domain.Node;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.scu.csaserver.vo.NodeInfo;
import edu.scu.csaserver.vo.NodeList;

import java.util.List;

/**
 * 节点 服务层
 */
public interface NodeService extends IService<Node> {
    /**
     * 获取节点分页数据
     * @param page 当前页（1开始）
     * @param limit 每页节点数
     * @return
     */
    List<NodeInfo> getNodePage(Integer page, Integer limit);

    /**
     * 获取所以节点的漏洞总数
     * @return
     */
    Integer getNodeVulnerability();

    /**
     * 根据传入的节点id数组，调用关键节点算法，给出关键节点id
     * 由于获取关键节点的算法，仅仅是基于边的
     * 所以将实现方法放在这里，会更加合适
     * @param nodeIds
     * @return
     */
    List<Integer> getKeyNodeIds(List<Integer> nodeIds);
}
