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
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 服务端口
     */
    private Integer servicePort;

    /**
     * 服务版本
     */
    private String serviceVersion;

    /**
     * 服务漏洞数量
     */
    private Integer serviceVulnerabilitySum;

    /**
     * 服务对应的安全等级（可选）
     */
    private String serviceSafetyLevel;

    /**
     * 服务创建时间
     */
    private Date gmtCreate;

    /**
     * 服务修改时间
     */
    private Date gmtModified;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}