package edu.scu.csaserver.utils;

import edu.scu.csaserver.domain.Link;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 使用java调用若干链路预测算法
 * @author Lifeng
 * @date 2022/3/24 22:16
 */
public class LinkPredictionUtil {
    private final static String pythonPath = "/home/lifeng/anaconda3/envs/mytorch/bin/python pyfile/LinkPrediction.py ";

    /**
     * 使用链路预测算法 func 计算 path 图的存在链路（取前10%）
     * @param func 使用哪种链路预测算法，xx.py
     * @param path 待计算的图的路径，xx.txt,点对格式（从0或1开始编号）
     * @return 返回前10%节点作为预测的链路
     */
    public static List<Integer> linkPrediction(String func, String path) {
        List<Integer> list = new ArrayList<>();
        String cmd = pythonPath + func + " graph/" + path;
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
}
