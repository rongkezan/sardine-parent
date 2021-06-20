package com.sardine.zk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author keith
 */
@RestController
@EnableScheduling
@SpringBootApplication
public class ZkApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZkApplication.class, args);
    }

    @Resource
    private ZookeeperService zookeeperService;

    @Resource
    private RedisTemplate<String, Integer> redisTemplate;

    /**
     * If the server has leadership, increment number.
     * It can do distributed scheduling.
     */
    @Scheduled(fixedDelay = 1000)
    public void hello(){
        if (zookeeperService.hasLeaderShip()) {
            Long value = redisTemplate.opsForValue().increment("curator");
            System.out.println("Hello: " + value);
        }
    }

    @GetMapping("isLeader")
    public boolean isLeader(){
        return zookeeperService.hasLeaderShip();
    }
}
