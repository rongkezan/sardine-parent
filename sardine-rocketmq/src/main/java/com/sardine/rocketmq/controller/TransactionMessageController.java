package com.sardine.rocketmq.controller;

import com.sardine.rocketmq.entity.OrderDo;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 发送事务消息
 *
 * @author keith
 */
@Slf4j
@RestController
public class TransactionMessageController {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("sendTransactionMessage")
    public String sendTransactionMessage() {
        OrderDo orderDo = new OrderDo();
        TransactionSendResult sendResult = rocketMQTemplate.sendMessageInTransaction("tx-topic",
                MessageBuilder.withPayload(orderDo).setHeader("tx_id", orderDo.getOrderId()).build(),
                orderDo
        );
        return sendResult.getTransactionId();
    }
}
