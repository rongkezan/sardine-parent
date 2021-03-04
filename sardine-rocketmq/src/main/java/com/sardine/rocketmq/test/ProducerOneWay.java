package com.sardine.rocketmq.test;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * One way message, no fallback
 */
public class ProducerOneWay {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("MyAsyncGroup");
        // Set nameServer address
        producer.setNamesrvAddr("192.168.25.101:9876");
        producer.start();
        producer.setSendMsgTimeout(6000);
        // Topic: The address to which message will be sent
        // Body: The real message
        Message message = new Message("myTopic", "myAsyncMessage".getBytes());
        // Asynchronous send, waiting for confirm about broker by event listener mechanism.
        producer.sendOneway(message);
        producer.shutdown();
    }
}
