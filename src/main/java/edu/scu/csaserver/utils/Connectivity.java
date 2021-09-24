package edu.scu.csaserver.utils;

/**
 * 这个是从 0 开始编号节点的
 * 计算网络的点连通度和边连通度
 * FordFulkerson 可以计算任意两个节点的最大流（最小割）
 * 则整个网络的边连通度就是：任意两个节点的最大流的最小值
 * 由于所有节点最终被 s-t 割 分成了两个集合 O(n * n)
 * 因此只需要一个节点和其他所有的节点最大流即可 O(n)
 * @author Lifeng
 * @date 2021/9/23 14:38
 */
public class Connectivity {

    public static double[][] graph(int[][] edges) {
        int idMax = 0;
        for (int[] edge : edges) {
            idMax = Math.max(idMax, Math.max(edge[0], edge[1]));
        }
        double[][] graph = new double[idMax][idMax];
        for (int[] edge : edges) {
            // 换成从0 开始编号
            graph[edge[0] - 1][edge[1] - 1] = 1;
            graph[edge[1] - 1][edge[0] - 1] = 1;
        }
        return graph;
    }

    public static int edgeConn(double[][] graph) {
        int len = graph.length;
        double res = len, maxFlow;
        for (int i = 1; i < len; i++) {
            maxFlow = FordFulkerson.edmondsKarpMaxFlow(graph, 0, i);
            res = Math.min(res, maxFlow);
        }
        return (int) res;
    }

    // TODO: 节点连通度有问题
    public static int nodeConn(double[][] graph) {
        int len = graph.length;
        double[][] convertedGraph = convert(graph);
        double res = len - 1, maxFlow;
        for (int i = 1; i < len; i++) {
            maxFlow = FordFulkerson.edmondsKarpMaxFlow(convertedGraph, 1, 2 * i);
            res = Math.min(res, maxFlow);
        }
        return (int) res;
    }

    /**
     *
     * @param graph 原始的邻接矩阵图
     * @return 转化后的用于计算节点连通度的图
     */
    private static double[][] convert(double[][] graph) {
        int len = graph.length;
        double[][] res = new double[len + len][len + len];
        for (int i = 0; i < len; i++) res[2 * i][2 * i + 1] = 1;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if (graph[i][j] != 0) {
                    // 对于无穷的表示，计算机没有无穷，会溢出的
                    res[2 * i + 1][2 * j] = len * 2;
                    res[2 * j + 1][2 * i] = len * 2;
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[][] edges = {
                {1, 2}, {1, 3}, {2, 4}, {3, 4}
        };
        double[][] graph = graph(edges);
        System.out.println(Connectivity.edgeConn(graph));
        System.out.println(Connectivity.nodeConn(graph));
    }
}
