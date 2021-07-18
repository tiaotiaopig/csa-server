package edu.scu.csaserver.ro;

import edu.scu.csaserver.domain.Node;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用于封装前端添加节点的请求
 * 主要是添加节点时,还要确定子网和节点的关系
 * @author lifeng
 * @date 2021/7/18 下午2:57
 */
@Data
public class AddedNode {

    /**
     * 子网id
     */
    @ApiModelProperty("所属子网id")
    private Integer subId;

    /**
     * 待添加的节点数据
     */
    @ApiModelProperty("待添加的节点数据")
    private Node node;
}
