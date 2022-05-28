package edu.scu.csaserver.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 将 Category LinkInfo NodeInfo 组装起来
 * 供前端绘制网络拓扑图
 * @author Lifeng
 * @date 2021/6/25 19:42
 */
@Data
public class Graph implements Serializable {
    private static final long serialVersionUID = 2599753147171110921L;
    /**
     * 相当于拓扑图的子网
     */
    @ApiModelProperty("相当于拓扑图的子网")
    private List<Category> categories;

    /**
     * 所有节点
     */
    @ApiModelProperty("所有节点")
    private List<NodeInfo> nodes;

    /**
     * 所有边
     */
    @ApiModelProperty("所有边")
    private List<LinkInfo> links;

    public Graph () {
        this.categories = new ArrayList<>();
        this.nodes = new ArrayList<>();
        this.links = new ArrayList<>();
    }
}
