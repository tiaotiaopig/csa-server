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
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 网络名称
     */
    private String subNetworkName;

    /**
     * 网络物理类型(有线，无线)
     */
    private Integer physicalType;

    /**
     * 网络逻辑类型（星型、总线型等）
     */
    private Integer logicalType;

    /**
     * 节点数量
     */
    private Integer nodeSum;

    /**
     * 边数量
     */
    private Integer linkSum;

    /**
     * 节点连通度
     */
    private Integer nodeConnectivity;

    /**
     * 边连通度
     */
    private Integer linkConnectivity;

    /**
     * 网络可靠度
     */
    private Integer reliability;

    /**
     * 网络脆弱点总数
     */
    private Integer vulnerabilitySum;

    /**
     * 记录创建时间
     */
    private Date gmtCreate;

    /**
     * 记录修改时间
     */
    private Date gmtModified;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}