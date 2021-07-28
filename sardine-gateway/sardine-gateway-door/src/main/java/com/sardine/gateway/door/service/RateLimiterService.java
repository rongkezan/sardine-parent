package com.sardine.gateway.door.service;

import org.redisson.api.RRateLimiter;

/**
 * rate limiter service
 *
 * @author keith
 */
public interface RateLimiterService {

    /**
     * get rate limiter which key is made by ip and url.
     *
     * @param ip    request ip
     * @param url   request url
     * @return      rate limiter
     */
    RRateLimiter getRateLimiter(String ip, String url);
}
