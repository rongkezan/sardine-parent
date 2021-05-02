/**
 * Copyright © 2020 Renrenbit All Rights Reserved
 */
package com.sardine.redisson;

import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author keith
 */
public class RedissonFactory {

    private static volatile RedissonClient client;

    public static RedissonClient getClient() {
        if (client == null) {
            synchronized (RedissonFactory.class) {
                if (client == null) {
                    client = SpringContexts.getBean("redissonClient", RedissonClient.class);
                }
            }
        }
        return client;
    }
}
