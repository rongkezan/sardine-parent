package com.sardine.gateway.door.filter;

import com.sardine.gateway.door.service.RateLimiterService;
import com.sardine.gateway.door.utils.IpUtils;
import com.sardine.utils.JacksonUtils;
import com.sardine.utils.http.Results;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RRateLimiter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * 全局限流过滤器
 *
 * @author keith
 */
@Slf4j
@Component
public class GlobalApiLimitFilter implements GlobalFilter, Ordered {

    @Resource
    private RateLimiterService rateLimiterService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String url = request.getURI().getPath();
        String ip = IpUtils.getIpAddress(request);
        RRateLimiter rRateLimiter = rateLimiterService.getRateLimiter(ip, url);
        if (!rRateLimiter.tryAcquire()) {
            log.info("Request limited, ip = {}, url = {}", ip, url);
            response.setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
            DataBuffer buffer = response.bufferFactory().wrap(JacksonUtils.toJson(Results.failed("Your request has been limited.")).getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(buffer));
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
