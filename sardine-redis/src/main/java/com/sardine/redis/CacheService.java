package com.sardine.redis;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author keith
 */
@Service
public class CacheService {

    @Cacheable(value = "my_cache", key = "#key", unless = "#result == null")
    public String getSomething(Long key){
        return key + ":Hello";
    }
}
