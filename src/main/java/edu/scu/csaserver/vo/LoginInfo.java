package edu.scu.csaserver.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用于封装登录时,返回给前端的响应信息
 * @author lifeng
 * @date 2021/7/23 上午11:36
 */
@Data
public class LoginInfo {

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
