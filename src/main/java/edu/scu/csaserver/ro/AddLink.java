package edu.scu.csaserver.ro;

import edu.scu.csaserver.domain.Link;
import edu.scu.csaserver.domain.Node;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用于封装前端添加连接的请求
 * 主要是添加连接时,还要确定子网和连接的关系
 * @author lifeng
 * @date 2021/7/21 下午3:27
 */
@Data
public class AddLink {
    /**
     * 子网id
     */
    @ApiModelProperty("所属子网id")
    private Integer subId;

    /**
     * 待添加的连接数据
     */
    @ApiModelProperty("待添加的节点数据")
    private Link link;
}
