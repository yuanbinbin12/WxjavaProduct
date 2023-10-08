package com.ybb.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;

@EnableSwagger2
@Configuration
@EnableKnife4j
@ConditionalOnProperty(name = "pinda.swagger.enabled", havingValue = "true",matchIfMissing = true)
public class swaggerConfig {
    @Bean
    public Docket createRestApi(Environment environment){
        String[] activeProfiles = environment.getActiveProfiles();
        ArrayList<String> arrays = new ArrayList<>(Arrays.asList(activeProfiles));
        boolean isDev = arrays.contains("dev");
        System.out.println("环境为："+isDev);
        Docket docket = new Docket(DocumentationType.SWAGGER_2).
                apiInfo(apiInfo()).
                enable(isDev).select().
                apis(RequestHandlerSelectors.basePackage("com.ybb.testController")).
                build();
        return docket;
    }
    //配置Swagger的信息
    private ApiInfo apiInfo() {
        //作者信息
        Contact contact = new Contact("苑彬彬", "https://www.baidu.com", "447422351@qq.com");
        return new ApiInfo("苑彬彬的swaggerApi文档", "swagger文档详情咨询yuanbinbin", "1.0", "https://www.baidu.com", contact, "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList());
    }
}
