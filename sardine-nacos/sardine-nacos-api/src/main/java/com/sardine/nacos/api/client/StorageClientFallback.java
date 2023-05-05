package com.sardine.nacos.api.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StorageClientFallback implements StorageClient {

    @Override
    public void reduce(Long goodsId, int count) {
        log.error("库存扣减失败, GoodsId: {}, 数量: {}", goodsId, count);
    }

    @Override
    public void hello() {
        log.error("Storage Service Say Hello Failed!");
    }
}
