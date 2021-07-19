package com.sardine.redis;

import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author keith
 */
@RestController
public class TestController {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private RedissonClient redissonClient;

    @GetMapping("get")
    public Object get(){
        return redisTemplate.opsForValue().get("a");
    }

    @GetMapping("set")
    public String set(){
        redisTemplate.opsForValue().set("a", new Cat());
        return "success";
    }

    @GetMapping("get1")
    public Cat get1(){
        return redissonClient.<Cat>getBucket("cat").get();
    }

    @GetMapping("set1")
    public String set1(){
        redissonClient.<Cat>getBucket("cat").set(new Cat());
        return "success";
    }
}
