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
 * @TableName service_net
 */
@TableName(value ="service_net")
@Data
public class ServiceNet implements Serializable {
    /**
     * 服务索引id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 服务名称
     */
    @TableField(value = "service_name")
    private String service_name;

    /**
     * 服务端口
     */
    @TableField(value = "service_port")
    private Integer service_port;

    /**
     * 服务版本
     */
    @TableField(value = "service_version")
    private String service_version;

    /**
     * 服务漏洞数量
     */
    @TableField(value = "service_vulnerability_sum")
    private Integer service_vulnerability_sum;

    /**
     * 服务对应的安全等级（可选）
     */
    @TableField(value = "service_safety_level")
    private String service_safety_level;

    /**
     * 服务创建时间
     */
    @TableField(value = "gmt_create")
    private Date gmt_create;

    /**
     * 服务修改时间
     */
    @TableField(value = "gmt_modified")
    private Date gmt_modified;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}