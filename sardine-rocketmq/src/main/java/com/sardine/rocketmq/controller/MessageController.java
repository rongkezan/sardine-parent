package com.sardine.rocketmq.controller;

import com.sardine.rocketmq.entity.OrderDo;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 发送普通消息
 *
 * @author keith
 */
@RestController
public class MessageController {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("sendMessage")
    public String sendMessage() {
        rocketMQTemplate.convertAndSend("test-topic:tag1", new OrderDo());
        return "success";
    }
}
