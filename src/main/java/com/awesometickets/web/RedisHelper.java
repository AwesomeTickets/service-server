package com.awesometickets.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;


/**
 * Provide methods to communicate with redis.
 */
@Component
public class RedisHelper {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private ValueOperations<String, String> operations;

    @PostConstruct
    public void init() {
        operations = redisTemplate.opsForValue();
    }

    public RedisHelper() {}

    public void set(String key, String value) {
        operations.set(key, value);
    }

    public String get(String key) {
        return operations.get(key);
    }

    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    public void del(String key) {
        redisTemplate.delete(key);
    }
}
