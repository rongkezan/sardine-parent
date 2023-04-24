package com.sardine.rocketmq.service;

import com.sardine.rocketmq.entity.OrderDo;
import com.sardine.rocketmq.mapper.OrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Transactional
    public void placeOrder(OrderDo order){
        orderMapper.insert(order);
    }
}
