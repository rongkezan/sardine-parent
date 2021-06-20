package com.sardine.dubbo.provider;

import com.sardine.dubbo.api.ProviderService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author keith
 */
@DubboService(version = "1.0.0")
public class ProviderServiceImpl implements ProviderService {

    @Override
    public String hello() {
        System.out.println("Hello World");
        return "Hello World";
    }
}
