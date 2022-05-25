package edu.scu.csaserver.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * 使用java调用关于整个图的python方法
 * @author Lifeng
 * @date 2022/4/7 20:30
 */
public class GraphUtil {
    private final static String pythonPath = "/home/lifeng/anaconda3/envs/mytorch/bin/python pyfile/GraphCount.py ";

    /**
     * 根据路径名获取对应图的统计信息
     * @param path 路径名
     * @return 图的统计信息
     */
    public static HashMap<String, String> graphCount(String path) {
        HashMap<String, String> map = new HashMap<>();
        String cmd = pythonPath  + "graph/" + path;
        try {
            Process proc = Runtime.getRuntime().exec(cmd);
            Scanner res = new Scanner(proc.getInputStream());
            map.put("name", res.next());
            map.put("node_count", res.next());
            map.put("edge_count", res.next());
            map.put("avg_degree", res.next());
            map.put("diameter", res.next());
            map.put("density", res.next());
            map.put("radius", res.next());
            map.put("avg_path_length", res.next());
            res.close();
            proc.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return map;
    }
}
