package com.sardine.gateway.door.config;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author keith
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfigurer {

    @Resource
    private RedisProperties redisProperties;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        RedisProperties.Cluster cluster = redisProperties.getCluster();
        if (cluster != null) {
            List<String> nodes = cluster.getNodes();
            config.useClusterServers()
                    .addNodeAddress(nodes.toArray(new String[0]))
                    .setConnectTimeout((int) redisProperties.getTimeout().toMillis())
                    .setPassword(redisProperties.getPassword());
        } else {
            String prefix = redisProperties.isSsl() ? "rediss://" : "redis://";
            config.useSingleServer()
                    .setAddress(prefix + redisProperties.getHost() + ":" + redisProperties.getPort())
                    .setConnectTimeout((int) redisProperties.getTimeout().toMillis())
                    .setDatabase(redisProperties.getDatabase())
                    .setPassword(redisProperties.getPassword());
        }
        return Redisson.create(config);
    }
}
