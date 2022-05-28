package edu.scu.csaserver.service.impl;

import edu.scu.csaserver.domain.Link;
import edu.scu.csaserver.domain.Node;
import edu.scu.csaserver.domain.SubNetwork;
import edu.scu.csaserver.domain.vo.Category;
import edu.scu.csaserver.domain.vo.Graph;
import edu.scu.csaserver.domain.vo.LinkInfo;
import edu.scu.csaserver.domain.vo.NodeInfo;
import edu.scu.csaserver.mapper.LinkMapper;
import edu.scu.csaserver.mapper.NodeMapper;
import edu.scu.csaserver.mapper.SubNetworkMapper;
import edu.scu.csaserver.mapper.SubNetworkNodeMapper;
import edu.scu.csaserver.service.FileService;
import edu.scu.csaserver.service.GraphService;
import edu.scu.csaserver.utils.GraphUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class GraphServiceImpl implements GraphService {

    @Autowired
    private SubNetworkMapper subNetworkMapper;
    @Autowired
    private NodeMapper nodeMapper;
    @Autowired
    private LinkMapper linkMapper;
    @Autowired
    private SubNetworkNodeMapper subNetworkNodeMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private FileService fileService;

    @Override
    public Graph generateGraph() {
        Graph graph = new Graph();
        HashMap<Integer, String> map = new HashMap<>();
        String[] symbols = {"circle", "rect", "roundRect", "triangle", "diamond", "pin", "arrow", "none"};
        int index = 0, len = symbols.length;
        // 填充类别信息
        List<SubNetwork> subNetworks = subNetworkMapper.selectList(null);
        for (SubNetwork subNetwork : subNetworks) {
            Category category = new Category();
            category.setName(subNetwork.getSubNetworkName());
            category.setSymbol(symbols[index++ % len]);
            graph.getCategories().add(category);
            // 确定该子网有那些节点
            List<Integer> ids = subNetworkNodeMapper.getNodeId(subNetwork.getId());
            for (Integer id : ids) {
                map.put(id, subNetwork.getSubNetworkName());
            }
        }
        // 填充节点信息
        List<Node> nodes = null;
        if (redisTemplate.opsForHash().hasKey("graph", "nodes")) {
            nodes = (List<Node>) redisTemplate.opsForHash().get("graph", "nodes");
        } else {
            nodes = nodeMapper.selectList(null);
            redisTemplate.opsForHash().put("graph", "nodes", nodes);
        }
        for (Node node : nodes) {
            NodeInfo nodeInfo = new NodeInfo();
            nodeInfo.setName("节点" + node.getId());
            nodeInfo.setCategory(map.get(node.getId()));
            nodeInfo.setNode(node);
            graph.getNodes().add(nodeInfo);
        }
        // 填充边的信息
        // 边的source 和 target 要和 node 的 name 对应
        List<Link> links = linkMapper.selectList(null);
        for (Link link : links) {
            LinkInfo linkInfo = new LinkInfo();
            linkInfo.setSource("节点" + link.getSourceNodeId());
            linkInfo.setTarget("节点" + link.getTargetNodeId());
            linkInfo.setLink(link);
            graph.getLinks().add(linkInfo);
        }
        return graph;
    }

    /**
     * 根据拓扑图文件生成图
     *
     * @param filename
     * @return
     */
    @Override
    public Graph generateGraph(String filename) {
        Graph graph = new Graph();
        List<int[]> parseRes;
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        if (hash.hasKey("graph", filename)) {
            parseRes = (List<int[]>) hash.get("graph", filename);
        } else {
            parseRes = fileService.parseTxt(filename);
            hash.put("graph", filename, parseRes);
        }
        int len = parseRes.size();
        // 先填充边信息
        int[] link, nodes;
        for (int i = 0; i < len - 1; i++) {
            link = parseRes.get(i);
            LinkInfo linkInfo = new LinkInfo();
            linkInfo.setSource("节点" + link[0]);
            linkInfo.setTarget("节点" + link[1]);
            graph.getLinks().add(linkInfo);
        }
        // 填充节点
        nodes = parseRes.get(len - 1);
        for (int node : nodes) {
            NodeInfo nodeInfo = new NodeInfo();
            nodeInfo.setName("节点" + node);
            graph.getNodes().add(nodeInfo);
        }
        return graph;
    }

    /**
     * 由拓扑图路径名获取图的统计信息
     *
     * @param filename 拓扑图路径名
     * @return 图的统计信息
     */
    @Override
    public HashMap<String, String> getGraphInfo(String filename) {
        String key = "graphCount";
        HashMap<String, String> graphCount;
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        if (hash.hasKey(key, filename)) {
            graphCount = (HashMap<String, String>) hash.get(key, filename);
//            redisTemplate.expire(key, 60, TimeUnit.SECONDS);
        } else {
            graphCount = GraphUtil.graphCount(filename);
            hash.put(key, filename, graphCount);
        }
        return graphCount;
    }
}
