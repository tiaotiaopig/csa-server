package edu.scu.csaserver.service;

import edu.scu.csaserver.domain.Node;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.scu.csaserver.utils.KeyNodePath;
import edu.scu.csaserver.vo.Count;
import edu.scu.csaserver.vo.NodeInfo;
import edu.scu.csaserver.vo.NodeList;
import edu.scu.csaserver.vo.ServiceVul;
import io.swagger.models.auth.In;

import java.util.HashMap;
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

    /**
     * 选择某种关键节点算法，对某张图进行关键节点检测
     * @param func 方法名称
     * @param path 拓扑图路径
     * @return 关键节点id列表
     */
    List<Integer> keyNode2(String func, String path);

    /**
     * 选择某种关键节点算法，对某张图进行关键节点检测
     * @param func 方法名称
     * @param path 拓扑图路径
     * @return 关键节点id和对应的权值
     */
    HashMap<Integer, Double> keyNode(String func, String path);

    /**
     * 返回某张图的节点详情分页结果
     * 这里直接 mock,脏活累活都是我，天哪
     * @param page 第几页
     * @param limit 多少行
     * @param filepath 图名称
     * @return
     */
    List<NodeInfo> getNodePageBy(Integer page, Integer limit, String filepath);

    /**
     * 根据文件名获取节点数
     * @param path 文件名
     * @return 节点数
     */
    Integer getNodeCountBy(String path);

}
