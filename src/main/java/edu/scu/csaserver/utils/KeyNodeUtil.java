package edu.scu.csaserver.utils;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
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
    private final static String pythonPath = "/home/lifeng/anaconda3/bin/python keyNode.py ";

    /**
     * 使用关键节点算法 func 计算 path 图的关键节点（取前10%）
     * @param func 使用哪种关键节点算法，xx.py
     * @param path 待计算的图的路径，xx.txt,点对格式（从0或1开始编号）
     * @return 返回前10%节点作为关键节点
     */
    public static List<Integer> keyNode(String func, String path) {
        List<Integer> list = new ArrayList<>();
        String cmd = pythonPath + func + " " + path;
        try {
            Process proc = Runtime.getRuntime().exec(cmd);
            Scanner res = new Scanner(proc.getInputStream());
            while (res.hasNext()) list.add(res.nextInt());
            res.close();
            proc.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
//        execBC("", "graph/BUP.txt");
        List<Integer> list = keyNode("D", "graph/BUP.txt");
        list.forEach(System.out::println);
    }
}
