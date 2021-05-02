package com.sardine.redisson;

import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RList;
import org.redisson.api.RMap;
import org.redisson.api.RQueue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author keith
 */
@RestController
public class TestController {

    @GetMapping("bucket/get")
    public String getBucket(){
        return RedissonFactory.getClient().getBucket("bucket.a").get().toString();
    }

    @GetMapping("bucket/set")
    public String setBucket(){
        RedissonFactory.getClient().getBucket("bucket.a").set(new Cat());
        return "success";
    }

    @GetMapping("queue/push")
    public String pushQueue(){
        RQueue<String> queue = RedissonFactory.getClient().getQueue("queue.a");
        queue.add("a");
        queue.add("b");
        return "success";
    }

    @GetMapping("queue/poll")
    public String pollQueue(){
        RQueue<String> queue = RedissonFactory.getClient().getQueue("queue.a");
        return queue.poll();
    }

    @GetMapping("list/add")
    public String addList(){
        RList<Object> list = RedissonFactory.getClient().getList("list.a");
        list.add("啊");
        list.add("哦");
        return "success";
    }

    @GetMapping("list/get")
    public List<String> getList(){
        RList<String> list = RedissonFactory.getClient().getList("list.a");
        return list.readAll();
    }

    @GetMapping("map/put")
    public String putMap(){
        RMap<String, String> map = RedissonFactory.getClient().getMap("map.a");
        map.put("a", "aaa");
        map.put("b", "bbb");
        return "success";
    }

    @GetMapping("map/get")
    public Map<String, String> getMap(){
        RMap<String, String> map = RedissonFactory.getClient().getMap("map.a");
        return map;
    }
}
