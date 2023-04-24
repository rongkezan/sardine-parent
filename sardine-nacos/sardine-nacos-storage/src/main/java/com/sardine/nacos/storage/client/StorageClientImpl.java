package com.sardine.nacos.storage.client;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sardine.nacos.api.client.StorageClient;
import com.sardine.nacos.storage.entity.GoodsDo;
import com.sardine.nacos.storage.mapper.GoodsMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class StorageClientImpl implements StorageClient {

    @Resource
    private GoodsMapper goodsMapper;

    @Override
    @Transactional
    public void reduce(Long goodsId, int count) {
        goodsMapper.update(null, Wrappers.<GoodsDo>lambdaUpdate()
                .eq(GoodsDo::getGoodsId, goodsId)
                .setSql(count > 0, "stock = stock - " + count)
        );
        throw new RuntimeException();
    }
}
