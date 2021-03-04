package com.sardine.rocketmq.test;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

public class Consumer {
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("MyConsumer");
        consumer.setNamesrvAddr("192.168.25.101:9876");
        // Each consumer just focus one topic
        // Topic: the address to which message will be sent
        // Filter: * refers to not filter
//        consumer.setConsumeThreadMin(1);
//        consumer.setConsumeThreadMax(1);
        consumer.subscribe("myTopic", "*");
//        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
//            for (MessageExt msg : msgs) {
//                String msgStr = new String(msg.getBody());
//                System.out.println(msgStr + Thread.currentThread().getName());
//            }
//            // This message will be consumed by one consumer in default situation, point to point.
//            // On the other hand, consumer can update status of message, ack
//            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//        });
        consumer.registerMessageListener((MessageListenerOrderly) (msgs, context) -> {
            for (MessageExt msg : msgs) {
                String msgStr = new String(msg.getBody());
                System.out.println(msgStr + " Thread:" + Thread.currentThread().getName() + " Queue:" + msg.getQueueId());
            }
            return ConsumeOrderlyStatus.SUCCESS;
        });
        // Clustering: consume once.
        consumer.start();
        System.out.println("Consumer started.");
    }
}
