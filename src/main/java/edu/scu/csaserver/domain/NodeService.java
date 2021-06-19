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
 * @TableName node_service
 */
@TableName(value ="node_service")
@Data
public class NodeService implements Serializable {
    /**
     * 节点和服务关系id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 节点id
     */
    @TableField(value = "node_id")
    private Integer node_id;

    /**
     * 服务id
     */
    @TableField(value = "service_id")
    private Integer service_id;

    /**
     * 关系创建时间
     */
    @TableField(value = "gmt_create")
    private Date gmt_create;

    /**
     * 服务可运用能力（可破坏，可利用，可控制）
     */
    @TableField(value = "service_controllable")
    private String service_controllable;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}