package edu.scu.csaserver.utils;

import com.alibaba.fastjson.JSON;

/**
 * 工具类的方法最好定义为静态方法
 * 简单封装下,json和object相互转换的操作,方便更换第三方库
 * @author Lifeng
 * @date 2022/5/28 13:18
 */
public class JSONUtil {

    public static <T> T parseObject(String jsonStr, Class<T> clazz) {
        return JSON.parseObject(jsonStr, clazz);
    }

    public static String objToJson(Object obj) {
        return JSON.toJSONString(obj);
    }
}
