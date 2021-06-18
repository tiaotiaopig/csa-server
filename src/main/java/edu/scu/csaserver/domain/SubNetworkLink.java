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
 * @TableName sub_network_link
 */
@TableName(value ="sub_network_link")
@Data
public class SubNetworkLink implements Serializable {
    /**
     * 网络-边关系索引id

     */
    @TableId
    private Integer id;

    /**
     * 网络id
     */
    private Integer subNetworkId;

    /**
     * 边id
     */
    private Integer linkId;

    /**
     * 关系创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}