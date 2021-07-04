package com.sardine.rocketmq;

import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author keith
 */
@Component
@RocketMQMessageListener(
        topic = "test-topic", 					// topic：和生产者发送的topic相同
        consumerGroup = "mq-test",              // group：不用和生产者group相同
        selectorExpression = "tag1",            // tag
        messageModel = MessageModel.CLUSTERING,
        consumeMode = ConsumeMode.ORDERLY)
public class Consumer implements RocketMQListener<Dog> {

    @Override
    public void onMessage(Dog message) {
        System.out.println(message);
    }
}
