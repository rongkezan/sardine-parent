package com.sardine.rocketmq.web;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProducerService {

    @Autowired
    private DefaultMQProducer producer;

    public String sendMsg(String msg) {
        for (int i = 0; i < 1; i++) {
            Message message = new Message("testTopic", msg.getBytes());
            try {
                SendResult sendResult = producer.send(message);
                return sendResult == null ? "" : sendResult.getMsgId();
            } catch (Exception e) {
                log.error("Send message error", e);
            }
        }
        return null;
    }
}
