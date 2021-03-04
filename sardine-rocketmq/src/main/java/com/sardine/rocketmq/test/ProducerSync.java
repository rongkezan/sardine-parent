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
public class ProducerSync {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("MyGroup");
        // Set nameServer address
        producer.setNamesrvAddr("192.168.25.101:9876");
        producer.start();
        producer.setSendMsgTimeout(6000);
        // Topic: The address to which message will be sent
        // Body: The real message
        for (int i = 0; i < 100; i++) {
            Message message = new Message("myTopic", ("myMessage" + i).getBytes());
            message.putUserProperty("order", String.valueOf(i));
            // Synchronized send, it will blocking, slow but don't lose message
            producer.send(message);
        }
        producer.shutdown();
    }
}
