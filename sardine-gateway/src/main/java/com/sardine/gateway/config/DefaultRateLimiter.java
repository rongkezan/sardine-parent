package com.sardine.gateway.config;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.cloud.gateway.filter.ratelimit.AbstractRateLimiter;
import org.springframework.cloud.gateway.support.ConfigurationService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.validation.constraints.DecimalMin;
import java.util.HashMap;
import java.util.Objects;

@Primary
@Component
public class DefaultRateLimiter extends AbstractRateLimiter<DefaultRateLimiter.Config> {

    private final RateLimiter limiter = RateLimiter.create(1);

    public DefaultRateLimiter() {
        super(Config.class, "default-rate-limiter", new ConfigurationService());
    }

    @Override
    public Mono<Response> isAllowed(String routeId, String id) {
        Config config = getConfig().get(routeId);
        limiter.setRate(Objects.isNull(config.getPermitsPerSecond()) ? 1 : config.getPermitsPerSecond());
        boolean isAllow = limiter.tryAcquire();
        return Mono.just(new Response(isAllow, new HashMap<>()));
    }

    public static class Config {

        @DecimalMin("0.1")
        private Double permitsPerSecond;

        public Double getPermitsPerSecond() {
            return permitsPerSecond;
        }

        public Config setPermitsPerSecond(Double permitsPerSecond) {
            this.permitsPerSecond = permitsPerSecond;
            return this;
        }
    }
}
