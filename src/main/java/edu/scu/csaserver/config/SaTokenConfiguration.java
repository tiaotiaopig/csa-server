package edu.scu.csaserver.config;

import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author lifeng
 * @date 2021/7/23 下午7:29
 */
// 展示让配置不生效
@Configuration
public class SaTokenConfiguration implements WebMvcConfigurer {

    private StpUtil StpUserUtil;
    /**
     * cors的全局配置，@CrossOrigin 是更细粒度（fine-grained）的方法和controller级的cors
     * 使用cors解决跨域问题，nginx不需要额外配置
     * 为了使前后端分离，我们需要使用跨域资源共享解决跨域问题
     * 主要还是为了让跨域能够带cookie,或者自定义请求头，进行认证授权
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://127.0.0.1:5500", "http://192.168.50.17:6688")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
        // 经测试，允许的额外请求头参数，可以设为"satoken", "content-type"
        // content-type 在登陆时用到啦，为了方便我们还是使用了通配符
    }

    // 注册Sa-Token的拦截器
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // 注册路由拦截器，自定义验证规则
//        registry.addInterceptor(new SaRouteInterceptor((req, res, handler) -> {
//
//            // 登录验证 -- 拦截所有路由，并排除/user/doLogin 用于开放登录
////            SaRouter.match("/**", "/user/doLogin", StpUtil::checkLogin);
//
//            // 这里把预请求给过滤掉
//            // 跨域的自定义请求头，会发送两次请求，但是预请求中不包含自定义请求头
//            // 因此已定义请求头也会鉴权，铁定失败，我们在这里过滤一下
//            // 为了开发方便，放行来自swagger的请求
//            // swagger上发送请求，会有一个自定义请求头，刚好让我们识别来源进行放行
//            if (!"OPTIONS".equals(req.getMethod()) && !"SwaggerBootstrapUi".equals(req.getHeader("request-origion"))) {
//                // 登录验证 -- 排除多个路径
//                SaRouter.match(Collections.singletonList("/**"), Arrays.asList("/user/doLogin", "/user/captcha"), StpUtil::checkLogin);
//            }
////            // 角色认证 -- 拦截以 admin 开头的路由，必须具备[admin]角色或者[super-admin]角色才可以通过认证
////            SaRouter.match("/admin/**", () -> StpUtil.checkRoleOr("admin", "super-admin"));
////
////            // 权限认证 -- 不同模块, 校验不同权限
////            SaRouter.match("/user/**", () -> StpUtil.checkPermission("user"));
////            SaRouter.match("/admin/**", () -> StpUtil.checkPermission("admin"));
////            SaRouter.match("/goods/**", () -> StpUtil.checkPermission("goods"));
////            SaRouter.match("/orders/**", () -> StpUtil.checkPermission("orders"));
////            SaRouter.match("/notice/**", () -> StpUtil.checkPermission("notice"));
////            SaRouter.match("/comment/**", () -> StpUtil.checkPermission("comment"));
////
////            // 匹配 restful 风格路由
////            SaRouter.match("/article/get/{id}", () -> StpUtil.checkPermission("article"));
////
////            // 检查请求方式
////            SaRouter.match("/notice/**", () -> {
////                if(req.getMethod().equals(HttpMethod.GET.toString())) {
////                    StpUtil.checkPermission("notice");
////                }
////            });
////
////            // 提前退出 (执行SaRouter.stop()后会直接退出匹配链)
////            SaRouter.match("/test/back", SaRouter::stop);
////
////            // 在多账号模式下，可以使用任意StpUtil进行校验
////            SaRouter.match("/user/**", () -> StpUserUtil.checkLogin());
//
//        })).addPathPatterns("/**")
//                .excludePathPatterns("/doc.html","/v2/api-docs", "/swagger-resources/configuration/ui",
//                        "/swagger-resources", "/swagger-resources/configuration/security",
//                        "/swagger-ui.html", "/webjars/**", "/images/**", "/layuiadmin/**", "/login.html");
//    }
}
