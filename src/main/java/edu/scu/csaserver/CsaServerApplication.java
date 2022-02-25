package edu.scu.csaserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("edu.scu.csaserver.mapper")
@EnableFeignClients
public class CsaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CsaServerApplication.class, args);
    }

}
