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
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 网络id
     */
    @TableField(value = "sub_network_id")
    private Integer sub_network_id;

    /**
     * 边id
     */
    @TableField(value = "link_id")
    private Integer link_id;

    /**
     * 关系创建时间
     */
    @TableField(value = "gmt_create")
    private Date gmt_create;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}