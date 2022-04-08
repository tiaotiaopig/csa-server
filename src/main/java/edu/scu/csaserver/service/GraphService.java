package edu.scu.csaserver.service;

import edu.scu.csaserver.vo.Graph;

import java.util.HashMap;

public interface GraphService {

    /**
     * 由数据库的信息生成图
     * @return
     */
    public Graph generateGraph();

    /**
     * 根据拓扑图文件生成图
     * @param filename
     * @return
     */
    Graph generateGraph(String filename);

    /**
     * 由拓扑图路径名获取图的统计信息
     * @param filename 拓扑图路径名
     * @return 图的统计信息
     */
    HashMap<String, String> getGraphInfo(String filename);
}
