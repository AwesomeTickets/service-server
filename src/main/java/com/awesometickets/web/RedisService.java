package com.awesometickets.web;

import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;


/**
 * Service that provides methods to communicate with redis.
 */
public class RedisService {
    private Jedis jedis;
    private String ip;
    private int port;

    public RedisService() {}

    public void set(String key, String value) {
        jedis.set(key, value);
    }

    public String get(String key) {
        return jedis.get(key);
    }

    public boolean exists(String key) {
        return jedis.exists(key);
    }

    public void del(String key) {
        jedis.del(key);
    }

    @PostConstruct
    public void setupAndConnect() {
        try {
            jedis = new Jedis(ip, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
