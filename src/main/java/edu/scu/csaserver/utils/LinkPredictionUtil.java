package edu.scu.csaserver.utils;

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
    private final static String pythonFile = "pyfile/mask_predict.py ";
    private final static String pythonFile2 = "pyfile/LinkPrediction.py ";

    /**
     * 使用链路预测算法 func 计算 path 图的存在链路（取前10%）
     * @param func 使用哪种链路预测算法，xx.py
     * @param path 待计算的图的路径，xx.txt,点对格式（从0或1开始编号）
     * @return 返回前10%节点作为预测的链路
     */
    public static List<String> linkPrediction(String func, String path) {
        String cmd = pythonFile2 + func + " graph/" + path;
        return PythonUtil.exec(cmd);
    }

    public static List<String> getMasked(String dataName, String ratio) {
        String cmd = pythonFile + "1 " + dataName + " " + ratio;
        return PythonUtil.exec(cmd);
    }

    public static List<String> getPrediction(String dataName, String ratio, String funcName) {
        String cmd = pythonFile + "2 " + dataName + " " + ratio + " " + funcName;
        return PythonUtil.exec(cmd);
    }
}
