package com.aaa.lee.sso.controller;

import com.aaa.lee.sso.model.User;
import com.aaa.lee.sso.service.RedisService;
import com.aaa.lee.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Company AAA软件教育
 * @Author Seven Lee
 * @Date Create in 2019/11/16 11:54
 * @Description
 **/
@Controller
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;

    /**
     * @author Seven Lee
     * @description
     *      跳转到登录页面
     * @param [returnUrl]
     * @date 2019/11/16
     * @return java.lang.String
     * @throws 
    **/
    @RequestMapping("/turnLoginPage")
    public String turnLoginPage(HttpServletRequest request, ModelMap modelMap) {
        //接到发送的值
        String returnUrl = request.getParameter("returnUrl");
        if(null != returnUrl && !"".equals(returnUrl)) {
            // 说明用户之前浏览页面了
            modelMap.addAttribute("returnUrl", returnUrl);
            return "login";
        }
        return "404";
    }

    /**
     * @author Seven Lee
     * @description
     *      执行登录操作
     * @param [user, request, response]
     * @date 2019/11/16
     * @return java.lang.String
     * @throws 
    **/
    @RequestMapping("/doLogin")
    @ResponseBody
    public String doLogin(User user, HttpServletRequest request, HttpServletResponse response) {
        return userService.doLogin(user, redisService, request, response);
    }

    /**
     * @author Seven Lee
     * @description
     *      检测用户是否处于登录状态
     *      必须要知道这个方法是跨域被请求的
     *      所有的跨域请求的controller中必须要接收一个固定的特定参数(callback)--->固定的，不能随意写(全部小写)
     *      callback就是来标识该方法是跨域被请求的方法(ajax-->jsonp)
     * @param [redisKey, callback]
     * @date 2019/11/16
     * @return java.lang.String
     * @throws 
    **/
    @RequestMapping("/token/{redisKey}")
    @ResponseBody
    public Object checkLogin(@PathVariable String redisKey, String callback) {
        // 1.从redis中获取用户信息
        String userString = userService.checkLogin(redisService, redisKey);
        // 2.判断是否为null
        if(!"".equals(userString) && null != userString) {
            // 说明登录成功
            // 3.判断callback是否为null
            if(!"".equals(callback) && null != callback) {
                // 该请求为跨域请求，并且用户已经处于登录状态，需要返回给8081项目的一个状态
                MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userString);
                mappingJacksonValue.setJsonpFunction(callback);
                return mappingJacksonValue;
            } else {
                // 说明该请求是一个普通的请求
                return userString;
            }
        }
        return null;
    }

}
