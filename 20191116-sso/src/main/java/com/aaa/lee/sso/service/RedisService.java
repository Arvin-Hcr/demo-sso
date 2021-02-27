package com.aaa.lee.sso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

/**
 * @Company AAA软件教育
 * @Author Seven Lee
 * @Date Create in 2019/11/16 12:18
 * @Description
 **/
@Service
public class RedisService {

    @Autowired
    private JedisCluster jedisCluster;

    /**
     * @author Seven Lee
     * @description
     *      向redis中存入数据
     * @param [key, value]
     * @date 2019/11/16
     * @return java.lang.String
     * @throws 
    **/
    public String set(String key, String value) {
        return jedisCluster.set(key, value);
    }

    /**
     * @author Seven Lee
     * @description
     *      从redis中取数据
     * @param [key]
     * @date 2019/11/16
     * @return java.lang.String
     * @throws 
    **/
    public String get(String key) {
        return jedisCluster.get(key);
    }

    /**
     * @author Seven Lee
     * @description
     *      设置缓存的失效时间
     * @param [key, seconds]
     * @date 2019/11/16
     * @return java.lang.Long
     * @throws
    **/
    public Long expire(String key, Integer seconds) {
        return jedisCluster.expire(key, seconds);
    }

}
