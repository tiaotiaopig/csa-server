package edu.scu.csaserver.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 实现调用python脚本的统一操作，减少代码冗余
 * @author Lifeng
 * @date 2022/4/21 20:38
 */
public class PythonUtil {
    private final static String pythonPath = "/home/lifeng/anaconda3/envs/mytorch/bin/python ";

    public static List<String> exec(String cmd) {
        List<String> list = new ArrayList<>();
        try {
            Process proc = Runtime.getRuntime().exec(pythonPath + cmd);
            Scanner res = new Scanner(proc.getInputStream());
            while (res.hasNext()) list.add(res.next());
            res.close();
            proc.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }
}
