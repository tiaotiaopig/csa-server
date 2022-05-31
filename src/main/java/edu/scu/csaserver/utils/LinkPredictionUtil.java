package edu.scu.csaserver.utils;

import edu.scu.csaserver.constant.PythonExec;

import java.util.List;

/**
 * 使用java调用若干链路预测算法
 * @author Lifeng
 * @date 2022/3/24 22:16
 */
public class LinkPredictionUtil {
    private final static String pythonFile = PythonExec.PREDICT.getEXEC();
    private final static String pythonFile2 = "/home/lifeng/Develop/backend/csa/pyfile/LinkPrediction.py ";

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
//        String cmd = pythonFile + "1 " + dataName + " " + ratio;
        String cmd = String.format(pythonFile, "mask", dataName, ratio, "None");
        return PythonUtil.exec(cmd);
    }

    public static List<String> getPrediction(String dataName, String ratio, String funcName) {
//        String cmd = pythonFile + "2 " + dataName + " " + ratio + " " + funcName;
        String cmd = String.format(pythonFile, "predict", dataName, ratio, funcName);
        return PythonUtil.exec(cmd);
    }
}
