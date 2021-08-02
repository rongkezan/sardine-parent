package com.sardine.gateway.door.utils;

import com.sardine.utils.JacksonUtils;
import com.sardine.utils.http.Result;
import com.sardine.utils.http.Results;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @author keith
 */
public class Monos {

    public static Mono<Void> limited(ServerHttpResponse response) {
        Result<Void> result = Results.failed("Your request has been limited.");
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        DataBuffer buffer = response.bufferFactory().wrap(JacksonUtils.toJson(result).getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }
}
