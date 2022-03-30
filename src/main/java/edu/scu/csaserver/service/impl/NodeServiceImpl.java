package edu.scu.csaserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.scu.csaserver.domain.Link;
import edu.scu.csaserver.domain.Node;
import edu.scu.csaserver.domain.ServiceNet;
import edu.scu.csaserver.domain.SubNetworkNode;
import edu.scu.csaserver.mapper.LinkMapper;
import edu.scu.csaserver.mapper.ServiceNetMapper;
import edu.scu.csaserver.mapper.SubNetworkNodeMapper;
import edu.scu.csaserver.service.LinkService;
import edu.scu.csaserver.service.NodeService;
import edu.scu.csaserver.mapper.NodeMapper;
import edu.scu.csaserver.utils.KeyLink;
import edu.scu.csaserver.utils.KeyNode;
import edu.scu.csaserver.utils.KeyNodePath;
import edu.scu.csaserver.utils.KeyNodeUtil;
import edu.scu.csaserver.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 节点管理的 service 层
 *
 */
@Service
public class NodeServiceImpl extends ServiceImpl<NodeMapper, Node>
implements NodeService{

    @Autowired
    private NodeMapper nodeMapper;
    @Autowired
    private SubNetworkNodeMapper subNetworkNodeMapper;
    @Autowired
    private LinkMapper linkMapper;
    @Autowired
    private ServiceNetMapper serviceNetMapper;
    @Autowired
    private KeyNode keyNode;
    @Autowired
    private KeyLink keyLink;
    @Autowired
    private LinkService linkService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<NodeInfo> getNodePage(Integer page, Integer limit) {
        page = (page - 1) * limit;
        List<Node> nodePage = nodeMapper.getNodePage(page, limit);
        // 为了echarts绘图，封装了一下
        // 填充子网号
        List<NodeInfo> result = new ArrayList<>(nodePage.size());
        for (Node node : nodePage) {
            NodeInfo nodeInfo = new NodeInfo();
            nodeInfo.setName("节点" + node.getId());
            Integer subId = subNetworkNodeMapper.getSubByNodeId(node.getId());
            nodeInfo.setCategory("子网" + subId);
            nodeInfo.setNode(node);
            result.add(nodeInfo);
        }
        return result;
    }

    @Override
    public Integer getNodeVulnerability() {
        // 1. 创建QueryWrapper 对象
        QueryWrapper<Node> query = new QueryWrapper<>();
        // 2. 设置查询条件
        query.select("vulnerability_sum");
        // 3. 执行查询
        List<Node> nodes = nodeMapper.selectList(query);
        int sum = 0;
        for (Node node : nodes) {
            sum += node.getVulnerabilitySum();
        }
        return sum;
    }

    @Override
    public List<Integer> getKeyNodeIds(List<Integer> nodeIds) {
        List<Link> related = linkService.getRelatedLinks(nodeIds);
//        List<Link> links = linkMapper.selectList(null);
//        // 获取节点相关边
//        for (Link link : links) {
//            if (nodeIds.contains(link.getSourceNodeId()) &&
//                    nodeIds.contains(link.getTargetNodeId())) {
//                related.add(link);
//            }
//        }
        // 将相关边的连接关系取出
        // 封装成二维数组，供关键节点算法使用
        Link link;
        int len = related.size();
        int[][] linkArray = new int[len][2];
        for (int i = 0; i < len; i++) {
            // 这里用的是 related,不是links,我傻了
            link = related.get(i);
            linkArray[i][0] = link.getSourceNodeId();
            linkArray[i][1] = link.getTargetNodeId();
        }
        // int[] 转 List<Integer>
        // 关键节点算法传入的是节点id最大值,而不是节点数量
        // 虽然有时它们相等
        // 否则数组越界,项目直接崩,服啦
        Integer max = Collections.max(nodeIds);
        int[] keys = keyNode.getKeyNodeIds(max, linkArray);
        List<Integer> result = new ArrayList<>();
        for (int key : keys) result.add(key);
        // 在计算关键节点时，一并把关键链路算了
        keyLink.init(linkArray, max);
        redisTemplate.opsForHash().put("graph", "keyLinks", keyLink.getKeyLinks(result));
        return result;
    }

    @Override
    public Boolean deleteNodeById(Integer id) {
        // 删除节点的前提:没有边和其关联
        List<Link> links = linkMapper.selectList(null);
        for (Link link : links) {
            if (link.getSourceNodeId().equals(id) ||
                    link.getTargetNodeId().equals(id)) {
                return false;
            }
        }
        // 删除节点的同时,也要把节点-子网关系删除
        // 关系要先删除,节点id是关系的外键
        // 1. 创建查询包装类
        QueryWrapper<SubNetworkNode> query = new QueryWrapper<>();
        // 2. 设置查询条件
        query.eq("node_id", id);
        subNetworkNodeMapper.delete(query);
        nodeMapper.deleteById(id);
        return true;
    }

    @Override
    public void addNode(Integer subId, Node node) {
        nodeMapper.insert(node);
        int autoId = nodeMapper.getNodeAutoIncrement();
        subNetworkNodeMapper.insert(new SubNetworkNode(subId, autoId));
    }

    @Override
    public List<Count> physicalCount() {
        List<Count> counts = nodeMapper.getPhysicalTypeCount();
        // 1.主机 2.路由器 3.交换机 4.网桥 5.集线器
        for (Count count : counts) {
            switch (count.getCountName()) {
                case "1":
                    count.setCountName("主机");
                    break;
                case "2":
                    count.setCountName("路由器");
                    break;
                case "3":
                    count.setCountName("交换机");
                    break;
            }
        }
        return counts;
    }

    @Override
    public List<Count> logicalCount() {
        List<Count> counts = nodeMapper.getLogicalTypeCount();
        // 1.交换 2.路由 3.应用
        for (Count count : counts) {
            switch (count.getCountName()) {
                case "1":
                    count.setCountName("交换");
                    break;
                case "2":
                    count.setCountName("路由");
                    break;
                case "3":
                    count.setCountName("应用");
                    break;
            }
        }
        return counts;
    }

    @Override
    public List<Count> serviceVulCount() {
        List<Count> counts = nodeMapper.getServiceVulCount();
        for (Count count : counts) {
            count.setCountName("节点" + count.getCountName());
        }
        return counts;
    }

    @Override
    public List<Node> getNodeBySafety(Integer safety) {
        // 1. 创建查询包装类
        QueryWrapper<Node> query = new QueryWrapper<>();
        // 2. 设置查询条件
        query.eq("controllable_level", safety);
        // 3. 执行查询
        return nodeMapper.selectList(query);
    }

    @Override
    public ServiceVul nodeServiceVulNum() {
        ServiceVul serviceVul = new ServiceVul();
        // 设置所有服务名称
        List<ServiceNet> serviceNets = serviceNetMapper.selectList(null);
        List<String> services = serviceNets.stream().map(ServiceNet::getServiceName).collect(Collectors.toList());
        serviceVul.setServices(services);
        // 统计每个节点的服务漏洞数量
        HashMap<String, int[]> map = new HashMap<>();
        List<NodeVul> nodeVulNum = nodeMapper.getNodeVulNum();
        for (NodeVul nodeVul : nodeVulNum) {
            String nodeName = nodeVul.getNodeName();
            if (!map.containsKey(nodeName)) {
                map.put(nodeName, new int[services.size()]);
            }
            map.get(nodeName)[nodeVul.getServiceId() - 1] = nodeVul.getVulSum();
        }
        // 将map数据转化成数组
        List<NodeVulNum> data = new ArrayList<>(map.size());
        map.forEach((key, value) -> {
            NodeVulNum elem = new NodeVulNum();
            elem.setNodeName(key);
            List<Integer> list = new ArrayList<>();
            for (int i : value) {
                list.add(i);
            }
            elem.setNums(list);
            data.add(elem);
        });
        serviceVul.setData(data);
        return serviceVul;
    }

    @Override
    public KeyNodePath getKeyNodePath() {
        return null;
    }

    /**
     * 选择某种关键节点算法，对某张图进行关键节点检测
     *
     * @param func 方法名称
     * @param path 拓扑图路径
     * @return 关键节点id列表
     */
    @Override
    public List<Integer> keyNode(String func, String path) {
        if (!"default".equals(func)) return KeyNodeUtil.keyNode(func, path);
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        // 前端确保先解析，这样redis中才有数据
        List<int[]> graph = (List<int[]>) hash.get("graph", path);
        int len = graph.size(), idMax;
        int[][] links = new int[len - 1][];
        for (int i = 0; i < len - 1; i++) links[i] = graph.get(i);
        idMax = Arrays.stream(graph.get(len - 1)).max().getAsInt();
        // 图太大，直接崩掉了
        int[] keyNodeIds = keyNode.getKeyNodeIds(idMax, links);
        ArrayList<Integer> res = new ArrayList<>();
        for (int keyNodeId : keyNodeIds) res.add(keyNodeId);
        return res;
    }
}




