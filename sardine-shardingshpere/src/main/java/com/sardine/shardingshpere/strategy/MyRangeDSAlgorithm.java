package com.sardine.shardingshpere.strategy;

import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.Collection;

public class MyRangeDSAlgorithm implements RangeShardingAlgorithm<Long> {

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Long> rangeShardingValue) {
        // 实现按照 Between 进行范围分片，得到上限和下限，进行一些计算
        Long lowerEndpoint = rangeShardingValue.getValueRange().lowerEndpoint();
        Long upperEndpoint = rangeShardingValue.getValueRange().upperEndpoint();
        System.out.println("lowerEndpoint:" + lowerEndpoint);
        System.out.println("upperEndpoint:" + upperEndpoint);
        // 对于我们这个奇偶分离的场景，大部分范围查询都是两张表都要查
        return collection;
    }
}
