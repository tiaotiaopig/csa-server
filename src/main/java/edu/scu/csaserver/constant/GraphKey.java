package edu.scu.csaserver.constant;

/**
 * 定义图操作相关的缓存key常量
 */
public enum GraphKey {

    /**
     * 使用hash缓存所有图的完整拓扑,这是键
     * 使用文件名作为hash键
     */
    GRAPH("graph:topology"),
//
    /**
     * 使用hash缓存所有图的统计信息
     * 使用文件名作为hash键
     */
    COUNT("graph:count"),
    /**
     * 使用hash缓存所有图的掩盖后的结果,掩盖比例作为键
     * 使用文件名作为hash键
     */
    MASK("mask:%s"),
    /**
     * 使用hash缓存所有图掩盖后的预测结果,图名+掩盖比例作为键
     * 使用方法名作为hash键
     */
    PREDICT("predict:%s-%s");

    private final String KEY;
    GraphKey(String KEY) {
        this.KEY = KEY;
    }

    public String getKEY() {
        return KEY;
    }
}
