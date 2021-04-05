package com.sardine.nacos.provider;

import com.sardine.nacos.provider.api.ProviderService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class ProviderServiceImpl implements ProviderService {

    @Override
    public String hello() {
        System.out.println("Hello World");
        return "Hello World";
    }
}
