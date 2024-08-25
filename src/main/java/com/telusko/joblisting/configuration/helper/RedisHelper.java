package com.telusko.joblisting.configuration.helper;

import com.telusko.joblisting.exception.code.RedisException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
@Data
public class RedisHelper {

    @Autowired
    CacheManager cacheManager;

    //evict cache
    public void evictCache(String cacheName, Object key) {
        if (isNull(cacheName) || isNull(key)) {
            throw new RedisException("Empty request to update Cache");
        }
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.evict(key);
        }
    }

    //update cache
    public void putCache(String cacheName, Object key, Object value) {
        if (isNull(cacheName) || isNull(key) || isNull(value)) {
            throw new RedisException("Empty request to update Cache");
        }
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.put(key, value);
        }
    }

    //fetch cache
    public Object getFromCache(String cacheName, Object key) {
        if (isNull(cacheName) || isNull(key)) {
            throw new RedisException("Empty request to update Cache");
        }
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            return cache.get(key, Object.class);
        }
        return null;
    }

}
