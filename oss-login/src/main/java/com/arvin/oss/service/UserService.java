package com.arvin.oss.service;

import com.arvin.oss.mapper.UserMapper;
import com.arvin.oss.model.User;
import com.arvin.oss.utils.CookieUtil;
import com.arvin.oss.utils.JSONUtil;
import com.arvin.oss.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: UserService
 * @Description: TODO
 * @Author: Arvin @hand-china.com
 * @Date: 2020/11/30
 * @Since version-1.0
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
    * @description: 执行登录操作
    * @param
    * @return
    */
    public String doLogin(User user, RedisService redisService, HttpServletRequest request, HttpServletResponse response){
        User user1 = userMapper.selectUsernameAndPassword(user);
        if (null != user1){
            user1.setPassword(null);
            String redisKey = UUIDUtil.getUUID() + user1.getId();
            String setResult = redisService.set(redisKey, JSONUtil.toJsonString(user1));
            redisService.expire(redisKey,expireTimeout);
            if ("OK".equals(setResult.toUpperCase())){
                CookieUtil.setCookie(request,response,cookieKey,redisKey,86400);
                return "success";
            }
        }
        return null;
    }

    public String checkLogin(RedisService redisService,String redisKey){
        return redisService.get(redisKey);
    }
}
