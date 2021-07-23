package com.sardine.gateway.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author keith
 */
@Data
@Component
@ConfigurationProperties("sardine.gateway.auth")
public class AuthProperties {

    /** Token名称 */
    private String tokenName = "SardineToken";

    /** 是否允许发送Cookie */
    private Boolean allowedCredentials = true;

    /** 允许的头信息 */
    private String allowedHeader = "*";

    /** 第一次请求最大缓存时间 */
    private Long maxAge = 3600L;

    /** 允许不通过网关的路径 */
    private List<String> allowedPaths = Collections.singletonList("/api/backdoor");

    /** 允许访问的域名 */
    private List<String> allowedOrigins = Collections.singletonList("http://127.0.0.1:8080");

    /** 允许的请求方式 */
    private List<String> allowedMethods = Arrays.asList("OPTIONS", "HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
}
