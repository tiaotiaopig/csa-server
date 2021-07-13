package edu.scu.csaserver.service.impl;

import edu.scu.csaserver.domain.Link;
import edu.scu.csaserver.domain.Node;
import edu.scu.csaserver.domain.SubNetwork;
import edu.scu.csaserver.mapper.LinkMapper;
import edu.scu.csaserver.mapper.NodeMapper;
import edu.scu.csaserver.mapper.SubNetworkMapper;
import edu.scu.csaserver.mapper.SubNetworkNodeMapper;
import edu.scu.csaserver.service.GraphService;
import edu.scu.csaserver.vo.Category;
import edu.scu.csaserver.vo.Graph;
import edu.scu.csaserver.vo.LinkInfo;
import edu.scu.csaserver.vo.NodeInfo;
import org.springframework.beans.factory.annotation.Autowired;
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
        List<Node> nodes = nodeMapper.selectList(null);
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
}
