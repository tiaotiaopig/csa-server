package edu.scu.csaserver.utils;

import java.util.Arrays;

/**
 * 使用弗洛伊德算法（Floyd）求图的最短路径
 * 有个尴尬的问题，对于连通图中的任意两个节点，都至少存在一条最短路径
 * 这个算法就没办法了，╮（╯＿╰）╭
 * 这个算法我们只能算平均路径长度了
 * 相同长度的最短路径我们只记录一条
 * @author Lifeng
 * @date 2021/9/23 20:59
 */
public class Floyd {

    private static int[][] graph;
    private static int[][] path;

    public static void floyd(int[][] edges) {
        int idMax = 0;
        for (int[] edge : edges) idMax = Math.max(idMax, Math.max(edge[0], edge[1]));
        graph = new int[idMax + 1][idMax + 1];
        path = new int[idMax + 1][idMax + 1];
        for (int[] g : graph) {
            Arrays.fill(g, idMax + 1);
        }
        for (int[] edge : edges) {
            graph[edge[0]][edge[1]] = 1;
            graph[edge[1]][edge[0]] = 1;
        }
        // 以 k 作为中间节点，对所有顶点对进行检测
        for (int k = 1, sum; k <= idMax; k++) {
            for (int i = 1; i <= idMax; i++) {
                for (int j = 1; j <= idMax; j++) {
                    sum = graph[i][k] + graph[k][j];
                    if (graph[i][j] > sum) {
                        graph[i][j] = sum;
                        path[i][j] = k;
                    }
                }
            }
        }
    }

    /**
     * 我们这是个连通图
     * 最短路径条数已知
     * 相同长度的我们只记录一条
     * 归一化的话，就平均除以最长
     * @return 平均路径长度，最大路径长度
     */
    public static float[] avgShort() {
        int len = graph.length, count, max = 0;
        float sum = 0;
        for (int i = 1; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                sum += graph[i][j];
                max = Math.max(max, graph[i][j]);
            }
        }
        count = (len - 1) * (len - 2) / 2;
        return new float[]{sum / count, max};
    }

    public static float centrality() {
        int len = graph.length, min = len * len, max = 0, count;
        float[] nodeCenter = new float[len];
        int shortest = (len - 1) * (len - 2) / 2;
        // 统计节点 i 在所有最短路径上出现的次数
        for (int i = 1; i < len; i++) {
            count = existCount(i);
            nodeCenter[i] = count;
            min = Math.min(min, count);
            max = Math.max(max, count);
        }
        // 直接归一化，取平均值得了
        float sum = 0;
        for (int i = 1; i < len; i++) {
            sum += (nodeCenter[i] - min) / (max - min);
        }
        return sum / (len - 1);
    }

    private static int existCount(int node) {
        int len = graph.length, count = 0;
        for (int i = 1; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if (exist(i, j, node)) count++;
            }
        }
        return count;
    }

    /**
     * 判断 node 是否在 source -> target 这条最短路径出现
     * @param source
     * @param target
     * @param node
     * @return
     */
    private static boolean exist(int source, int target, int node) {
        if (path[source][target] == 0) return node == source || node == target;
        int transit = path[source][target];
        if (transit == node) return true;
        return exist(transit, target, node);
    }

    public static void main(String[] args) {
        int[][] edge = {{1, 2}, {1, 3}, {3, 4}, {2, 4}, {1, 4}};
        floyd(edge);
        System.out.println(avgShort());
        System.out.println(centrality());
    }
}
