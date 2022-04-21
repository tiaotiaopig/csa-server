package edu.scu.csaserver.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 调用 python 的分布统计实现
 * @author Lifeng
 * @date 2022/4/21 20:33
 */
public class DistributeUtil {

    private final static String pythonFile = "pyfile/distribution.py ";

    public static Map<String, List<Integer>> degree_distribute(String fileName) {
        String cmd = pythonFile + fileName + " degree_distribute";
        List<String> res = PythonUtil.exec(cmd);
        HashMap<String, List<Integer>> map = new HashMap<>();
        List<Integer> degree = new ArrayList<>();
        List<Integer> count = new ArrayList<>();
        int len = res.size();
        for (int id = 0; id < len; id += 2) {
            degree.add(Integer.parseInt(res.get(id)));
            count.add(Integer.parseInt(res.get(id + 1)));
        }
        map.put("degree", degree);
        map.put("count", count);
        return map;
    }

    public static Map<String, List<Integer>> community_distribute(String fileName) {
        String cmd = pythonFile + fileName + " community_distribute";
        List<String> res = PythonUtil.exec(cmd);
        HashMap<String, List<Integer>> map = new HashMap<>();
        List<Integer> community = new ArrayList<>();
        List<Integer> count = new ArrayList<>();
        int len = res.size();
        for (int id = 0; id < len; id += 2) {
            community.add(Integer.parseInt(res.get(id)));
            count.add(Integer.parseInt(res.get(id + 1)));
        }
        map.put("community", community);
        map.put("count", count);
        return map;
    }

}
