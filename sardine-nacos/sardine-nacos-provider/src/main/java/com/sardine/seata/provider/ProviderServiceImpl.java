package com.sardine.seata.provider;

import com.sardine.seata.provider.api.ProviderService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class ProviderServiceImpl implements ProviderService {

    @Override
    public String hello() {
        System.out.println("Hello World");
        return "Hello World";
    }
}
