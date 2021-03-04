package com.sardine.rocketmq.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private ProducerService producerService;

    @GetMapping("send")
    public String testSend(String msg){
        return producerService.sendMsg(msg);
    }
}
