package com.x1.frans.redis.service;

public interface RedisService {

    void save(String prefix, String key, String value, long expires);

    String get(String prefix, String key);

    void remove(String prefix, String key);

    boolean exists(String prefix, String key);

    void incrementCount(String prefix, String key);
}
