package com.sardine.nacos.account.client;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sardine.nacos.account.entity.AccountDo;
import com.sardine.nacos.account.mapper.AccountMapper;
import com.sardine.nacos.api.client.AccountClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Slf4j
@RestController
public class AccountClientImpl implements AccountClient {

    @Resource
    private AccountMapper accountMapper;

    @Override
    public void reduce(Long accountId, BigDecimal amount) {
        accountMapper.update(null, Wrappers.<AccountDo>lambdaUpdate()
                .eq(AccountDo::getAccountId, accountId)
                .setSql(amount.compareTo(BigDecimal.ZERO) > 0, "balance = balance - " + amount)
        );
    }

    @Override
    public void hello() {
        log.info("Account Service Say Hello!");
    }
}
