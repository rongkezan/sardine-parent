package com.sardine.nacos.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "sardine-nacos-storage", fallback = StorageClientFallback.class)
public interface StorageClient {

    /**
     * 扣减库存
     *
     * @param goodsId 商品ID
     * @param count   扣减库存数量
     */
    @GetMapping("reduce")
    void reduce(@RequestParam("goodsId") Long goodsId, @RequestParam("count") int count);

    @GetMapping("hello1")
    void hello();
}
