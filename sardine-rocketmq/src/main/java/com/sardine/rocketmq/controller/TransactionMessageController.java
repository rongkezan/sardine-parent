package com.sardine.rocketmq.controller;

import com.sardine.rocketmq.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.UUID;

@Slf4j
@RestController
public class TransactionMessageController {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("sendTransactionMessage")
    public String sendTransactionMessage() {
        String txId = UUID.randomUUID().toString();
        Order order = new Order();
        TransactionSendResult sendResult = rocketMQTemplate.sendMessageInTransaction("tx_topic",
                MessageBuilder.withPayload(order).setHeader("tx_id", txId).build(),
                order
        );
        return sendResult.getTransactionId();
    }
}
