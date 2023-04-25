package com.sardine.nacos.order.service;

import com.sardine.nacos.api.client.StorageClient;
import com.sardine.nacos.order.entity.OrderDo;
import com.sardine.nacos.order.mapper.OrderMapper;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private StorageClient storageClient;

    @GlobalTransactional
    @Transactional
    public void placeOrder(OrderDo order) {
        // 模拟 库存已经扣减，但是新增订单失败 的情况
        storageClient.reduce(10L, 1);
        int a = 1 / 0;
        orderMapper.insert(order);
    }
}
