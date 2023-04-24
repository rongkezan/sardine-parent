package com.sardine.nacos.order.service;

import com.sardine.nacos.order.entity.OrderDo;
import com.sardine.nacos.order.mapper.OrderMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;

    public void placeOrder(OrderDo order) {
        orderMapper.insert(order);
    }
}
