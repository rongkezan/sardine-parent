package com.sardine.nacos.api.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class AccountClientFallback implements AccountClient {

    @Override
    public void reduce(Long accountId, BigDecimal amount) {
        log.error("余额扣减失败, AccountId: {}, 金额: {}", accountId, amount);
    }
}
