package com.sardine.rocketmq;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author keith
 */
@RestController
public class TestController {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("send")
    public String send(){
        rocketMQTemplate.convertAndSend("test-topic:tag1||tag2", new Dog());
        return "success";
    }
}
