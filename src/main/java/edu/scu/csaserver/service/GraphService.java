package edu.scu.csaserver.service;

import edu.scu.csaserver.vo.Graph;

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
}
