package edu.scu.csaserver.situation;

import edu.scu.csaserver.utils.Connectivity;
import edu.scu.csaserver.utils.Floyd;

/**
 * 计算拓扑结构效能
 * 我们使用点连通度，边连通度，介数中心性，平均路径长度
 * 计算结构拓扑效能
 * @author Lifeng
 * @date 2021/9/21 15:57
 */
public class TopologyStructure {

    public static float nodeConn;
    public static float edgeConn;
    public static float betweenCentrality;
    public static float avgShortest;
    public static float avgPath;

    public static float topoSituation(int[][] edges) {
        double[][] graph = Connectivity.graph(edges);
        int nodeMax = graph.length, edgeMax = nodeMax * (nodeMax - 1) / 2;
        nodeConn = Connectivity.nodeConn(graph);
        edgeConn = Connectivity.edgeConn(graph);
        Floyd.floyd(edges);
        betweenCentrality = Floyd.centrality();
        float[] floats = Floyd.avgShort();
        avgPath = floats[0];
        avgShortest = floats[0] / floats[1];

        float res = 0.2f * nodeConn / nodeMax + 0.3f * edgeConn / edgeMax + betweenCentrality * 0.2f + avgShortest * 0.3f;
        return Float.parseFloat(String.format("%.3f", res));
    }


}
