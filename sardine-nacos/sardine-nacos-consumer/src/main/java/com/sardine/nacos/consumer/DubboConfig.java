package com.sardine.nacos.consumer;

import com.alibaba.dubbo.config.ReferenceConfig;
import com.sardine.nacos.provider.api.ProviderService;
import org.apache.dubbo.config.ReferenceConfigBase;
import org.apache.dubbo.config.RegistryConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author keith
 */
@Configuration
public class DubboConfig {

    @Bean
    public RegistryConfig registryConfigNacos() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("nacos://127.0.0.1:8848?username=nacos&password=nacos");
        return registryConfig;
    }

    @Bean
    public RegistryConfig registryConfigZookeeper() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://127.0.0.1:2181");
        return registryConfig;
    }
}
