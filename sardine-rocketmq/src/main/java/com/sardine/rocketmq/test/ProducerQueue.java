package com.sardine.rocketmq.test;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.selector.SelectMessageQueueByHash;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;


/**
 * Ordered Message
 */
public class ProducerQueue {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("MyGroup");
        producer.setNamesrvAddr("192.168.25.101:9876");
        producer.setSendMsgTimeout(6000);
        producer.start();

        // Same arg message will send to same queue.
        for (int i = 0; i < 20; i++) {
            Message message = new Message("myTopic", String.valueOf(i).getBytes());
            producer.send(
                    // Message you want to send
                    message,
                    // Queue Selector
                    // mqs: all queues in current topic
                    // msg: the message you want to send
                    // arg: mapping method producer.send() arg
                    (mqs, msg, arg) ->
                            // Write messages to a fixed queue
                            mqs.get((Integer) arg), 1, 6000);
//            producer.send(message, new SelectMessageQueueByHash(), 1);
        }
        producer.shutdown();
    }
}
