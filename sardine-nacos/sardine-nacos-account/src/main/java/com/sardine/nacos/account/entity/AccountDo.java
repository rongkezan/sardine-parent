package com.sardine.nacos.account.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("tb_account")
public class AccountDo {

    private Long accountId;

    private String username;

    private BigDecimal balance;

    private LocalDateTime createTime;
}
