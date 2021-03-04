package com.sardine.rocketmq.test;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * Transaction producer
 */
public class ProducerTransaction {
    public static void main(String[] args) throws MQClientException {
        TransactionMQProducer producer = new TransactionMQProducer("MyTransactionGroup");
        producer.setNamesrvAddr("192.168.25.101:9876");
        producer.setSendMsgTimeout(6000);
        producer.setTransactionListener(new TransactionListener() {
            @Override
            public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                // Execute local transaction
                try {
                    System.out.println("--- Execute ---");
                    System.out.println("msg: " + new String(msg.getBody()));
                    System.out.println("msg: " + msg.getTransactionId());
                    return LocalTransactionState.COMMIT_MESSAGE;
                } catch (Exception e) {
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }
            }

            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                // When no response to half message, this method will be invoked.
                System.out.println("--- Check ---");
                System.out.println("msg: " + new String(msg.getBody()));
                System.out.println("msg: " + msg.getTransactionId());
                return LocalTransactionState.UNKNOW;
            }
        });
        producer.start();
        producer.sendMessageInTransaction(new Message("myTopic", "Test Transaction Message".getBytes()), null);
    }
}
