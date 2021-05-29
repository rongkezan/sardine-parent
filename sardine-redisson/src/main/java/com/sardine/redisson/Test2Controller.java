package com.sardine.redisson;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class Test2Controller {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("r/set")
    public Long set() {
        return redisTemplate.opsForList().leftPush("a.list", "1");
    }

    @GetMapping("r/lrange")
    public List<String> lrange() {
        List<String> range = redisTemplate.opsForList().range("a.list", 0, -1);
        return range;
    }
}
