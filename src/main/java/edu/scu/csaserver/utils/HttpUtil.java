package edu.scu.csaserver.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


import java.util.Iterator;
import java.util.Map;

//interface CyberSky {
//    @RequestLine("POST /token/access")
//    @Headers({"Content-Type:application/x-www-form-urlencoded"})
//    ApiResponse access(CyberAccess access);
//
//}

@Component
public class HttpUtil {

    @Value("${self.cyber-sky.url}")
    private String cyberSkyUrl;


    /*
        获取cyberSky token
     */
    public String accessToken(String api,MultiValueMap map){
        String url = cyberSkyUrl+api;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, map, String.class);
        return responseEntity.getBody();
    }

    public ResponseEntity  post(String api, HttpHeaders headers, Map data){
        String url = cyberSkyUrl + api;
        RestTemplate restTemplate= new RestTemplate();
        HttpEntity entity= new HttpEntity<>(data, headers);
        ResponseEntity response =  restTemplate.exchange(url, HttpMethod.POST,entity,Object.class);
        return response;
    }
    public ResponseEntity get(String api,HttpHeaders headers,Map data){
        String url = cyberSkyUrl + api;
        RestTemplate restTemplate= new RestTemplate();
        StringBuffer stringBuffer = new StringBuffer(url);//拼接url
        if (data!=null) {
            Iterator iterator = data.entrySet().iterator();
            if (iterator.hasNext()) {
                stringBuffer.append("?");
                Object element;
                while (iterator.hasNext()) {
                    element = iterator.next();
                    Map.Entry entry = (Map.Entry) element;
                    if (entry.getValue() != null) {
                        stringBuffer.append(element).append("&");
                    }
                    url = stringBuffer.substring(0, stringBuffer.length() - 1);
                }
            }
        }
        HttpEntity entity= new HttpEntity<>(data, headers);
        ResponseEntity response =  restTemplate.exchange(url, HttpMethod.GET,entity,Object.class);
        return response;
    }

}
