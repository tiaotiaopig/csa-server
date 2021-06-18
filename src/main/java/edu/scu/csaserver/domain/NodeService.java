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
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 节点id
     */
    private Integer nodeId;

    /**
     * 服务id
     */
    private Integer serviceId;

    /**
     * 关系创建时间
     */
    private Date createTime;

    /**
     * 服务可运用能力（可破坏，可利用，可控制）
     */
    private String serviceControllable;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}