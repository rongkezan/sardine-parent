package com.sardine.cookbook.app.configurer;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RetryRule;
import com.sardine.cookbook.app.controller.LoggingClientHttpRequestInterceptor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author keith
 */
@Configuration
public class RestTemplateConfigurer {
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new LoggingClientHttpRequestInterceptor());
        return restTemplate;
    }

    @Bean
    public IRule myRule() {
        //return new RoundRobinRule();
        //return new RandomRule();
        return new RetryRule();
    }
}
