package edu.scu.csaserver.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用来接收 service（节点上的服务）的统计信息
 * @author Lifeng
 * @date 2021/7/14 12:39
 */
@Data
public class ServiceCount {
    /**
     * 服务id索引
     */
    @ApiModelProperty("服务id索引")
    private Integer serviceId;
    /**
     * 服务的名称
     */
    @ApiModelProperty("服务的名称")
    private String serviceName;
    /**
     * 服务的数量
     */
    @ApiModelProperty("服务的数量")
    private Integer serviceCount;

}
