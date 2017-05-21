package com.awesometickets.web;

import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;

/**
 * Cache service.
 */
public class RedisService {
    private Jedis jedis = null;
    private String redisServerIP = null;
    private Integer redisServerPort = null;

    public RedisService() {
    }

    public String getSingleValueByKey(String key) {
        return jedis.get(key);
    }

    public void setSingleValue(String key, String value) {
        jedis.set(key, value);
    }

    @PostConstruct
    public void setupAndConnect() {
        if (redisServerIP == null) redisServerIP = "localhost";
        if (redisServerPort == null) redisServerPort = 6379;
        try {
            jedis = new Jedis(this.redisServerIP, this.redisServerPort);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getRedisServerIP() {
        return redisServerIP;
    }
    public void setRedisServerIP(String redisServerIP) {
        this.redisServerIP = redisServerIP;
    }

    public Integer getRedisServerPort() {
        return redisServerPort;
    }
    public void setRedisServerPort(String redisServerPort) {
        this.redisServerPort = Integer.valueOf(redisServerPort);
    }


}
