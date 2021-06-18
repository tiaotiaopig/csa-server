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
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 源节点id
     */
    private Integer sourceNodeId;

    /**
     * 目的节点id
     */
    private Integer targetNodeId;

    /**
     * 连接名称
     */
    private String linkName;

    /**
     * 连接带宽
     */
    private Double bandwidth;

    /**
     * 连接时延
     */
    private Double delay;

    /**
     * 丢包率
     */
    private Double losePackage;

    /**
     * 信噪比
     */
    private Double sn;

    /**
     * 连接类型（有线，无线）
     */
    private String linkType;

    /**
     * 频段占用率（可选）
     */
    private Double spectrumUtilization;

    /**
     * 信道电路数（可选）
     */
    private Integer channelSum;

    /**
     * 连接逻辑类型(协议层次)
     */
    private String protocolLevel;

    /**
     * 连接服务类型（可选, 协议类型）
     */
    private String protocolType;

    /**
     * 连接初始权重
     */
    private Double initWeight;

    /**
     * 连接创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}