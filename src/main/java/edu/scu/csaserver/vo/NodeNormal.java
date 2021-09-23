package edu.scu.csaserver.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 包装一下节点属性归一化后的值
 * @author Lifeng
 * @date 2021/9/22 19:07
 */

@Data
public class NodeNormal implements Serializable {

    private static final long serialVersionUID = 7782659380157656752L;
    /**
     * 节点id
     */
    private Integer nodeId;

    /**
     * 吞吐量归一化值
     */
    private Float throughout;

    /**
     * 计算性能归一化值
     */
    private Float cp;

    /**
     * 服务数量归一化值
     */
    private Float serviceNum;

    /**
     * 漏洞数量归一化值
     */
    private Float vul;
}
