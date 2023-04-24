package com.sardine.nacos.storage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("tb_goods")
public class GoodsDo {

    private Long goodsId;

    private String goodsName;

    private BigDecimal price;

    private Integer stock;

    private LocalDateTime createTime;
}
