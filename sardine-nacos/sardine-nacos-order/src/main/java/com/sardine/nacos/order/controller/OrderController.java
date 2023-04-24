package com.sardine.nacos.order.controller;

import com.sardine.nacos.api.client.StorageClient;
import com.sardine.nacos.order.entity.OrderDo;
import com.sardine.nacos.order.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class OrderController {

    @Resource
    private StorageClient storageClient;

    @Resource
    private OrderService orderService;

    @GetMapping("placeOrder")
    public String placeOrder() {
        orderService.placeOrder(new OrderDo());
        storageClient.reduce(10L, 1);
        return "success";
    }
}
