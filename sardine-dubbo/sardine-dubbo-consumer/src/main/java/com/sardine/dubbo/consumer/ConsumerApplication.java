package com.sardine.dubbo.consumer;

import com.sardine.dubbo.api.ProviderService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author keith
 */
@RestController
@SpringBootApplication
public class ConsumerApplication {

    @DubboReference(version = "1.0.0", check = false)
    private ProviderService providerService;

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @GetMapping("hello")
    public String hello(){
        return providerService.hello();
    }
}
