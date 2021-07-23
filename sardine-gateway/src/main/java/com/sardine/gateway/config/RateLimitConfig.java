//package com.sardine.gateway.config;
//
//import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//import reactor.core.publisher.Mono;
//
//@Component
//public class RateLimitConfig {
//    @Bean
//    public KeyResolver userKeyResolver(){
//        // 获取请求用户IP作为限流key
//        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
//    }
//}
