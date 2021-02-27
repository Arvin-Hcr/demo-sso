package com.aaa.lee.sso.service;

import com.aaa.lee.sso.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Company AAA软件教育
 * @Author Seven Lee
 * @Date Create in 2019/11/16 10:10
 * @Description
 *      LoginService:和事务没有关系
 **/
@Service
public class LoginService {

    @Value("${sso_base_url}")
    public String baseUrl;
    @Value("${sso_access_login_url}")
    public String loginUrl;
    @Value("${sso_access_url}")
    public String tokenUrl;
    @Value("${sso_login_url_param}")
    public String loginParam;
    @Value("${cookie_key}")
    public String cookieKey;

    /**
     * @author Seven Lee
     * @description
     *      检查用户是否处于登录状态(需要传递redisKey，也就是要传递cookie的value值)
     *      必须要跳转到login项目中，必须要远程发送，restful
     * @param [redisKey]
     * @date 2019/11/16
     * @return java.lang.String
     * @throws 
    **/
    public String checkLogin(String redisKey) {
        // localhost:8082/token/3127846187632187
        return HttpClientUtil.doGet(baseUrl + tokenUrl + redisKey );
    }

}
