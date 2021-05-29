package com.sardine.dubbo.consumer;

import com.sardine.dubbo.api.ProviderService;
import com.sardine.dubbo.api.RemoteService;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class ConsumerApplication {

    @DubboReference
    private ProviderService providerService;

//    @DubboReference
//    private RemoteService remoteService;

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @GetMapping("test")
    public String test(){
        return "Hello World";
    }

    @GetMapping("dubbo")
    public String dubbo(){
        return providerService.hello();
    }

    @GetMapping("remote")
    public String remote(){
//        return remoteService.remoteHello();
        return null;
    }
}
