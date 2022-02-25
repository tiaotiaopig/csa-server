package edu.scu.csaserver.controller;

import edu.scu.csaserver.utils.HttpUtil;
import edu.scu.csaserver.utils.SecurityUtil;
import edu.scu.csaserver.vo.CyberSkyArg;
import edu.scu.csaserver.vo.Res;
import edu.scu.csaserver.vo.ResCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Map;

@Api(tags = "CyberSky接口转发")
@RestController
public class CyberSkyController {

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private HttpUtil httpUtil;

    @Value("${self.cyber-sky.secret}")
    private String secret;

    @Value("${self.cyber-sky.username}")
    private String username;

    /**
     * 获取CyberSky的token
     *
     * @return
     */
    @ApiOperation(value = "token刷新", notes = "获取cyberSky server token")
    @PostMapping("/cyberSky/access")
    public Res access() {
        String timestamp = System.currentTimeMillis() + "";
        byte[] bytes = new SecureRandom().generateSeed(16);
        String nonce = securityUtil.bytesToHex(bytes);
        String[] array = new String[]{
                nonce,
                secret,
                timestamp
        };
        Arrays.sort(array);

        String sig = securityUtil.encodeSHA256(securityUtil.join(array));
        MultiValueMap map = new LinkedMultiValueMap<>();
        map.add("username", username);
        map.add("signature", sig);
        map.add("timestamp", timestamp);
        map.add("nonce", nonce);
        String token = httpUtil.accessToken("/token/access", map);
        token = token.substring(1, token.length() - 1);
        return new Res<String>(200, "success", token);
    }
//
//    /**
//     * 获取设备类型字典表
//     * GET /api/normaliz/getAllDevtypeDict
//     * data
//     * 字符串 代表获取失败
//     * 字典 获取成功
//     */
//    @PostMapping("/cyberSky/getAllDevtypeDict")
//    public Res getAllDevTypeDict(@RequestParam(value = "cyberToken", required = true) String token) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + token);
//        try {
//            ResponseEntity response = httpUtil.get("/normaliz/getAllDevtypeDict", headers, null);
//            if (response.getBody() instanceof Map) {
//                return Res.success(response.getBody());
//            } else {
//                return Res.fail(401, response.getBody().toString());
//            }
//        } catch (Exception e) {
//            return Res.fail(401, e.getMessage());
//        }
//    }
    @ApiOperation(value = "转发请求", notes = "转发请求至cyberSky server")
    @PostMapping("/cyberSky")
    public Res query(@RequestBody CyberSkyArg arg) {
        HttpHeaders headers = new HttpHeaders();
        if (arg.getCyberToken() != null && arg.getCyberToken().length() > 0) {
            headers.add("Authorization", "Bearer " + arg.getCyberToken());
        }
        try {
            ResponseEntity response;
            System.out.println("data:"+arg.getData());
            String method = "get";
            if (arg.getMethod() != null) {
                method = arg.getMethod();
            }
            if ("post".equals(method.toLowerCase())) {
                response = httpUtil.post(arg.getUrl(), headers, arg.getData());
            } else {
                response = httpUtil.get(arg.getUrl(), headers, arg.getData());
            }
            if (response.getBody() == null){
                return Res.success(ResCode.SUCCESS,null);
            }
            return Res.success(response.getBody());
//            else if (response.getBody() instanceof Map) {
//                return Res.success(response.getBody());
//            } else {
//                return Res.fail(response.getStatusCodeValue(), response.getBody().toString());
//            }
        } catch (Exception e) {
            return Res.fail(401, e.getMessage());
        }

    }

}
