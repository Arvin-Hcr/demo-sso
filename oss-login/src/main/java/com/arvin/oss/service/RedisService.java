package com.arvin.oss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import javax.lang.model.element.NestingKind;

/**
 * @ClassName: RedisService
 * @Description: TODO
 * @Author: Arvin @hand-china.com
 * @Date: 2020/11/30
 * @Since version-1.0
 **/
@Service
public class RedisService {
    @Autowired
    private JedisCluster jedisCluster;

    /**
     * 向redis中存储数据
     * @param key
     * @param value
     * @return
     */
    public String set(String key,String value){
        return jedisCluster.set(key, value);
    }

    public String get(String key){
        return jedisCluster.get(key);
    }

    public Long expire(String key,Integer seconds){
        return jedisCluster.expire(key,seconds);
    }
}
