package com.sardine.shardingshpere.strategy;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.math.BigInteger;
import java.util.Collection;

/**
 * 根据score进行判断分片，分为60以上和60以下的
 */
public class MyPreciseTableAlgorithm implements PreciseShardingAlgorithm<Integer> {

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Integer> preciseShardingValue) {
        // 参数1：所有可用分片 参数2：分片值
        Integer score = preciseShardingValue.getValue();
        int shard = score >= 60 ? 1 : 2;
        String key = preciseShardingValue.getLogicTableName() + "_" + shard;
        if (collection.contains(key)) {
            return key;
        }
        throw new UnsupportedOperationException("route " + key + " is not supported. Please check your config");
    }
}
