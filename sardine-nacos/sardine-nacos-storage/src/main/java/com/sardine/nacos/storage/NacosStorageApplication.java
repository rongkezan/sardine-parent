package com.sardine.nacos.storage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Nacos Application
 *
 * @author keith
 */
@SpringBootApplication
@MapperScan("com.sardine.nacos.storage.mapper")
public class NacosStorageApplication {
    public static void main(String[] args) {
        SpringApplication.run(NacosStorageApplication.class, args);
    }
}
