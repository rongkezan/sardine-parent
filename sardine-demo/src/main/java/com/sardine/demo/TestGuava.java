package com.sardine.demo;

import com.google.common.cache.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Guava: Achieve current limiting
 * 实现每秒只有10个线程来执行方法
 */
public class TestGuava {
    private static LoadingCache<Long, AtomicLong> counter = CacheBuilder.newBuilder()
            .expireAfterWrite(2, TimeUnit.SECONDS)
            .removalListener(notification -> {
                // 缓存移除的时候会触发这个监听器
            })
            .build(new CacheLoader<Long, AtomicLong>() {
                @Override
                public AtomicLong load(Long second) {
                    System.out.println("load:" + second);
                    return new AtomicLong(0);
                }
            });


    public static void main(String[] args) throws ExecutionException {
        // 使用当前的秒级时间作为key
        long currSecond = System.currentTimeMillis() / 1000;
        // 模拟执行100次
        for (int i = 0; i < 100; i++) {
            for (;;){
                // 每秒最多放行10个请求，否则自旋
                if (counter.get(currSecond).incrementAndGet() <= 10){
                    break;
                }
            }
            System.out.println(i);
        }
    }
}
