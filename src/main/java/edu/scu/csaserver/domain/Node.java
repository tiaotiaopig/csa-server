package edu.scu.csaserver.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
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
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 节点IP地址(可选)
     */
    @TableField(value = "node_ip")
    private String node_ip;

    /**
     * 节点MAC（可选）
     */
    @TableField(value = "node_mac")
    private String node_mac;

    /**
     * 节点vlan(可选)
     */
    @TableField(value = "node_vlan")
    private Integer node_vlan;

    /**
     * 节点名称
     */
    @TableField(value = "node_name")
    private String node_name;

    /**
     * 节点物理类型
     */
    @TableField(value = "physical_type")
    private Integer physical_type;

    /**
     * 节点逻辑类型
     */
    @TableField(value = "logical_type")
    private Integer logical_type;

    /**
     * 频谱下限
     */
    @TableField(value = "spectrum_floor")
    private Double spectrum_floor;

    /**
     * 频谱上限
     */
    @TableField(value = "spectrum_top")
    private Double spectrum_top;

    /**
     * 频谱可利用率
     */
    @TableField(value = "spectrum_availability")
    private Integer spectrum_availability;

    /**
     * 吞吐量
     */
    @TableField(value = "throughput")
    private Integer throughput;

    /**
     * 节点计算性能
     */
    @TableField(value = "compute_performance")
    private Integer compute_performance;

    /**
     * 硬件类型
     */
    @TableField(value = "hardware_type")
    private Integer hardware_type;

    /**
     * 节点服务总数
     */
    @TableField(value = "service_sum")
    private Integer service_sum;

    /**
     * 节点漏洞总数
     */
    @TableField(value = "vulnerability_sum")
    private Integer vulnerability_sum;

    /**
     * 节点可控制级别
     */
    @TableField(value = "controllable_level")
    private Integer controllable_level;

    /**
     * 节点创建时间
     */
    @TableField(value = "gmt_create")
    private Date gmt_create;

    /**
     * 节点修改时间
     */
    @TableField(value = "gmt_modified")
    private Date gmt_modified;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}