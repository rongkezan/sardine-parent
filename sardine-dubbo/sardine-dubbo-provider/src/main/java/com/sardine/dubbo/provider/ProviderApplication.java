package com.sardine.dubbo.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Only dubbo provider need config '@EnableDubbo'
 * to scan '@DubboService' in specified packages.
 *
 * @author keith
 */
@SpringBootApplication
@EnableDubbo(scanBasePackages = "com.sardine.dubbo.provider")
public class ProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }
}
