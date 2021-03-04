package com.sardine.registry;

import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Properties;

/**
 * @author keith
 */
@SpringBootApplication
@EnableEurekaServer
public class RegistryApplication implements ApplicationContextAware {
    public static void main(String[] args) {
        SpringApplication.run(RegistryApplication.class, args);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext.publishEvent();
    }
}
