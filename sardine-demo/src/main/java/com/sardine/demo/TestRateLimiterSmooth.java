package com.sardine.demo;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;

/**
 * 令牌桶实现限流算法
 * 平滑限流，从冷启动速率（满的）到平均消费速率的时间间隔
 */
@SuppressWarnings("all")
public class TestRateLimiterSmooth {
    public static void main(String[] args) throws InterruptedException {
        RateLimiter rateLimiter = RateLimiter.create(2, 1000L, TimeUnit.MILLISECONDS);
        for (int i = 0; i < 20; i++) {
            // 阻塞的方式获取令牌，返回获取到令牌的时间
            System.out.println(rateLimiter.acquire());
        }
    }
}
