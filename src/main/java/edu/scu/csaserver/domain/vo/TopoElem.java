package edu.scu.csaserver.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 计算拓扑效能的各要素
 * @author Lifeng
 * @date 2021/9/23 10:41
 */
@Data
public class TopoElem implements Serializable {
    private static final long serialVersionUID = -4002685188777804923L;

    /**
     * 节点数量
     */
    @ApiModelProperty("节点数量")
    private Integer nodeCount;

    /**
     * 边数量
     */
    @ApiModelProperty("边数量")
    private Integer linkCount;

    /**
     * 网络点连通度
     */
    @ApiModelProperty("网络点连通度")
    private Integer nodeConn;

    /**
     * 网络边连通度
     */
    @ApiModelProperty("网络边连通度")
    private Integer linkConn;

    /**
     * 节点强度
     */
    @ApiModelProperty("节点强度")
    private Float nodeStrength;

    /**
     * 介数中心性
     */
    @ApiModelProperty("介数中心性")
    private Float centrality;

    /**
     * 集权聚类系数
     */
    @ApiModelProperty("集权聚类系数")
    private Float WCCoefficient;

    /**
     * 网络平均路径长度
     */
    @ApiModelProperty("网络平均路径长度")
    private Float avgPathLength;
}
