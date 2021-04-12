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

    @DubboReference(registry = "registryConfigNacos")
    private ProviderService providerService;

    @DubboReference(registry = "registryConfigZookeeper")
    private ProviderService providerService2;

    @GetMapping("test")
    public String test(){
        return providerService.hello();
    }

    @GetMapping("test2")
    public String test2(){
        return providerService2.hello();
    }
}
