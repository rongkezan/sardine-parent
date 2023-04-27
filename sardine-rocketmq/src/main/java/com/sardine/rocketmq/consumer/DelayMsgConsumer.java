package com.sardine.rocketmq.consumer;

import com.sardine.rocketmq.entity.OrderDo;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(
        // topic：和生产者发送的topic相同
        topic = "sync-send-delay",
        consumerGroup = "sync-send-delay",
        selectorExpression = "*",
        // BROADCASTING: 广播模式，消费组中每台机器都能收到消息
        // CLUSTERING: 集群模式，消费组中只有一台机器能收到消息
        messageModel = MessageModel.CLUSTERING,
        consumeMode = ConsumeMode.ORDERLY
)
public class DelayMsgConsumer implements RocketMQListener<OrderDo> {

    @Override
    public void onMessage(OrderDo orderDo) {
        log.info("接收延时消息结果: {}", orderDo);
    }
}
