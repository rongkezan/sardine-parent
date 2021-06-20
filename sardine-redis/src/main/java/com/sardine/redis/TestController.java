package com.sardine.redis;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author keith
 */
@RestController
public class TestController {

    /** redisson bucket get operation */
    @GetMapping("redisson/bucket/get")
    public String getBucket(){
        return RedisFactory.redisson().getBucket("bucket.a").get().toString();
    }

    /** redisson bucket set operation */
    @GetMapping("redisson/bucket/set")
    public String setBucket(){
        RedisFactory.redisson().getBucket("bucket.a").set(new Cat());
        return "success";
    }

    /** redisTemplate string get operation */
    @GetMapping("redisTemplate/string/get")
    public String getString(){
        return RedisFactory.stringRedisTemplate().opsForValue().get("template");
    }

    /** redisTemplate string set operation */
    @GetMapping("redisTemplate/string/set")
    public String setString(){
        RedisFactory.stringRedisTemplate().opsForValue().set("template", "aaa");
        return "success";
    }
}
