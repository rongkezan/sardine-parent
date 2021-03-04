package com.sardine.rocketmq.test;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * Synchronized message, waiting fallback blocking
 */
public class ProducerTagKey {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("MyGroup");
        // Set nameServer address
        producer.setNamesrvAddr("192.168.25.101:9876");
        producer.start();
        producer.setSendMsgTimeout(6000);
        // Topic: The address to which message will be sent
        // Body: The real message
        Message message = new Message("myTopic", "MyTag", "MyKey", "myMessage".getBytes());
        // Synchronized send, it will blocking, slow but don't lose message
        SendResult result = producer.send(message);
        // Get consumer's fallback
        System.out.println(result);
        producer.shutdown();
    }
}
