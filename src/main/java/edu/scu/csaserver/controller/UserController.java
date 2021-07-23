package edu.scu.csaserver.controller;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.session.SaSessionCustomUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.wf.captcha.GifCaptcha;
import edu.scu.csaserver.ro.LoginUser;
import edu.scu.csaserver.ro.Req;
import edu.scu.csaserver.vo.LoginInfo;
import edu.scu.csaserver.vo.Res;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Random;

/**
 * 我们来简单实现一下认证功能
 * 至于鉴权以后有机会再实现吧
 * @author lifeng
 * @date 2021/7/22 下午2:48
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
@Api(tags = "用户登录鉴权")
public class UserController {
    private String verCode;

    @PostMapping("/doLogin")
    @ApiOperation("用户登录")
    public Res<LoginInfo> doLogin(@RequestBody @ApiParam(value = "前端登录用户", required = true) Req<LoginUser> req, HttpServletRequest request) {
        LoginUser loginUser = req.getParams();
        LoginInfo loginInfo = new LoginInfo();
        if (verCode == null || !verCode.equals(loginUser.getVerCode())) {
            return new Res<>(100, "验证码失败", loginInfo);
        }
        loginInfo.setCaptchaMsg(1);
        if (!"zhangsan".equals(loginUser.getUsername())) {
            return new Res<>(100, "用户名有误", loginInfo);
        }
        loginInfo.setUserMsg(1);
        if (!"123456".equals(loginUser.getPassword())) {
            return new Res<>(100, "密码有误", loginInfo);
        }
        loginInfo.setPwdMsg(1);
        // sa登录
        StpUtil.login(10001);
        return new Res<>(200, "登录成功", loginInfo);
    }

    @GetMapping("/captcha")
    @ApiOperation("获取验证码")
    public void captcha(HttpServletResponse response) throws Exception {
        // 设置请求头为输出图片类型
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        // 生成验证码
        GifCaptcha captcha = new GifCaptcha(130, 48, 5);
        verCode = captcha.text().toLowerCase();
        captcha.out(response.getOutputStream());
    }

    @GetMapping("/doLogout")
    @ApiOperation("用户登出")
    public void doLogout() {
        StpUtil.logout();
    }
}
