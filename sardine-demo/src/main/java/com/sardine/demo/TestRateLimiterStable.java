package com.sardine.demo;

import com.google.common.util.concurrent.RateLimiter;

import java.util.ArrayList;
import java.util.List;

/**
 * 令牌桶实现限流算法
 * 令牌桶算法允许将一段时间内没有消费的令牌暂存到令牌桶中，用来突发消费
 * 稳定模式：令牌生成的速度恒定
 */
@SuppressWarnings("ALL")
public class TestRateLimiterStable {

    public static void main(String[] args) throws InterruptedException {
        // 每秒产生2个令牌
        RateLimiter rateLimiter = RateLimiter.create(2);
        for (int i = 0; i < 20; i++) {
            // 阻塞的方式获取令牌，返回获取到令牌的时间
            System.out.println(rateLimiter.acquire());
        }
    }
}
