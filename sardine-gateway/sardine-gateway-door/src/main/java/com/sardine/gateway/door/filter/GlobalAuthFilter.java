package com.sardine.gateway.door.filter;

import com.sardine.gateway.door.config.AuthProperties;
import com.sardine.utils.JacksonUtils;
import com.sardine.utils.http.Results;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;

/**
 * 全局鉴权过滤器
 *
 * @author keith
 */
@Component
public class GlobalAuthFilter implements GlobalFilter, Ordered {

    @Resource
    private AuthProperties authProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        HttpHeaders headers = request.getHeaders();
        List<String> tokens = headers.get(authProperties.getTokenName());
        if (CollectionUtils.isEmpty(tokens)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            DataBuffer dataBuffer = response.bufferFactory().wrap(JacksonUtils.toJson(Results.failed("No Authorized")).getBytes());
            return response.writeWith(Mono.just(dataBuffer));
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
