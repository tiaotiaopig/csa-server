package edu.scu.csaserver.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 这里我们将连接（边）的信息转化为前端需要的形式
 * 最重要的是 source 和 target
 * @author Lifeng
 * @date 2021/6/25 19:36
 */
@Data
public class LinkInfo implements Serializable {

    /**
     * 源节点（相当于id）
     */
    private String source;

    /**
     * 目标节点（相当于id）
     */
    private String target;

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
    private Integer losePackage;

    /**
     * 信噪比
     */
    private Double sn;

    /**
     * 连接类型（有线，无线）
     */
    private Integer linkType;

    /**
     * 频段占用率（可选）
     */
    private Integer spectrumUtilization;

    /**
     * 信道电路数（可选）
     */
    private Integer channelSum;

    /**
     * 连接逻辑类型(协议层次)
     */
    private Integer protocolLevel;

    /**
     * 连接服务类型（可选, 协议类型）
     */
    private Integer protocolType;

    /**
     * 连接初始权重
     */
    private Integer initWeight;

    private static final long serialVersionUID = 1L;
}
