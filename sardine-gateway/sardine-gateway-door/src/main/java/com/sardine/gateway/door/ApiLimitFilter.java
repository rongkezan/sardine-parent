package com.sardine.gateway.door;

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

import java.util.Optional;

/**
 * @author keith
 */
@Slf4j
@Component
public class ApiLimitFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        HttpHeaders headers = request.getHeaders();
        String url = request.getURI().getPath();
        String userId = Optional.ofNullable(headers.get("token")).map(list -> list.get(0)).orElse("");
        String ip = IpUtils.getIpAddress(request);
        RateLimiterDto limiterDto = new RateLimiterDto();
        limiterDto.setApi(url);
        limiterDto.setUserId(userId);
        limiterDto.setIp(ip);
        RRateLimiter rRateLimiter = RateLimiterFactory.getRateLimiter(limiterDto);
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
