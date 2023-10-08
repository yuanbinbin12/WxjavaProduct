package com.ybb;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableRedisRepositories
@SpringBootApplication
@EnableAsync//开启异步注解的方法
@EnableScheduling  //
@EnableKnife4j
public class WxjavaProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxjavaProductApplication.class, args);
    }

}
