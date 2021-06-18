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
 * @TableName sub_network_node
 */
@TableName(value ="sub_network_node")
@Data
public class SubNetworkNode implements Serializable {
    /**
     * 网络节点关系索引id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 网络id
     */
    private Integer subNetworkId;

    /**
     * 节点id
     */
    private Integer nodeId;

    /**
     * 关系创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}