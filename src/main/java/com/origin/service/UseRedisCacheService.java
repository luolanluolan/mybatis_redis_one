package com.origin.service;

public interface UseRedisCacheService {
    public void put(String key, Object value, long seconds);
    public Object get(String key);
}
