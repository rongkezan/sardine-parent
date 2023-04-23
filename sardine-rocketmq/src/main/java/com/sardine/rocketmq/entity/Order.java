package com.sardine.rocketmq.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Order {

    /**
     * 订单ID
     */
    private Long orderId = 100L;

    /**
     * 商品名称
     */
    private String goodsName = "电动牙刷";

    /**
     * 发生额
     */
    private BigDecimal amount = BigDecimal.valueOf(610);

    /**
     * 创建时间
     */
    private LocalDateTime createTime = LocalDateTime.now();
}
