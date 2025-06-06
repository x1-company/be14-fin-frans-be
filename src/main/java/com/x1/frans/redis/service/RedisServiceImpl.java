package com.x1.frans.redis.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(String prefix, String key, String value, long expireMillis) {
        redisTemplate.opsForValue().set(prefix + ":" + key, value, Duration.ofMillis(expireMillis));
    }

    public String get(String prefix, String key) {
        return redisTemplate.opsForValue().get(prefix + ":" + key);
    }

    public void remove(String prefix, String key) {
        redisTemplate.delete(prefix + ":" + key);
    }

    public boolean exists(String prefix, String key) {
        return redisTemplate.hasKey(prefix + ":" + key);
    }
}
