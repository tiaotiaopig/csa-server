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
    /**
     * parent 用来记录增广路径
     */
    private static int[] parent;

    /**
     * 实现FordFulkerson方法的一种算法——edmondsKarp算法
     *
     * @param graph 原始的图
     * @param s 源点
     * @param t 汇点
     * @return s -> t 的最大流
     */
    public static double edmondsKarpMaxFlow(double[][] graph, int s, int t) {
        int length = graph.length;
        parent = new int[length];
        double[][] f = new double[length][length];
        double[][] r = residualNetwork(graph, f);
        double sum = 0, result = augmentPath(r, s, t);
        while (result != -1) {
            int cur = t;
            while (cur != s) {
                // 正向流量增加
                f[parent[cur]][cur] += result;
                // 反向流量
                f[cur][parent[cur]] = -f[parent[cur]][cur];
                // 残留网络刚好相反
                r[parent[cur]][cur] -= result;
                r[cur][parent[cur]] += result;
                cur = parent[cur];
            }
            sum += result;
            result = augmentPath(r, s, t);
        }
        return sum;
    }

    /**
     * deepCopy
     * 仅仅只是个深拷贝的作用
     * 这样计算残留网络复杂度太高
     * 我们仅仅需要更新增广路径上的f 和 r,即可
     * @param c c(u,v) 代表 u 到 v 允许的最大流量
     * @param f f(u,v) 代表 u 到 v 边当前流量
     * @return 深拷贝
     */
    private static double[][] residualNetwork(double[][] c, double[][] f) {
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
     * @param graph 这里的图应该是残留网络
     * @param s 源点
     * @param t 汇点
     * @return -1 代表没有增广路径，其他值代表可以增加的流量
     */
    public static double augmentPath(double[][] graph, int s, int t) {
        double maxFlow = Integer.MAX_VALUE;
        Arrays.fill(parent, -1);
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        parent[s] = s;

        while (!queue.isEmpty()) {
            int p = queue.poll();
            if (p == t) {
                // 求得可以增加的最小流量
                // 路径上的最少可用流量
                while (p != s) {
                    if (maxFlow > graph[parent[p]][p])
                        maxFlow = graph[parent[p]][p];
                    p = parent[p];
                }
                break;
            }
            for (int i = 0; i < graph.length; i++) {
                // parent 还有一个作用，就是visited,标记一个节点是否被访问过
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

    public static void main(String[] args) {
        double[][] graph = {
                {0, 1, 1, 0},
                {1, 0, 1, 1},
                {1, 0, 0, 1},
                {0, 1, 1, 0}
        };
        System.out.println(FordFulkerson.edmondsKarpMaxFlow(graph, 0, 3));
    }
}
