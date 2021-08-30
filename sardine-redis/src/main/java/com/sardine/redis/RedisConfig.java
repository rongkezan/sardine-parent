package com.sardine.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
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
@EnableCaching
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig {

    private static final String PREFIX = "redis://";

    private static final String SSL_PREFIX = "rediss://";

    @Resource
    private RedisProperties redisProperties;

    @Bean
    public CacheManager cacheManager(RedissonClient redissonClient) {
        return new RedissonSpringCacheManager(redissonClient);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 解决jackson2无法反序列化LocalDateTime的问题
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        om.registerModule(new JavaTimeModule());

        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer(om);  // 序列化方式
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);
        return redisTemplate;
    }

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 解决jackson2无法反序列化LocalDateTime的问题
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        om.registerModule(new JavaTimeModule());
        config.setCodec(new JsonJacksonCodec(om));
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
            sentinelConfig.addSentinelAddress(PREFIX + node);
        }
    }

    private void singleConfig(Config config){
        String prefix = redisProperties.isSsl() ? SSL_PREFIX : PREFIX;
        config.useSingleServer()
                .setAddress(prefix + redisProperties.getHost() + ":" + redisProperties.getPort())
                .setConnectTimeout((int) redisProperties.getTimeout().toMillis())
                .setDatabase(redisProperties.getDatabase())
                .setPassword(redisProperties.getPassword());
    }

}
