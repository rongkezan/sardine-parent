package com.sardine.rocketmq.service;

import com.sardine.rocketmq.entity.OrderDo;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RocketMQTransactionListener
public class OrderTransactionListenerImpl implements RocketMQLocalTransactionListener {

    @Resource
    private OrderService orderService;

    // 可以将事务状态存到redis，就不会有因为JVM重启后丢失事务状态的问题
    private ConcurrentHashMap<String, RocketMQLocalTransactionState> localTransactions = new ConcurrentHashMap<>();

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        log.info("执行本地事务, message: {}, arg: {}", msg, arg);
        OrderDo order = (OrderDo) arg;
        try {
            // 执行本地事务
            localTransactions.put(order.getOrderId().toString(), RocketMQLocalTransactionState.UNKNOWN);
            orderService.placeOrder(order);
            localTransactions.put(order.getOrderId().toString(), RocketMQLocalTransactionState.COMMIT);
        } catch (Exception e) {
            // 执行本地事务失败，回滚消息
            log.warn("本地事务执行失败，回滚事务消息，arg: {}", arg, e);
            localTransactions.put(order.getOrderId().toString(), RocketMQLocalTransactionState.COMMIT);
            return RocketMQLocalTransactionState.ROLLBACK;
        }
        // 执行本地事务成功，提交消息
        return RocketMQLocalTransactionState.COMMIT;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        Object txId = msg.getHeaders().get("tx_id");
        String orderId = txId != null ? txId.toString() : "";
        log.info("检查本地事务, orderId: {}", orderId);

        // 查询本地事务状态
        RocketMQLocalTransactionState transactionState = localTransactions.get(orderId);
        log.info("查询本地事务状态结果: {}", transactionState);

        // 返回本地事务状态
        return transactionState;
    }
}