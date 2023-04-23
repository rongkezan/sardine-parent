package com.sardine.rocketmq.service;

import com.sardine.rocketmq.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author keith
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = "test-topic", 			        // topic：和生产者发送的topic相同
        consumerGroup = "msg-group",            // group：不用和生产者group相同
        selectorExpression = "*",               // tag
        messageModel = MessageModel.CLUSTERING,
        consumeMode = ConsumeMode.ORDERLY
)
public class OrderConsumerService implements RocketMQListener<Order> {

    @Override
    public void onMessage(Order order) {
        log.info("接收普通消息结果: {}", order);
    }
}
