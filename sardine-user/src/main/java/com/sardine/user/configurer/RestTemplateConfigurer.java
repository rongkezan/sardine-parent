package com.sardine.user.configurer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author keith
 */
@Configuration
public class RestTemplateConfigurer {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
