package com.sardine.rocketmq.controller;

import com.sardine.rocketmq.entity.OrderDo;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 发送普通消息
 *
 * @author keith
 */
@Slf4j
@RestController
public class MessageController {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("sendMessage")
    public String sendMessage() {
        log.info("发送消息");
        rocketMQTemplate.convertAndSend("test-topic:tag1", new OrderDo());
        return "success";
    }
}
