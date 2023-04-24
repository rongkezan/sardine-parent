package com.sardine.nacos.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("tb_order")
public class OrderDo {

    @TableId(type = IdType.ASSIGN_ID)
    private Long orderId;

    private String goodsName = "鼠标";

    private BigDecimal amount = BigDecimal.valueOf(20);

    private LocalDateTime createTime = LocalDateTime.now();
}
