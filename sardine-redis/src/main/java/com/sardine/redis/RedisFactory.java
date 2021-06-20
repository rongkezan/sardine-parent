package com.sardine.redis;

import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author keith
 */
public class RedisFactory {

    private static volatile RedissonClient redissonClient;

    private static volatile StringRedisTemplate stringRedisTemplate;

    public static RedissonClient redisson() {
        if (redissonClient == null) {
            synchronized (RedisFactory.class) {
                if (redissonClient == null) {
                    redissonClient = SpringContexts.getBean("redissonClient", RedissonClient.class);
                }
            }
        }
        return redissonClient;
    }

    public static StringRedisTemplate stringRedisTemplate(){
        if (stringRedisTemplate == null) {
            synchronized (RedisFactory.class) {
                if (stringRedisTemplate == null) {
                    stringRedisTemplate = SpringContexts.getBean("stringRedisTemplate", StringRedisTemplate.class);
                }
            }
        }
        return stringRedisTemplate;
    }
}
