package com.sardine.rocketmq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * @author keith
 */
@RestController
public class TestController {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("sendMessage")
    public String sendMessage() {
        rocketMQTemplate.convertAndSend("test-topic:tag1||tag2", new Dog());
        return "success";
    }

    @GetMapping("sendTransactionMessage")
    public String sendTransactionMessage(@RequestParam String message) throws MQClientException {
        // 创建事务生产者
        TransactionMQProducer producer = new TransactionMQProducer("transaction_producer_group");
        producer.setNamesrvAddr("localhost:9876");
        // 设置事务监听器
        producer.setTransactionListener(new TransactionListenerImpl());
        // 启动事务生产者
        producer.start();

        try {
            // 发送半消息
            Message msg = new Message("topic", "tag", message.getBytes(StandardCharsets.UTF_8));
            SendResult sendResult = rocketMQTemplate.getProducer().sendMessageInTransaction(msg, null);
            System.out.println("Half message send success, transactionId: " + sendResult.getTransactionId());
        } finally {
            // 关闭事务生产者
            producer.shutdown();
        }
        return "success";
    }

    static class TransactionListenerImpl implements TransactionListener {

        @Override
        public LocalTransactionState executeLocalTransaction(Message message, Object o) {
            // 执行本地事务
            // ...

            // 返回事务状态
            return LocalTransactionState.COMMIT_MESSAGE;
        }

        @Override
        public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
            // 查询本地事务状态
            // ...

            // 返回事务状态
            return LocalTransactionState.COMMIT_MESSAGE;
        }
    }
}
