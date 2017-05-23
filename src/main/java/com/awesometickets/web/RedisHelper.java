package com.awesometickets.web;

import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;


/**
 * Provide methods to communicate with redis.
 */
public class RedisHelper {
    private Jedis jedis;
    private String host;
    private int port;

    public RedisHelper() {}

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
            jedis = new Jedis(host, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
