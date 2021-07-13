package edu.scu.csaserver.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 服务器给前端响应的通用模板
 * @author Lifeng
 * @date 2021/7/10 15:11
 */
@Data
public class Res<T> {
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
     * 响应数据
     */
    @ApiModelProperty("响应数据")
    private T data;

    public Res(){}

    public Res(Integer code, String msg){
        this.msg = msg;
        this.code = code;
    }
}
