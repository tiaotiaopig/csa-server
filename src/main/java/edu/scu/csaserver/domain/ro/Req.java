package edu.scu.csaserver.domain.ro;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 前端请求数据的封装
 * @author Lifeng
 * @date 2021/7/10 15:08
 */
@Data
public class Req<T> {

    /**
     * 请求口令，用于用户验证
     */
    @ApiModelProperty("请求口令")
    private String token;
    /**
     * 请求参数封装
     */
    @ApiModelProperty("请求参数json")
    private T params;
}
