package edu.scu.csaserver.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用于封装返给前端的节点服务漏洞数量
 * @author lifeng
 * @date 2021/7/22 下午4:54
 */
@Data
public class NodeVulNum implements Serializable {

    private static final long serialVersionUID = -9054288897870697737L;
    /**
     * 节点名称
     */
    @ApiModelProperty("节点名称")
    private String nodeName;

    /**
     * 服务漏洞数量
     */
    @ApiModelProperty("服务漏洞数量")
    private List<Integer> nums;
}
