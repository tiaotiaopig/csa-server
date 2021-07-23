package edu.scu.csaserver.ro;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用于接受前端用户登录请求的封装
 * @author lifeng
 * @date 2021/7/23 上午11:29
 */
@Data
public class LoginUser {
    /**
     * 登录用户名
     */
    @ApiModelProperty("登录用户名")
    private String username;

    /**
     * 登录密码
     */
    @ApiModelProperty("登录密码")
    private String password;

    /**
     * 登录验证码
     */
    @ApiModelProperty("登录验证码")
    private String verCode;
}
