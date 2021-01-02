package com.sardine.gateway.config;

import com.sardine.gateway.prop.AuthProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.Resource;

/**
 * 全局跨域配置
 * @author keith
 */
@Configuration
public class GlobalCorsConfigurer {

    @Resource
    AuthProperties authProperties;

    @Bean
    public CorsFilter corsFilter(){
        CorsConfiguration config = new CorsConfiguration();

        // 允许的域
        for (String allowOrigin : authProperties.getAllowedOrigins())
            config.addAllowedOrigin(allowOrigin);

        // 允许请求方式
        for (String allowedMethod : authProperties.getAllowedMethods())
            config.addAllowedMethod(allowedMethod);

        // 是否允许发送Cookie
        config.setAllowCredentials(authProperties.getAllowedCredentials());

        // 允许头信息
        config.addAllowedHeader(authProperties.getAllowedHeader());

        // 第一次请求最大缓存时间
        config.setMaxAge(authProperties.getMaxAge());

        // 添加映射路径
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**", config);
        return new CorsFilter(configurationSource);
    }
}
