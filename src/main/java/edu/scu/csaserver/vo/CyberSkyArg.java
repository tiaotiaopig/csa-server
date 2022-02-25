package edu.scu.csaserver.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.util.MultiValueMap;

import java.util.Map;


@Data
@AllArgsConstructor
public class CyberSkyArg {
    private String cyberToken;
    private String url;
    private Map data;
    private String method;
}
