package com.sardine.nacos.storage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * Nacos Application
 *
 * @author keith
 */
@SpringBootApplication
@MapperScan("com.sardine.nacos.storage.mapper")
@EnableFeignClients("com.sardine.nacos.api")
@ComponentScan("com.sardine.nacos")
public class NacosStorageApplication {
    public static void main(String[] args) {
        SpringApplication.run(NacosStorageApplication.class, args);
    }
}
