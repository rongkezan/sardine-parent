package com.sardine.nacos;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author keith
 */
@SpringBootApplication
@EnableDubbo(scanBasePackages = "com.sardine.nacos")
public class Nacos2Application {
    public static void main(String[] args) {
        SpringApplication.run(Nacos2Application.class, args);
    }
}
