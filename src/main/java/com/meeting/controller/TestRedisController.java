package com.meeting.controller;

import com.alibaba.fastjson.JSON;
import com.meeting.cache.RedisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//@Controller
public class TestRedisController {

    @Autowired
    private RedisDao redisDao;

    @RequestMapping("/set")
    public void set(String key, String value) {
        redisDao.set(key, value);
    }

    @RequestMapping("/keys")
    public String keys(){
        return JSON.toJSONString(redisDao.keys());
    }

    @RequestMapping("/get")
    public String get(String key){
        return redisDao.get(key);
    }
}
