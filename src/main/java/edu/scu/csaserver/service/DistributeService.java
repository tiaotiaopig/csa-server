package edu.scu.csaserver.service;

import java.util.List;
import java.util.Map;

/**
 * 一些图分布统计的具体实现
 */
public interface DistributeService {

    /**
     * 获取某个图的度分布信息
     * @param fileName
     * @return
     */
    Map<String, List<Integer>> degree_distribute(String fileName);

    /**
     * 获取某个图的社团分布信息
     * @param fileName
     * @return
     */
    Map<String, List<Integer>> community_distribute(String fileName);

    Map<String, List<? extends Number>> edge_betweenness_centrality(String fileName);
}
