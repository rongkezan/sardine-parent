package com.sardine.rocketmq.controller;

import com.sardine.rocketmq.entity.OrderDo;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * RocketMQ支持的消息有：普通消息、顺序消息、延时消息、事务消息
 *
 * @author keith
 */
@Slf4j
@RestController
public class MessageController {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("syncSend")
    public String syncSend() {
        // 普通消息[同步]：producer向broker发送消息时同步等待，直到broker服务器返回发送结果
        SendResult sendResult = rocketMQTemplate.syncSend("sync-send", new OrderDo());
        return sendResult.getMsgId();
    }

    @GetMapping("asyncSend")
    public String asyncSend() {
        // 普通消息[异步]：producer向broker发送消息时异步执行，不会影响后面逻辑。
        // 而异步里面会调用一个回调方法，来处理消息发送成功或失败的逻辑
        rocketMQTemplate.asyncSend("async-send", new OrderDo(), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("异步消息发送成功, 消息ID: {}", sendResult.getMsgId());
            }

            @Override
            public void onException(Throwable e) {
                log.error("异步消息发送失败", e);
            }
        });
        return "异步消息已发送";
    }

    @GetMapping("sendOneWay")
    public String sendOneWay() {
        // 普通消息[单向]：producer向broker发送消息时直接返回，不等待broker服务器的响应
        rocketMQTemplate.sendOneWay("send-one-way", new OrderDo());
        return "单向消息已发送";
    }

    @GetMapping("sendAndReceive")
    public String sendAndReceive() {
        // 普通消息[回调]：producer向broker发送消息时异步等待broker服务器的响应
        return rocketMQTemplate.sendAndReceive("send-and-receive", new OrderDo(), String.class);
    }

    @GetMapping("syncSendOrderly")
    public String syncSendOrderly() {
        // 顺序消息[同步]: 消费者按照生产者发送消息的顺序去消费。
        // PS: 异步、单向顺序消息实现方式与普通消息类似
        rocketMQTemplate.syncSendOrderly("sync-send-orderly", new OrderDo().setStatusName("1001顺序消费-创建"), "1001");
        rocketMQTemplate.syncSendOrderly("sync-send-orderly", new OrderDo().setStatusName("1001顺序消费-支付"), "1001");
        rocketMQTemplate.syncSendOrderly("sync-send-orderly", new OrderDo().setStatusName("1001顺序消费-完成"), "1001");

        rocketMQTemplate.syncSendOrderly("sync-send-orderly", new OrderDo().setStatusName("1002顺序消费-创建"), "1002");
        rocketMQTemplate.syncSendOrderly("sync-send-orderly", new OrderDo().setStatusName("1002顺序消费-支付"), "1002");
        rocketMQTemplate.syncSendOrderly("sync-send-orderly", new OrderDo().setStatusName("1002顺序消费-完成"), "1002");
        return "顺序消息已同步发送";
    }

    @GetMapping("syncSendDelay")
    public String syncSendDelay() {
        // 延时消息[同步]: 生产者设定了延迟时间，时间到了消费者才去消费。
        // PS: 异步、单向延时消息实现方式与普通消息类似
        // 延迟等级 0 不延迟，1 延时1s，2 延时5s，3 延时10s，4 延时 30s，以此类推。。。
        // messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
        rocketMQTemplate.syncSend("sync-send-delay",
                MessageBuilder.withPayload(new OrderDo().setStatusName("延迟消费5秒")).build(),
                3000, 2);
        return "延时消息已发送 5s延迟";
    }

    @GetMapping("sendMessageInTransaction")
    public String sendTransactionMessage() {
        // 事务消息
        OrderDo orderDo = new OrderDo();
        TransactionSendResult sendResult = rocketMQTemplate.sendMessageInTransaction("tx-topic",
                MessageBuilder.withPayload(orderDo).setHeader("tx_id", orderDo.getOrderId()).build(),
                orderDo
        );
        return sendResult.getTransactionId();
    }
}
