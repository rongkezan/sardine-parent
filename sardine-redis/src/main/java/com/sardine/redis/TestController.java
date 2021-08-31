package com.sardine.redis;

import com.sardine.utils.BeanUtils;
import jodd.bean.BeanUtil;
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

    @GetMapping("rt/set1")
    public String rtSet1() {
        redisTemplate.opsForValue().set("a", new Cat());
        return "success";
    }

    @GetMapping("rt/get1")
    public Object rtGet1() {
        return redisTemplate.opsForValue().get("a");
    }

    @GetMapping("rt/set2")
    public String rtSet2() {
        redisTemplate.opsForHash().put("a_map", "aaa", new Cat());
        return "success";
    }

    @GetMapping("rt/get2")
    public Object rtGet2() {
        return redisTemplate.opsForHash().get("a_map", "aaa");
    }

    @GetMapping("rs/set1")
    public String rsSet1() {
        redissonClient.<Cat>getBucket("cat").set(new Cat());
        return "success";
    }

    @GetMapping("rs/get1")
    public Cat rsGet1() {
        return redissonClient.<Cat>getBucket("cat").get();
    }

    @GetMapping("rs/set2")
    public String rsSet2() {
        Map<String, Cat> map = redissonClient.getMap("s_map");
        map.put("aaa", new Cat());
        return "success";
    }

    @GetMapping("rs/get2")
    public Cat rsGet2() {
        Map<String, Cat> map = redissonClient.getMap("s_map");
        return map.get("aaa");
    }

    @GetMapping("cache")
    public String getSomething(@RequestParam Long key) {
        return cacheService.getSomething(key);
    }
}
