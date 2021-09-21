package edu.scu.csaserver.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 计算图中 点 或者 边 的拓扑权重
 * 点的拓扑权重就是其相邻的边的权重和
 * 边的权重计算如下：
 *      先计算出图中任意两个节点存在的路径
 *      也就是图中所有可能的路径之和（不能存在环路）
 * @author Lifeng
 * @date 2021/9/14 18:01
 */
public class KeyNodePath {
    private final static int HOP_MAX = 13;      // 最大路径长度
    private int idMax;
    private boolean[] visited;    // 防止环路
    private ArrayList<Integer> edgeList;      // 所有的边
    private int[][] edges;
    private int[][] graphMatrix;    // 邻接矩阵
    private int[][] graph;  // 数组形式邻接表
    private int[] nodes;    // 所有节点编号
    private int[][] edgeWeight;     // 边的权重
    private int[][] nodeWeight;     // 节点权重 [?][0] 节点编号  [?][1] 节点权重

    public void init(File filename) {
        edgeList = new ArrayList<>();
        // 从文件读出所有边
        readFileToArray(filename);
        setIdMax();
        setGraph();
        // 获取所有节点编号
        setNodes();
        // 这里加一，是因为我们的节点编号是从 1 开始的
        visited = new boolean[idMax + 1];
        edgeWeight = new int[idMax + 1][idMax + 1];
        nodeWeight = new int[idMax + 1][2];
    }

    public void init(int[][] edges) {
        edgeList = new ArrayList<>();
        // 从文件读出所有边
        setAdjacentMatrix(edges);
        setIdMax();
        // 邻接表
        setGraph();
        // 邻接矩阵
        setGraphMatrix();
        // 获取所有节点编号
        setNodes();
        // 这里加一，是因为我们的节点编号是从 1 开始的
        visited = new boolean[idMax + 1];
        edgeWeight = new int[idMax + 1][idMax + 1];
        nodeWeight = new int[idMax + 1][2];
    }

    /**
     * 从边数组[][2]中，建立边列表
     * 主要是为了兼容读文件的方式，不然直接使用 edges 会更方便
     * @param edges
     */
    private void setAdjacentMatrix (int[][] edges) {
        this.edges = edges;
        for (int[] edge : edges) {
            edgeList.add(edge[0]);
            edgeList.add(edge[1]);
        }
    }

    /**
     * 从文件中读出所有边，并转化成数组形式的邻接表
     * @param filename 单个文件的文件名
     * @return
     */
    private void readFileToArray(File filename) {
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            while (true) {
                line = reader.readLine();
                if ("".equals(line)) continue;
                if (line == null) break;
                // 去除空白，任意数量分隔，增加健壮性
                String[] edge = line.trim().split(" +");
                edgeList.add(Integer.parseInt(edge[0]));
                edgeList.add(Integer.parseInt(edge[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * idMax 很重要，是我们初始化需要的最重要参数
     */
    private void setIdMax() {
        // 设置 id 最大值
        idMax = Collections.max(edgeList);
    }
    /**
     * 由边列表 edgeList,恢复出邻接表
     */
    private void setGraph() {
        // 设置数组邻接表
        List<List<Integer>> tmp = new ArrayList<>();
        for (int i = 0; i <= idMax; i++) tmp.add(new ArrayList<>());
        for (int i = 0, len = edgeList.size(); i < len; i+= 2) {
            tmp.get(edgeList.get(i)).add(edgeList.get(i + 1));
            tmp.get(edgeList.get(i + 1)).add(edgeList.get(i));
        }
        // list 转 数组
        graph = new int[idMax + 1][];
        for (int i = 0, size; i <= idMax; i++) {
            size = tmp.get(i).size();
            graph[i] = new int[size];
            for (int j = 0; j < size; j++) graph[i][j] = tmp.get(i).get(j);
        }
    }

    private void setGraphMatrix() {
        graphMatrix = new int[idMax + 1][idMax + 1];
        for (int[] edge : edges) {
            graphMatrix[edge[0]][edge[1]] = 1;
            graphMatrix[edge[1]][edge[0]] = 1;
        }
    }

    /**
     * 根据邻接表，统计出连通分量中节点的编号
     */
    private void setNodes() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < graph.length; i++) {
            // 单独的节点，不会统计
            if (graph[i].length > 0) list.add(i);
        }
        nodes = new int[list.size()];
        for (int i = 0; i < nodes.length; i++) nodes[i] = list.get(i);
    }

    /**
     * 寻找图中任意两个节点的路径数总和
     * 这个过程中，所有节点的权值和边的权值同时也被计算了出来
     * @return 小于 HOP_MAX 长度的所有路径数量
     */
    public int find() {
        int res = 0;
        for (int i = 0; i < nodes.length - 1; i++) {
            for (int j = i + 1; j < nodes.length; j++) {
                res += dfs(nodes[i], nodes[j], new ArrayList<>(), 0);
            }
        }
        return res;
    }

    /**
     * 获取根据权值降序排列的节点列表
     * @return 权值降序排列的节点列表
     */
    public int[][] getSortedNodeWeight() {
        int[][] res = nodeWeight.clone();
        Arrays.sort(res, (a, b) -> b[1] - a[1]);
        return res;
    }

    /**
     * 使用递归的深度搜索方法，寻找任意两个节点间的路径
     * 我们直接在计算路径的时候，就对点和边的权重进行计数
     * 这样就不用再保存路径，然后再遍历了
     * @param curr 当前节点
     * @param target 目标节点
     * @param selected 已经走过的路径
     * @param hop 经过的跳数
     * @return 当前和目标的路径数
     */
    private int dfs(int curr, int target, List<Integer> selected, int hop) {
        if (hop == HOP_MAX) return 0;
        if (curr == target) {
            selected.add(curr);
            // 为了边方便遍历，我们先初始化第一个点
            int left, right = selected.get(0), len = selected.size();
            nodeWeight[selected.get(0)][0] = selected.get(0);
            nodeWeight[selected.get(0)][1]++;
            for (int i = 1; i < len; i++) {
                left = right;
                right = selected.get(i);
                // 节点权重增加
                nodeWeight[right][1]++;
                nodeWeight[right][0] = right;
                // 边权重增加
                edgeWeight[left][right]++;
                edgeWeight[right][left]++;
            }
            selected.remove(len - 1);
            return 1;
        }
        int res = 0;
        // 加入路径
        visited[curr] = true;
        selected.add(curr);
        // 基于邻接表，获取curr的所有邻接点
        for (int adjacent : graph[curr]) {
            if (!visited[adjacent]) res += dfs(adjacent, target, selected, hop + 1);
        }
        // 恢复现场
        visited[curr] = false;
        selected.remove(selected.size() - 1);
        return res;
    }

    public static void main(String[] args) throws IOException {
        String inputPath = "graph\\real";
        File[] files = new File(inputPath).listFiles();
        for (File file : files) {
            if (file.isFile()) {
                KeyNodePath keyNode = new KeyNodePath();
                keyNode.init(file);
                File newFile = new File(file.getAbsolutePath().replace("graph", "result"));
                new File(newFile.getParent()).mkdirs();
                BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));
                int counts = keyNode.find();
                System.out.println(file.getName() + " : " + counts);
                int[][] nodeWeight = keyNode.getSortedNodeWeight();
                for (int i = 0; i < 10; i++) {
                    writer.write("node-" + nodeWeight[i][0] + " : " + nodeWeight[i][1]);
                    writer.newLine();
                }
                writer.flush();
                writer.close();
            }
        }
    }
}
