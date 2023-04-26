package com.sardine.nacos.account;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.sardine.nacos.account.mapper")
@EnableFeignClients("com.sardine.nacos.api")
@ComponentScan("com.sardine.nacos")
public class NacosAccountApplication {
    public static void main(String[] args) {
        SpringApplication.run(NacosAccountApplication.class, args);
    }
}
