package edu.scu.csaserver.utils;

import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * 调用python实现的若干关键节点实现方法
 *
 * @author Lifeng
 * @date 2022/3/24 22:07
 */
@Component
public class KeyNodeUtil {
    private final static String pythonPath = "/home/lifeng/anaconda3/bin/python pyfile/KeyNode.py ";

    /**
     * 这次获取关键节点 id 和 权值
     * @param func 使用哪种关键节点算法，xx.py
     * @param path 待计算的图的路径，xx.txt,点对格式（从0或1开始编号）
     * @return 返回前10%节点作为关键节点
     */
    public static HashMap<Integer, Double> keyNode(String func, String path) {
        HashMap<Integer, Double> map = new HashMap<>();
        String cmd = pythonPath + func + " graph/" + path;
        try {
            Process proc = Runtime.getRuntime().exec(cmd);
            Scanner res = new Scanner(proc.getInputStream());
            while (res.hasNext()) {
                map.put(res.nextInt(), res.nextDouble());
            }
            res.close();
            proc.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static void main(String[] args) {
//        execBC("", "graph/BUP.txt");
//        HashMap<Integer, Double> map = keyNode("D", "BUP.txt");
    }
}
