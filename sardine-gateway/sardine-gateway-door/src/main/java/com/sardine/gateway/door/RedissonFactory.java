package com.sardine.gateway.door;

import com.sardine.common.util.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * @author keith
 */
@Slf4j
@Configuration
public class RedissonFactory {

    private static volatile RedissonClient redissonClient;

    public static RedissonClient getRedissonClient() {
        if (redissonClient == null) {
            synchronized (RedissonFactory.class) {
                if (redissonClient == null) {
                    Config config = getConfig();
                    redissonClient = Redisson.create(config);
                }
            }
        }
        return redissonClient;
    }

    private static Config getConfig() {
        RedisProperties redisProperties = SpringContextUtils.getBean(RedisProperties.class);
        Config config = new Config();
        RedisProperties.Cluster cluster = redisProperties.getCluster();
        // TODO See if null
        Duration duration = redisProperties.getTimeout();
        int timeout = Optional.ofNullable(duration).map(d -> (int) d.toMillis()).orElse(3000);
        if (cluster != null) {
            List<String> nodesObject = cluster.getNodes();
            String[] nodes = convert(nodesObject);
            config.useClusterServers().addNodeAddress(nodes).setConnectTimeout(timeout).setPassword(redisProperties.getPassword());
        } else {
            boolean isSsl = redisProperties.isSsl();
            String prefix = "redis://";
            if (isSsl) {
                prefix = "rediss://";
            }
            config.useSingleServer()
                    .setAddress(prefix + redisProperties.getHost() + ":" + redisProperties.getPort())
                    .setConnectTimeout((int) duration.toMillis())
                    .setDatabase(redisProperties.getDatabase())
                    .setPassword(redisProperties.getPassword());
        }
        return config;
    }

    private static String[] convert(List<String> nodesObject) {
        List<String> nodes = new ArrayList(nodesObject.size());
        Iterator var3 = nodesObject.iterator();

        while (true) {
            while (var3.hasNext()) {
                String node = (String) var3.next();
                if (!node.startsWith("redis://") && !node.startsWith("rediss://")) {
                    nodes.add("redis://" + node);
                } else {
                    nodes.add(node);
                }
            }
            return nodes.toArray(new String[nodes.size()]);
        }
    }
}
