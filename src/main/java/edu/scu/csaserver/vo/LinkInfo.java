package edu.scu.csaserver.vo;

import edu.scu.csaserver.domain.Link;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 这里我们将连接（边）的信息转化为前端需要的形式
 * 最重要的是 source 和 target
 * 为了前端展示，特意封装了一下
 * @author Lifeng
 * @date 2021/6/25 19:36
 */
@Data
public class LinkInfo implements Serializable {

    /**
     * 源节点（相当于id）
     */
    @ApiModelProperty("源节点（相当于id）")
    private String source;

    /**
     * 目标节点（相当于id）
     */
    @ApiModelProperty("目标节点（相当于id）")
    private String target;

    @ApiModelProperty("链路预测权重")
    private String weight;

    /**
     * link 实体类
     */
    @ApiModelProperty("连接详情（link实体）")
    private Link link;

    private static final long serialVersionUID = 1L;
}
