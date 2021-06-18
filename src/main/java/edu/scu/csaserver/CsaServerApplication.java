package edu.scu.csaserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("edu.scu.csaserver.mapper")
public class CsaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CsaServerApplication.class, args);
    }

}
