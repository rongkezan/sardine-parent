package com.sardine.nacos.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(value = "sardine-nacos-account", fallback = AccountClientFallback.class)
public interface AccountClient {

    @GetMapping("reduce")
    void reduce(@RequestParam("accountId") Long accountId, @RequestParam("amount") BigDecimal amount);

    @GetMapping("hello1")
    void hello();
}
