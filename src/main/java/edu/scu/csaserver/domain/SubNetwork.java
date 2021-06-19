package edu.scu.csaserver.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName sub_network
 */
@TableName(value ="sub_network")
@Data
public class SubNetwork implements Serializable {
    /**
     * 网络id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 网络名称
     */
    @TableField(value = "sub_network_name")
    private String sub_network_name;

    /**
     * 网络物理类型(有线，无线)
     */
    @TableField(value = "physical_type")
    private Integer physical_type;

    /**
     * 网络逻辑类型（星型、总线型等）
     */
    @TableField(value = "logical_type")
    private Integer logical_type;

    /**
     * 节点数量
     */
    @TableField(value = "node_sum")
    private Integer node_sum;

    /**
     * 边数量
     */
    @TableField(value = "link_sum")
    private Integer link_sum;

    /**
     * 节点连通度
     */
    @TableField(value = "node_connectivity")
    private Integer node_connectivity;

    /**
     * 边连通度
     */
    @TableField(value = "link_connectivity")
    private Integer link_connectivity;

    /**
     * 网络可靠度
     */
    @TableField(value = "reliability")
    private Integer reliability;

    /**
     * 网络脆弱点总数
     */
    @TableField(value = "vulnerability_sum")
    private Integer vulnerability_sum;

    /**
     * 记录创建时间
     */
    @TableField(value = "gmt_create")
    private Date gmt_create;

    /**
     * 记录修改时间
     */
    @TableField(value = "gmt_modified")
    private Date gmt_modified;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}