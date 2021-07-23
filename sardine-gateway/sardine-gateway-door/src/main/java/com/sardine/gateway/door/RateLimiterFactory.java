package com.sardine.gateway.door;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.sardine.common.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.RedissonRateLimiter;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RedissonClient;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author keith
 */
@Slf4j
public class RateLimiterFactory {
    /**
     * 限流实例缓存
     */
    private static final Cache<RateLimiterDto, RRateLimiter> rRateLimiterCache = CacheBuilder.newBuilder().maximumSize(100000).expireAfterAccess(10, TimeUnit.MINUTES).build();
    /**
     * API限流规则缓存,永不失效，更新配置自动刷新缓存
     */
    private static final Cache<String, ApiLimiterConfig> limiterConfigCache = CacheBuilder.newBuilder().maximumSize(300).expireAfterAccess(Integer.MAX_VALUE, TimeUnit.DAYS).build();

    public static RRateLimiter getRateLimiter(RateLimiterDto limiterDto) {
        try {
            return rRateLimiterCache.get(limiterDto, () -> {
                RedissonClient client = RedissonFactory.getRedissonClient();
                Redisson redisson = (Redisson) client;
                RedissonRateLimiter rateLimiter = new RedissonRateLimiter(redisson.getCommandExecutor(), getKey(limiterDto));
                ApiLimiterConfig apiLimiterConfig = getApiLimiterConfig(limiterDto);
                rateLimiter.setRate(apiLimiterConfig.getRateType(), apiLimiterConfig.getRate(), apiLimiterConfig.getInterval(), RateIntervalUnit.MILLISECONDS);
                return rateLimiter;
            });
        } catch (ExecutionException e) {
            throw SystemException.of(e.getMessage());
        }
    }

    /**
     * 获取API限流规则
     *
     * @param limiterDto
     * @return
     */
    private static ApiLimiterConfig getApiLimiterConfig(RateLimiterDto limiterDto) {
        ApiLimiterConfig apiLimiterConfig;
        //如果有配置userId+api规则，优先获取，否则只获取api规则
        if (StringUtils.isNotEmpty(limiterDto.getUserId())) {
            String key = getLimiterConfigKey(limiterDto.getUserId(), limiterDto.getApi());
            apiLimiterConfig = getApiLimiterConfig(key);
            if (apiLimiterConfig == null) {
                apiLimiterConfig = getApiLimiterConfig(limiterDto.getApi());
            }
        } else {
            apiLimiterConfig = getApiLimiterConfig(limiterDto.getApi());
        }
        return apiLimiterConfig;
    }

    /**
     * 获取限流实例对象名，支持登陆或者非登陆接口
     * <pre>
     *     1、key值如：{open-gateway_v1_order_create::383} 或 {open-gateway_v1_order_create::172.0.0.1}
     *     2、优先获取{api}+{userId} 为key
     *     3、如果userId为空，使用{api}+{ip} 为key
     * </pre>
     *
     * @param limiterDto
     * @return
     */
    private static String getKey(RateLimiterDto limiterDto) {
        String api = limiterDto.getApi();
        if (api.startsWith("/")) {
            api = api.substring(1);
        }
        String apiKey = api.replaceAll("/", "_");
        if (StringUtils.isNotEmpty(limiterDto.getUserId())) {
            return String.format("{%s::%s}", apiKey, limiterDto.getUserId());
        } else {
            return String.format("{%s::%s}", apiKey, limiterDto.getIp());
        }
    }

    /**
     * 更新api限流规则缓存
     *
     * @param limiterConfigList
     */
    public static void refreshApiLimiterCache(List<ApiLimiterConfig> limiterConfigList) {
        limiterConfigList.forEach(config -> {
            String key = getLimiterConfigKey(config.getUserId(), config.getApi());
            limiterConfigCache.put(key, config);
        });
        rRateLimiterCache.invalidateAll();
    }

    private static ApiLimiterConfig getApiLimiterConfig(String api) {
        if (api.startsWith("/")) {
            api = api.substring(1);
        }
        String apiKey = api.replaceAll("/", "_");
        return limiterConfigCache.getIfPresent(apiKey);
    }

    /**
     * 获取规则key,如：v1_order_create_383 或 v1_order_create
     *
     * @param userId
     * @param api
     * @return
     */
    private static String getLimiterConfigKey(String userId, String api) {
        if (api.startsWith("/")) {
            api = api.substring(1);
        }
        String apiKey = api.replaceAll("/", "_");
        return Optional.ofNullable(userId).map(u -> apiKey.concat("_").concat(u)).orElse(apiKey);
    }

    /**
     * 查询所有API限流配置
     *
     * @return
     */
    public static Map<String, ApiLimiterConfig> getApiLimiterConfigs() {
        return limiterConfigCache.asMap();
    }
}
