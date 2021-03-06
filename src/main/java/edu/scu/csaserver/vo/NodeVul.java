package edu.scu.csaserver.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 用来接受数据库查询的节点漏洞数量信息
 * @author lifeng
 * @date 2021/7/22 下午4:04
 */
@Data
public class NodeVul implements Serializable {

    private static final long serialVersionUID = 4316515766917981349L;
    /**
     * 节点的名称
     */
    private String nodeName;

    /**
     * 服务id
     */
    private Integer ServiceId;

    /**
     * 服务漏洞数
     */
    private Integer vulSum;
}
