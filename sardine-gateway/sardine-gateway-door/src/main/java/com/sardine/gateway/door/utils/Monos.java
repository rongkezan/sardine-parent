package com.sardine.gateway.door.utils;

import com.sardine.utils.JacksonUtils;
import com.sardine.utils.http.Result;
import com.sardine.utils.http.Results;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;

import java.nio.charset.StandardCharsets;

/**
 * @author keith
 */
public class Monos {

    public static Mono<Void> writeFailure(ServerHttpResponse response) {
        Result<Void> result = Results.failed();
        DataBuffer buffer = response.bufferFactory().wrap(JacksonUtils.toJson(result).getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }

    public static Mono<Void> writeFailureAndFlush(ServerHttpResponse response) {
        Result<Void> result = Results.failed();
        response.setStatusCode(HttpStatus.BAD_REQUEST);
        response.getHeaders().add("Content-Type", "application/json");
        DataBuffer buffer = response.bufferFactory().wrap(JacksonUtils.toJson(result).getBytes(StandardCharsets.UTF_8));
        return response.writeAndFlushWith(Flux.just(ByteBufFlux.just(buffer)));
    }

    public static Mono<Void> unAuthorizedComplete(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete().then();
    }
}
