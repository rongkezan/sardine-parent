package com.sardine.rocketmq.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("tb_order")
public class OrderDo {

    /**
     * 订单ID
     */
    private Long orderId = IdWorker.getId();

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
