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
 * @TableName link
 */
@TableName(value ="link")
@Data
public class Link implements Serializable {
    /**
     * 连接详情id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 源节点id
     */
    @TableField(value = "source_node_id")
    private Integer source_node_id;

    /**
     * 目的节点id
     */
    @TableField(value = "target_node_id")
    private Integer target_node_id;

    /**
     * 连接名称
     */
    @TableField(value = "link_name")
    private String link_name;

    /**
     * 连接带宽
     */
    @TableField(value = "bandwidth")
    private Double bandwidth;

    /**
     * 连接时延
     */
    @TableField(value = "delay")
    private Double delay;

    /**
     * 丢包率
     */
    @TableField(value = "lose_package")
    private Integer lose_package;

    /**
     * 信噪比
     */
    @TableField(value = "sn")
    private Double sn;

    /**
     * 连接类型（有线，无线）
     */
    @TableField(value = "link_type")
    private Integer link_type;

    /**
     * 频段占用率（可选）
     */
    @TableField(value = "spectrum_utilization")
    private Integer spectrum_utilization;

    /**
     * 信道电路数（可选）
     */
    @TableField(value = "channel_sum")
    private Integer channel_sum;

    /**
     * 连接逻辑类型(协议层次)
     */
    @TableField(value = "protocol_level")
    private Integer protocol_level;

    /**
     * 连接服务类型（可选, 协议类型）
     */
    @TableField(value = "protocol_type")
    private Integer protocol_type;

    /**
     * 连接初始权重
     */
    @TableField(value = "init_weight")
    private Integer init_weight;

    /**
     * 连接创建时间
     */
    @TableField(value = "gmt_create")
    private Date gmt_create;

    /**
     * 连接修改时间
     */
    @TableField(value = "gmt_modified")
    private Date gmt_modified;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}