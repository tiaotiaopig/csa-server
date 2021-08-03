package edu.scu.csaserver.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用于封装登录时,返回给前端的响应信息
 * @author lifeng
 * @date 2021/7/23 上午11:36
 */
@Data
public class LoginInfo implements Serializable {

    private static final long serialVersionUID = 6449417731835896649L;
    /**
     * sa登录的token
     * 为了在前后端分离情况下，让前端携带token
     * 不然，实现不了登录
     */
    @ApiModelProperty("sa登录的token")
    private String saToken;

    /**
     * 用户名正确1 错误 0
     */
    @ApiModelProperty("用户名正确1 错误 0")
    private Integer userMsg = 0;

    /**
     * 密码正确 1,错误 0
     */
    @ApiModelProperty("密码正确 1,错误 0")
    private Integer pwdMsg = 0;

    /**
     * 验证码正确 1, 错误 0
     */
    @ApiModelProperty("验证码正确 1, 错误 0")
    private Integer captchaMsg = 0;

}
