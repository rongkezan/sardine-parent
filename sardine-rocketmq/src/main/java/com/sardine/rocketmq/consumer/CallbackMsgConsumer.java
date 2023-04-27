package com.sardine.rocketmq.consumer;

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
        // topic：和生产者发送的topic相同
        topic = "send-and-receive",
        consumerGroup = "send-and-receive",
        selectorExpression = "*",
        // BROADCASTING: 广播模式，消费组中每台机器都能收到消息
        // CLUSTERING: 集群模式，消费组中只有一台机器能收到消息
        messageModel = MessageModel.CLUSTERING,
        consumeMode = ConsumeMode.ORDERLY
)
public class CallbackMsgConsumer implements RocketMQReplyListener<OrderDo, String> {

    @Override
    public String onMessage(OrderDo message) {
        log.info("接收回调消息结果: {}", message);
        return "接收回调消息成功！";
    }
}
