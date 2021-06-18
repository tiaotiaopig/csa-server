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
    @TableId
    private Integer id;

    /**
     * 子网id
     */
    private Integer subNetworkId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}