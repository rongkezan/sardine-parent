package com.sardine.rocketmq.controller;

import com.sardine.rocketmq.entity.OrderDo;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
public class CallbackMessageController {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("sendCallbackMessage")
    public String sendCallbackMessage(){
        log.info("发送消息");
        return rocketMQTemplate.sendAndReceive("callback-topic:tag1", new OrderDo(), String.class);
    }
}
