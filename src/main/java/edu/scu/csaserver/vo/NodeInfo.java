package edu.scu.csaserver.vo;

import edu.scu.csaserver.domain.Node;
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
     * 节点详情
     */
    @ApiModelProperty("节点详情（节点实体类）")
    private Node node;

    private static final long serialVersionUID = 1L;
}
