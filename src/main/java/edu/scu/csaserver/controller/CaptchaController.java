package edu.scu.csaserver.controller;

import com.wf.captcha.GifCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 验证码登录模块
 * @author Lifeng
 * @date 2021/7/12 12:18
 */
@RestController
@RequestMapping("/auth")
@Api(tags = "认证授权")
public class CaptchaController {

    @CrossOrigin
    @GetMapping("/captcha")
    @ApiOperation("获取验证码")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 设置请求头为输出图片类型
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        // 生成验证码
        GifCaptcha captcha = new GifCaptcha(130, 48, 5);
        String verCode = captcha.text().toLowerCase();
        String key = UUID.randomUUID().toString();
        // 存入session,用于下一步登录验证
        request.getSession().setAttribute(key, verCode);
        captcha.out(response.getOutputStream());
    }
}
