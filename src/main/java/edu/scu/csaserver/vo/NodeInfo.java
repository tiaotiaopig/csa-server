package edu.scu.csaserver.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 节点详情VO
 * 这里我们将节点信息，转化为为前端需要的形式
 * 最重要的是 name 和 category
 * @author Lifeng
 * @date 2021/6/25 19:28
 */
@Data
public class NodeInfo implements Serializable {
    /**
     * 节点名称，在前端相当于id
     */
    @ApiModelProperty("节点名称，在前端相当于id")
    private String name;
    /**
     * 节点类别
     */
    @ApiModelProperty("节点类别")
    private String category;

    /**
     * 节点IP地址(可选)
     */
    @ApiModelProperty("节点IP地址(可选)")
    private String nodeIp;

    /**
     * 节点MAC（可选）
     */
    @ApiModelProperty("节点MAC（可选）")
    private String nodeMac;

    /**
     * 节点vlan(可选)
     */
    @ApiModelProperty("节点vlan(可选)")
    private Integer nodeVlan;

    /**
     * 节点名称
     */
    @ApiModelProperty("节点名称")
    private String nodeName;

    /**
     * 节点物理类型
     */
    @ApiModelProperty("节点物理类型")
    private Integer physicalType;

    /**
     * 节点逻辑类型
     */
    @ApiModelProperty("节点逻辑类型")
    private Integer logicalType;

    /**
     * 频谱下限
     */
    @ApiModelProperty("频谱下限")
    private Double spectrumFloor;

    /**
     * 频谱上限
     */
    @ApiModelProperty("频谱上限")
    private Double spectrumTop;

    /**
     * 频谱可利用率
     */
    @ApiModelProperty("频谱可利用率")
    private Integer spectrumAvailability;

    /**
     * 吞吐量
     */
    @ApiModelProperty("吞吐量")
    private Integer throughput;

    /**
     * 节点计算性能
     */
    @ApiModelProperty("节点计算性能")
    private Integer computePerformance;

    /**
     * 硬件类型
     */
    @ApiModelProperty("硬件类型")
    private Integer hardwareType;

    /**
     * 节点服务总数
     */
    @ApiModelProperty("节点服务总数")
    private Integer serviceSum;

    /**
     * 节点漏洞总数
     */
    @ApiModelProperty("节点漏洞总数")
    private Integer vulnerabilitySum;

    /**
     * 节点可控制级别
     */
    @ApiModelProperty("节点可控制级别")
    private Integer controllableLevel;

    private static final long serialVersionUID = 1L;
}
