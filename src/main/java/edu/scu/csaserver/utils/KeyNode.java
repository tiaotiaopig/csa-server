package edu.scu.csaserver.utils;

import org.springframework.stereotype.Component;

/**
 * 关键节点识别的工具类
 * 使用了第三方 C++ 代码
 * 利用JNI技术，实现 java 调用 C++ 的方法
 * @author Lifeng
 * @date 2021/7/14 20:21
 */
@Component
public class KeyNode {
    /**
     * 定义一个关键节点识别的方法，该方法在C++中实现
     * 该算法输入是边关系，即是用邻接矩阵表示的图
     * 为了方便，我们使用文件做中转传递数据
     * 后期有时间，我们可以改成输入是二维数组int[n][2]
     * 已经改成二维数组啦
     * @param node_num 图的节点数，用于重建邻接矩阵
     * @param links 输入网络邻接矩阵，内容，每行 id1 id2
     * @return 计算出来的关键节点数组
     */
    private native int[] getKeyNode(int node_num, int[][] links);

    /**
     * 对关键节点算法进行了简单的封装
     * windows 下需要加载 .dll
     * linux 下加载 .so
     * java 可移植性被破坏，连接库是本地编译的
     * 我们需要将链接库文件放到 java.library.path 下
     * 先将其放在jdk 的 /bin 目录下
     * @param nodeNum
     * @param links
     * @return
     */
    public int[] getKeyNodeIds(int nodeNum, int[][] links) {
        // System.out.println(System.getProperty("java.library.path"));
        // 默认从 java.library.path 下加载 .dll文件
        // 放在其中一个目录即可
        // 为了快速加载，我们可能要指定绝对路径
        /**
         * TODO: 改用加载方式，Runtime.getRuntime().load(soLibFilePath);
         */
        System.loadLibrary("KeyNode");
        return getKeyNode(nodeNum, links);
    }
}
