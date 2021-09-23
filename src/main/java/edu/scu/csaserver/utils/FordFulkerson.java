package edu.scu.csaserver.utils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 最大流最小割求边连通度和点连通度
 * 图的最小割就是边连通度
 * 点连通度需要将原图转化一下，将转化后的图的边连通度为原图的点连通度
 * Ford-Fulkerson 算法就是通过贪心的算法求最小割
 * 当找不到增广路径，算法结束
 *
 * @author Lifeng
 * @date 2021/9/23 9:27
 */
class FordFulkerson {
    private double[][] residualNetwork;
    private double[][] flowNetwork;
    private final int N;
    private int[] parent;

    public FordFulkerson(int N) {
        this.N = N;
        parent = new int[N];
    }

    /**
     * 实现FordFulkerson方法的一种算法——edmondsKarp算法
     *
     * @param graph
     * @param s
     * @param t
     * @return
     */
    public double edmondsKarpMaxFlow(double[][] graph, int s, int t) {
        int length = graph.length;
        double[][] f = new double[length][length];
        for (int i = 0; i < length; i++) {
            Arrays.fill(f[i], 0);
        }
        double[][] r = residualNetwork(graph, f);
        double result = augmentPath(r, s, t);

        double sum = 0;

        while (result != -1) {
            int cur = t;
            while (cur != s) {
                f[parent[cur]][cur] += result;
                f[cur][parent[cur]] = -f[parent[cur]][cur];
                r[parent[cur]][cur] -= result;
                r[cur][parent[cur]] += result;
                cur = parent[cur];
            }

            sum += result;
            result = augmentPath(r, s, t);
        }

        residualNetwork = r;
        flowNetwork = f;

        return sum;
    }

    /**
     * deepCopy
     *
     * @param c c(u,v) 代表 u 到 v 允许的最大流量
     * @param f f(u,v) 代表 u 到 v 边当前流量
     * @return
     */
    private double[][] residualNetwork(double[][] c, double[][] f) {
        int length = c.length;
        // r(u, v 就代表了残留网络)
        double[][] r = new double[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                r[i][j] = c[i][j] - f[i][j];
            }
        }

        return r;
    }

    /**
     * 广度优先遍历，寻找增光路径，也是最短增广路径
     *
     * @param graph
     * @param s
     * @param t
     * @return
     */
    public double augmentPath(double[][] graph, int s, int t) {

        double maxFlow = Integer.MAX_VALUE;
        Arrays.fill(parent, -1);
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.add(s);
        parent[s] = s;

        while (!queue.isEmpty()) {
            int p = queue.poll();
            if (p == t) {
                while (p != s) {
                    if (maxFlow > graph[parent[p]][p])
                        maxFlow = graph[parent[p]][p];
                    p = parent[p];
                }
                break;
            }
            for (int i = 0; i < graph.length; i++) {
                if (i != p && parent[i] == -1 && graph[p][i] > 0) {
                    //flow[i]=Math.min(flow[p], graph[p][i]);
                    parent[i] = p;
                    queue.add(i);
                }
            }
        }
        if (parent[t] == -1)
            return -1;
        return maxFlow;

    }

    public double[][] getResidualNetwork() {
        return residualNetwork;
    }

    public double[][] getFlowNetwork() {
        return flowNetwork;
    }
}
