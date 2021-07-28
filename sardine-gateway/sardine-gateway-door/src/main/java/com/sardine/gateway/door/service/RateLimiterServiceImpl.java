package com.sardine.gateway.door.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.sardine.gateway.door.LimiterConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.RedissonRateLimiter;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RedissonClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author keith
 */
@Slf4j
@Component
@EnableConfigurationProperties(LimiterConfigProperties.class)
@SuppressWarnings("all")
public class RateLimiterServiceImpl implements RateLimiterService {

    @Resource
    private LimiterConfigProperties limiterConfigProperties;

    @Resource
    private RedissonClient redissonClient;

    private static final Map<String, LimiterConfigProperties.LimitRule> LIMITER_RULES = new HashMap<>();

    private static final Cache<String, RRateLimiter> LIMITER_INSTANCES = CacheBuilder.newBuilder().maximumSize(100000).expireAfterAccess(10, TimeUnit.MINUTES).build();

    @PostConstruct
    public void load() {
        List<LimiterConfigProperties.LimitRule> limitRules = limiterConfigProperties.getLimitRules();
        limitRules.forEach(lc -> LIMITER_RULES.put(lc.getUrl(), lc));
    }

    @Override
    public RRateLimiter getRateLimiter(String ip, String url) {
        try {
            return LIMITER_INSTANCES.get(getKey(ip, url), () -> createRateLimiter(ip, url));
        } catch (ExecutionException e) {
            log.error("Get rate limiter from cache failed.", e);
        }
        return createRateLimiter(ip, url);
    }

    private RRateLimiter createRateLimiter(String ip, String url) {
        Redisson redisson = (Redisson) redissonClient;
        RedissonRateLimiter rateLimiter = new RedissonRateLimiter(redisson.getCommandExecutor(), getKey(ip, url));
        LimiterConfigProperties.LimitRule limitRule = LIMITER_RULES.get(url);
        rateLimiter.setRate(limitRule.getRateType(), limitRule.getTokenCount(), limitRule.getInterval(), RateIntervalUnit.MILLISECONDS);
        return rateLimiter;
    }

    /**
     * key: ip_url
     * example: 127.0.0.1_api_test
     */
    private String getKey(String ip, String url) {
        url = url.replaceAll("/", "_");
        return ip + url;
    }
}
