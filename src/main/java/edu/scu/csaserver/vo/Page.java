package edu.scu.csaserver.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 用于封装供layui分页结果
 * @author lifeng
 * @date 2021/7/20 下午2:32
 */
@Data
public class Page<T> {
    /**
     * 响应状态码
     */
    @ApiModelProperty("响应状态码")
    private Integer code;
    /**
     * 响应信息
     */
    @ApiModelProperty("响应信息")
    private String msg;
    /**
     * 节点总数
     */
    @ApiModelProperty("节点总数")
    private Integer count;
    /**
     * 分页结果
     */
    @ApiModelProperty("响应数据")
    private List<T> data;
}
