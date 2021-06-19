package edu.scu.csaserver.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName network
 */
@TableName(value ="network")
@Data
public class Network implements Serializable {
    /**
     * 全局网络id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 子网id
     */
    @TableField(value = "sub_network_id")
    private Integer sub_network_id;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}