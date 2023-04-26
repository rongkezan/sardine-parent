package com.sardine.rocketmq.service;

import com.sardine.rocketmq.entity.OrderDo;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQReplyListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(
        topic = "callback-topic", 			    // topic：和生产者发送的topic相同
        consumerGroup = "callback-msg-group",   // group：不用和生产者group相同
        selectorExpression = "*",               // tag
        messageModel = MessageModel.CLUSTERING,
        consumeMode = ConsumeMode.ORDERLY
)
public class OrderCallbackConsumerService implements RocketMQReplyListener<OrderDo, String> {

    @Override
    public String onMessage(OrderDo message) {
        log.info("接收callback消息: {}", message);
        return "接收callback消息成功！";
    }
}
