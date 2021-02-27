package com.aaa.lee.sso.service;

import com.aaa.lee.sso.mapper.UserMapper;
import com.aaa.lee.sso.model.User;
import com.aaa.lee.sso.utils.CookieUtil;
import com.aaa.lee.sso.utils.JSONUtil;
import com.aaa.lee.sso.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Company AAA软件教育
 * @Author Seven Lee
 * @Date Create in 2019/11/16 12:14
 * @Description
 **/
@Service
public class UserService {

    @Value("${cookie_key}")
    private String cookieKey;
    @Value("${expire_timeout}")
    private Integer expireTimeout;

    @Autowired
    private UserMapper userMapper;

    /**
     * @author Seven Lee
     * @description
     *      执行登录操作
     * @param [user, redisService, request, response]
     * @date 2019/11/16
     * @return java.lang.String
     * @throws 
    **/
    public String doLogin(User user, RedisService redisService, HttpServletRequest request, HttpServletResponse response) {
        // 1.从数据库中查询用户(根据用户名和密码查询)
        User u = userMapper.selectUsernameAndPassword(user);
        // 2.判断u对象是否为null
        if(null != u) {
            // 说明从数据库中查询出了数据，登录成功
            u.setPassword(null);
            // 3.要把u存入redis中
            // redis的key值不能固定
            String redisKey = UUIDUtil.getUUID() + u.getId();
            // 4.把u对象存入到redis
            String setResult = redisService.set(redisKey, JSONUtil.toJsonString(u));
            String m = redisService.get(redisKey);
            System.out.println(m);
            redisService.expire(redisKey, expireTimeout);
                // 5.判断redis是否保存成功
                if ("OK".equals(setResult.toUpperCase())) {
                    // 说明redis的存入成功
                    // 需要把redis的key保存到Cookie中(因为没有中文，所以不需要定义编码)
                    CookieUtil.setCookie(request, response, cookieKey, redisKey, 86400);
                    return "success";
                }
        }
        return null;
    }

    /**
     * @author Seven Lee
     * @description
     *      检查是否处于登录状态
     * @param [redisService, redisKey]
     * @date 2019/11/16
     * @return java.lang.String
     * @throws 
    **/
    public String checkLogin(RedisService redisService, String redisKey) {
        return redisService.get(redisKey);
    }

}
