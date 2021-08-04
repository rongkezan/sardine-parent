package com.sardine.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.util.List;

/**
 * Redis Config
 *
 * @author keith
 */
@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig {

    @Resource
    private RedisProperties redisProperties;

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        Jackson2JsonRedisSerializer<?> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        return redisTemplate;
    }

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.setCodec(new JsonJacksonCodec());
        RedisProperties.Cluster cluster = redisProperties.getCluster();
        RedisProperties.Sentinel sentinel = redisProperties.getSentinel();
        if (cluster != null) {
            clusterConfig(config, cluster);
        } else if (sentinel != null) {
            sentinelConfig(config, sentinel);
        } else {
            singleConfig(config);
        }
        return Redisson.create(config);
    }

    private void clusterConfig(Config config, RedisProperties.Cluster cluster){
        List<String> nodes = cluster.getNodes();
        config.useClusterServers()
                .addNodeAddress(nodes.toArray(new String[0]))
                .setConnectTimeout((int) redisProperties.getTimeout().toMillis())
                .setPassword(redisProperties.getPassword());
    }

    private void sentinelConfig(Config config, RedisProperties.Sentinel sentinel){
        List<String> nodes = sentinel.getNodes();
        SentinelServersConfig sentinelConfig = config.useSentinelServers().setMasterName(sentinel.getMaster());
        for (String node : nodes) {
            sentinelConfig.addSentinelAddress("redis://" + node);
        }
    }

    private void singleConfig(Config config){
        String prefix = redisProperties.isSsl() ? "rediss://" : "redis://";
        config.useSingleServer()
                .setAddress(prefix + redisProperties.getHost() + ":" + redisProperties.getPort())
                .setConnectTimeout((int) redisProperties.getTimeout().toMillis())
                .setDatabase(redisProperties.getDatabase())
                .setPassword(redisProperties.getPassword());
    }

}
