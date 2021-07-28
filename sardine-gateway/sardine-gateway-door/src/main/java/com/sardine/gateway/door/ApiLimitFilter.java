package com.sardine.gateway.door;

import com.sardine.gateway.door.service.RateLimiterService;
import com.sardine.gateway.door.service.RateLimiterServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RRateLimiter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author keith
 */
@Slf4j
@Component
public class ApiLimitFilter implements GlobalFilter, Ordered {

    @Resource
    private RateLimiterService rateLimiterService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        HttpHeaders headers = request.getHeaders();
        String url = request.getURI().getPath();
        String userId = Optional.ofNullable(headers.get("token")).map(list -> list.get(0)).orElse("");
        String ip = IpUtils.getIpAddress(request);
        RRateLimiter rRateLimiter = rateLimiterService.getRateLimiter(ip, url);
        if (!rRateLimiter.tryAcquire()) {
            log.info("Request too much. Ip = {}, UserId = {}, Url = {}", ip, userId, url);
            return Monos.writeFailure(response);
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
