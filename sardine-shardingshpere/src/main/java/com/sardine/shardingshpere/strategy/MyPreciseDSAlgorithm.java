package com.sardine.shardingshpere.strategy;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.math.BigInteger;
import java.util.Collection;

public class MyPreciseDSAlgorithm implements PreciseShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        // 参数1：所有可用分片 参数2：分片值
        BigInteger value = BigInteger.valueOf(preciseShardingValue.getValue());
        BigInteger res = value.mod(new BigInteger("2")).add(new BigInteger("1"));
        String key = "m" + res;
        if (collection.contains(key)) {
            return key;
        }
        throw new UnsupportedOperationException("route " + key + " is not supported. Please check your config");
    }
}
