package edu.scu.csaserver.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName node
 */
@TableName(value ="node")
@Data
public class Node implements Serializable {
    /**
     * 节点详细信息id索引
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 节点IP地址(可选)
     */
    private String nodeIp;

    /**
     * 节点MAC（可选）
     */
    private String nodeMac;

    /**
     * 节点vlan(可选)
     */
    private String nodeVlan;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 节点物理类型
     */
    private Integer physicalType;

    /**
     * 节点逻辑类型
     */
    private Integer logicalType;

    /**
     * 频谱下限
     */
    private Double spectrumFloor;

    /**
     * 频谱上限
     */
    private Double spectrumTop;

    /**
     * 频谱可利用率
     */
    private Double spectrumAvailability;

    /**
     * 吞吐量
     */
    private Integer throughput;

    /**
     * 节点计算性能
     */
    private Integer computePerformance;

    /**
     * 硬件类型
     */
    private Integer hardwareType;

    /**
     * 节点服务总数
     */
    private Integer serviceSum;

    /**
     * 节点漏洞总数
     */
    private Integer vulnerabilitySum;

    /**
     * 节点可控制级别
     */
    private Integer controllableLevel;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}