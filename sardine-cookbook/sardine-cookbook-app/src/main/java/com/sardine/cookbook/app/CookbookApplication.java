package com.sardine.cookbook.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author keith
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class CookbookApplication {
    public static void main(String[] args) {
        SpringApplication.run(CookbookApplication.class, args);
    }
}
