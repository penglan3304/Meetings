package com.meeting.cache;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class RedisDao {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private JedisPool jedisPool;

    private Jedis jedis;

    public RedisDao(int ip, String host) {
        jedisPool = new JedisPool(host, ip);
        jedis = jedisPool.getResource();
    }

    /**
     * redis set
     *
     * @param key   key
     * @param value value
     */
    public void set(String key, String value) {
        jedis.set(key, value);
    }

    /**
     * redis keys *
     *
     * @return set<String>
     */
    public Set<String> keys() {
        return jedis.keys("*");
    }


    /**
     * redis get key
     *
     * @param key key
     * @return value
     */
    public String get(String key) {
        return jedis.get(key);
    }

    /**
     * redis setEx
     *
     * @param key    key
     * @param value  value
     * @param expire expire
     */
    public void setEx(String key, String value, int expire) {
        jedis.setex(key, expire, value);
    }


}
