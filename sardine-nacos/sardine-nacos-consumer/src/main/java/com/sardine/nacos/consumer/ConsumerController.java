package com.sardine.nacos.consumer;

import com.sardine.nacos.provider.api.ProviderService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author keith
 */
@RestController
public class ConsumerController {

    @DubboReference
    private ProviderService providerService;

    @GetMapping("test")
    public String test(){
        return providerService.hello();
    }
}
