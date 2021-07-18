package edu.scu.csaserver.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @TableName sub_network_node
 */
@TableName(value ="sub_network_node")
@Data
public class SubNetworkNode implements Serializable {
    /**
     * 网络节点关系索引id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("网络节点关系索引id")
    private Integer id;

    /**
     * 网络id
     */
    @ApiModelProperty("网络id")
    private Integer subNetworkId;

    /**
     * 节点id
     */
    @ApiModelProperty("节点id")
    private Integer nodeId;

    /**
     * 关系创建时间
     */
    @ApiModelProperty("关系创建时间")
    private Date gmtCreate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public SubNetworkNode() {}

    public SubNetworkNode(Integer subNetworkId, Integer nodeId) {
        this.subNetworkId = subNetworkId;
        this.nodeId = nodeId;
    }
}