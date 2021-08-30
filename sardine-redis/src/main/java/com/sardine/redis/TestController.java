package com.sardine.redis;

import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author keith
 */
@RestController
public class TestController {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private CacheService cacheService;

    @GetMapping("get")
    public Object get(){
        return redisTemplate.opsForValue().get("a");
    }

    @GetMapping("set")
    public String set(){
        redisTemplate.opsForValue().set("a", new Cat());
        return "success";
    }

    @GetMapping("set3")
    public String set3(){
        redisTemplate.opsForHash().put("a_map", "aaa", new Cat());
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

    @GetMapping("get2")
    public Cat get2(){
        Map<String, Cat> map = redissonClient.getMap("s_map");
        return map.get("aaa");
    }

    @GetMapping("set2")
    public String set2(){
        Map<String, Cat> map = redissonClient.getMap("s_map");
        map.put("aaa", new Cat());
        return "success";
    }

    @GetMapping("cache")
    public String getSomething(@RequestParam Long key){
        return cacheService.getSomething(key);
    }
}
