package com.sardine.nacos.order.service;

import com.sardine.nacos.api.client.StorageClient;
import com.sardine.nacos.order.entity.OrderDo;
import com.sardine.nacos.order.mapper.OrderMapper;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Slf4j
@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private StorageClient storageClient;

    @GlobalTransactional
    @Transactional
    public void placeOrder(OrderDo order) {
        // 模拟A->B->C， 库存，余额已经扣减，但是新增订单失败
        storageClient.reduce(10L, 1);
        int a = 1 / 0;
        orderMapper.insert(order);
    }

    public void hello() {
        log.info("Order Service Say Hello!");
        storageClient.hello();
    }
}
