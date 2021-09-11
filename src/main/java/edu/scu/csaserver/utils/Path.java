package edu.scu.csaserver.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 计算图中 点 或者 边 的拓扑权重
 * 点的拓扑权重就是其相邻的边的权重和
 * 边的权重计算如下：
 *      先计算出图中任意两个节点存在的路径
 *      也就是图中所有可能的路径之和（不能存在环路）
 *
 * @author Lifeng
 * @date 2021/9/4 10:31
 */
public class Path {
    private final boolean[] visited;
    private final List<List<Integer>> graph;
    private final List<List<Integer>> path;
    private int[][] edges;

    public Path(int idMax) {
        graph = new ArrayList<>(idMax + 1);
        path = new ArrayList<>();
        visited = new boolean[idMax + 2];
        for (int i = 0; i <= idMax; i++) graph.add(new ArrayList<>());
    }

    /**
     * 寻找图中任意两个节点的路径数总和
     * @param nodes
     * @return
     */
    public int find(int[] nodes) {
        int res = 0;
        for (int i = 0; i < nodes.length - 1; i++) {
            for (int j = i + 1; j < nodes.length; j++) {
                res += dfs(nodes[i], nodes[j], new ArrayList<>());
            }
        }
        return res;
    }

    public List<List<Integer>> getPath() {
        return path;
    }

    /**
     * 获取边 (source, target) 关联的所有路径数
     * 也即删除该边后，总路径数的减少量
     * @param source
     * @param target
     * @return
     */
    public int getEdgeWeight(int source, int target) {
        int res = 0, left, right, len;
        for (List<Integer> list : path) {
            right = list.get(0);
            len = list.size();
            for (int i = 1; i < len; i++) {
                left = right;
                right = list.get(i);
                if ((source == left && target == right) || (source == right && target == left)) {
                    res++;
                    break;
                }
            }
        }
        return res;
    }

    public int getNodeWeight(int node) {
        int res = 0;
        List<Integer> list = graph.get(node);
        for (Integer i : list) res += getEdgeWeight(node, i);
        return res;
    }

    /**
     * 使用递归的深度搜索方法，寻找任意两个节点间的路径
     * @param curr 当前节点
     * @param target 目标节点
     * @param selected 已经走过的路径
     * @return 当前和目标的路径数
     */
    private int dfs(int curr, int target, List<Integer> selected) {
        if (curr == target) {
            List<Integer> list = new ArrayList<>(selected);
            list.add(curr);
            path.add(list);
            return 1;
        }
        int res = 0;
        // 加入路径
        visited[curr] = true;
        selected.add(curr);
        // 基于邻接表，获取curr的所有邻接点
        List<Integer> list = graph.get(curr);
        for (Integer adjacent : list) {
            if (!visited[adjacent]) res += dfs(adjacent, target, selected);
        }
        // 恢复现场
        visited[curr] = false;
        selected.remove(selected.size() - 1);
        return res;
    }

    public void readFile(String filename) throws IOException {
        String line;
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        do {
            line = reader.readLine();

        }
    }

    public static void main(String[] args) {
        List<List<Integer>> graph = new ArrayList<>(5);
        graph.add(0, Arrays.asList(1, 2, 3, 4));
        graph.add(1, Arrays.asList(0));
        graph.add(2, Arrays.asList(0));
        graph.add(3, Arrays.asList(0));
        graph.add(4, Arrays.asList(0));
    }
}
