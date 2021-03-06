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
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 网络 id
     */
    private Integer subNetworkId;

    /**
     * 边 id
     */
    private Integer linkId;

    /**
     * 关系创建时间
     */
    private Date gmtCreate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public SubNetworkLink() {}

    public SubNetworkLink(Integer subNetworkId, Integer linkId) {
        this.linkId = linkId;
        this.subNetworkId = subNetworkId;
    }
}