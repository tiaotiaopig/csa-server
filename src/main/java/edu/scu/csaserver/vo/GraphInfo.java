package edu.scu.csaserver.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 供前端展示的拓扑文件代表的图，一些统计信息
 * @author Lifeng
 * @date 2022/4/7 17:21
 */
@Data
public class GraphInfo implements Serializable {
    private static final long serialVersionUID = 3411816589972228000L;

    /**
     * 拓扑图的名称
     */
    @ApiModelProperty("拓扑图的名称")
    private String graphName;

    /**
     * 节点总数
     */
    @ApiModelProperty("节点总数")
    private Integer nodeCount;

    /**
     * 链路总数
     */
    @ApiModelProperty("链路总数")
    private Integer linkCount;

    /**
     * 平均节点度
     */
    @ApiModelProperty("平均节点度")
    private Double avgDegree;
}
