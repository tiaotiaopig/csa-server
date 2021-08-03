package edu.scu.csaserver.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用于前端展示每个节点上运行的服务及漏洞数量
 * @author lifeng
 * @date 2021/7/22 下午3:52
 */
@Data
public class ServiceVul implements Serializable {
    private static final long serialVersionUID = 9095769254908004958L;
    /**
     * 所有服务的名称,和data中的nums要一一对应
     */
    @ApiModelProperty("所有服务的名称")
    private List<String> services;

    /**
     * 节点服务漏洞数组
     */
    @ApiModelProperty("节点服务漏洞数组")
    private List<NodeVulNum> data;
}
