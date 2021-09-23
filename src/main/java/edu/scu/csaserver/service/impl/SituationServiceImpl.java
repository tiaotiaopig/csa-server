package edu.scu.csaserver.service.impl;

import edu.scu.csaserver.domain.Link;
import edu.scu.csaserver.domain.Node;
import edu.scu.csaserver.mapper.LinkMapper;
import edu.scu.csaserver.mapper.NodeMapper;
import edu.scu.csaserver.service.SituationService;
import edu.scu.csaserver.situation.NodeProcess;
import edu.scu.csaserver.situation.PhysicalTransmission;
import edu.scu.csaserver.utils.KeyNodePath;
import edu.scu.csaserver.vo.NodeNormal;
import edu.scu.csaserver.vo.Res;
import edu.scu.csaserver.vo.TopoElem;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 计算网络效能态势
 * 计划是根据选中的节点所构成的网络拓扑
 * 计算出网络态势
 * @author Lifeng
 * @date 2021/9/22 15:15
 */
@Service
public class SituationServiceImpl implements SituationService {

    private final LinkMapper linkMapper;
    private final NodeMapper nodeMapper;
    private final KeyNodePath keyNodePath;

    public SituationServiceImpl(LinkMapper linkMapper, NodeMapper nodeMapper, KeyNodePath keyNodePath) {
        this.linkMapper = linkMapper;
        this.nodeMapper = nodeMapper;
        this.keyNodePath = keyNodePath;
        init();
    }

    /**
     * 关键节点的算法，需要从数据库查询边的关系，进行初始化
     */
    private void init() {
        List<Link> links = linkMapper.selectList(null);
        if (links == null) return;
        int size = links.size();
        int[][] edges = new int[size][2];
        for (int i = 0; i < size; i++) {
            edges[i][0] = links.get(i).getSourceNodeId();
            edges[i][1] = links.get(i).getTargetNodeId();
        }
        keyNodePath.init(edges);
        keyNodePath.find();
    }

    @Override
    /**
     * 使用归一化 + 加权平均的方法，求得网络传输效能
     */
    public float phyTrans() {
        float sum = 0, score;
        List<Link> links = linkMapper.selectList(null);
        if (links == null) return sum;
        PhysicalTransmission.normalize(links);
        int[][] edgeWeight = keyNodePath.getEdgeWeight();
        int weightSum = keyNodePath.edgeWeightSum();
        for (Link link : links) {
            score = link.getBandwidth() * 0.3f + link.getDelay() * 0.3f +
                    link.getLosePackage() * 0.3f + link.getSn() * 0.1f;
            sum += score * edgeWeight[link.getSourceNodeId()][link.getTargetNodeId()] / weightSum;
        }
        return sum * 100;
    }

    @Override
    /**
     * 使用加权平均的方法计算物理传输要素的平均值
     */
    public Link phyLinkElem() {
        Link res = new Link();
        List<Link> links = linkMapper.selectList(null);
        float avgBw = 0, avgDelay = 0, avgLpr = 0, avgSnr = 0, weight;
        int[][] edgeWeight = keyNodePath.getEdgeWeight();
        int weightSum = keyNodePath.edgeWeightSum();
        for (Link link : links) {
            weight = 1f * edgeWeight[link.getSourceNodeId()][link.getTargetNodeId()] / weightSum;
            avgBw += link.getBandwidth() * weight;
            avgDelay += link.getDelay() * weight;
            avgLpr += link.getLosePackage() * weight;
            avgSnr += link.getSn() * weight;
        }
        res.setSn(avgSnr);
        res.setDelay(avgDelay);
        res.setLosePackage(avgLpr);
        res.setBandwidth(avgBw);
        return res;
    }

    @Override
    public float nodeProcess() {
        float sum = 0, score;
        List<Node> nodes = nodeMapper.selectList(null);
        if (nodes == null) return sum;
        List<NodeNormal> list = NodeProcess.normalize(nodes);
        int[][] nodeWeight = keyNodePath.getNodeWeight();
        int weightSum = keyNodePath.nodeWeightSum();
        for (NodeNormal normal : list) {
            score = normal.getServiceNum() * 0.3f + normal.getThroughout() * 0.2f +
                    normal.getCp() * 0.3f + normal.getVul() * 0.2f;
            sum += score * nodeWeight[normal.getNodeId()][1] / weightSum;
        }
        return sum * 100;
    }

    @Override
    public NodeNormal npElem() {
        NodeNormal res = new NodeNormal();
        List<Node> nodes = nodeMapper.selectList(null);
        float avgSm = 0, avgThroughout = 0, avgCp = 0, avgVul = 0, weight;
        int[][] nodeWeight = keyNodePath.getNodeWeight();
        int weightSum = keyNodePath.nodeWeightSum();
        for (Node node : nodes) {
            weight = 1f * nodeWeight[node.getId()][1] / weightSum;
            avgSm += weight * node.getServiceSum();
            avgCp += weight * node.getComputePerformance();
            avgThroughout += weight * node.getThroughput();
            avgVul += weight * node.getVulnerabilitySum();
        }
        res.setServiceNum(avgSm);
        res.setCp(avgCp);
        res.setThroughout(avgThroughout);
        res.setVul(avgVul);
        return res;
    }

    @Override
    public TopoElem topologyElem() {
        TopoElem topoElem = new TopoElem();
        topoElem.setLinkCount(22);
        topoElem.setNodeCount(20);
        topoElem.setLinkConn(3);
        topoElem.setNodeConn(2);
        topoElem.setNodeStrength(4.32f);
        topoElem.setWCCoefficient(5.78f);
        topoElem.setCentrality(3.27f);
        topoElem.setAvgPathLength(6.7f);
        return topoElem;
    }

}
