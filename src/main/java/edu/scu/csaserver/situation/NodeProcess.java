package edu.scu.csaserver.situation;

import edu.scu.csaserver.domain.Node;
import edu.scu.csaserver.vo.NodeNormal;

import java.util.ArrayList;
import java.util.List;

/**
 * 计算节点的处理效能（态势）
 * 主要包括节点的吞吐量，计算性能，服务数量，漏洞数量
 * @author Lifeng
 * @date 2021/9/21 15:56
 */
public class NodeProcess {
    public static List<NodeNormal> normalize (List<Node> nodes) {
        if (nodes.isEmpty()) return null;
        Node first = nodes.get(0);
        int throughoutMin = first.getThroughput(), throughoutMax = throughoutMin;
        int cpMin = first.getComputePerformance(), cpMax = cpMin;
        int serviceMin = first.getServiceSum(), serviceMax = serviceMin;
        int vulMin = first.getVulnerabilitySum(), vulMax = vulMin;
        // 计算最值
        for (Node node : nodes) {
            throughoutMin = Math.min(throughoutMin, node.getThroughput());
            throughoutMax = Math.max(throughoutMax, node.getThroughput());
            cpMin = Math.min(cpMin, node.getComputePerformance());
            cpMax = Math.max(cpMax, node.getComputePerformance());
            serviceMin = Math.min(serviceMin, node.getServiceSum());
            serviceMax = Math.max(serviceMax, node.getServiceSum());
            vulMin = Math.min(vulMin, node.getVulnerabilitySum());
            vulMax = Math.max(vulMax, node.getVulnerabilitySum());
        }
        // 进行归一化
        List<NodeNormal> res = new ArrayList<>();
        for (Node node : nodes) {
            NodeNormal nn = new NodeNormal();
            nn.setNodeId(node.getId());
            nn.setCp(1f * (node.getComputePerformance() - cpMin) / (cpMax - cpMin));
            nn.setServiceNum(1f * (node.getServiceSum() - serviceMin) / (serviceMax - serviceMin));
            nn.setThroughout(1f * (node.getThroughput() - throughoutMin) / (throughoutMax - throughoutMin));
            nn.setVul(1f * (node.getVulnerabilitySum() - vulMin) / (vulMax - vulMin));
            res.add(nn);
        }
        return res;
    }
}
