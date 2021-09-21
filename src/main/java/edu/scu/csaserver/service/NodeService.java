package edu.scu.csaserver.service;

import edu.scu.csaserver.domain.Node;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.scu.csaserver.utils.KeyNodePath;
import edu.scu.csaserver.vo.Count;
import edu.scu.csaserver.vo.NodeInfo;
import edu.scu.csaserver.vo.NodeList;
import edu.scu.csaserver.vo.ServiceVul;

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

    /**
     * 根据节点 id 删除节点
     * @param id 带删除节点id
     * @return 是否删除成功
     */
    Boolean deleteNodeById(Integer id);

    /**
     * 添加节点
     * @param subId
     * @param node
     */
    void addNode(Integer subId, Node node);

    /**
     * 所有节点物理类型统计
     * @return
     */
    List<Count> physicalCount();

    /**
     * 所有节点逻辑类型统计
     * @return
     */
    List<Count> logicalCount();

    /**
     * 所有节点
     * @return
     */
    List<Count> serviceVulCount();

    /**
     * 根据安全等级获取节点
     * @param safety
     * @return
     */
    List<Node> getNodeBySafety(Integer safety);

    /**
     * 获取节点上所有服务漏洞数
     * @return
     */
    ServiceVul nodeServiceVulNum();

    /**
     * 我们需要读取数据库的边数据，然后对 KeyNodePath
     * @return
     */
    KeyNodePath getKeyNodePath();
}
