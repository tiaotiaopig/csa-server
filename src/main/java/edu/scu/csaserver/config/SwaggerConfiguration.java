package edu.scu.csaserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Springfox swagger-ui 配置文件
 * @author Lifeng
 * @date 2021/7/7 15:39
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("edu.scu.csaserver.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("李峰","http://www.diqiyuzhou.tk","2807229316@qq.com");
        return new ApiInfoBuilder()
                .title("【网络态势感知Swagger UI】")
                .description("网络态势感知后台接口")
                .contact(contact)
                .version("1.0")
                .build();
    }
}
