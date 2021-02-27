package com.arvin.oss.controller;

import com.arvin.oss.model.User;
import com.arvin.oss.service.RedisService;
import com.arvin.oss.service.UserService;
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
 * @ClassName: LoginController
 * @Description: TODO
 * @Author: Arvin @hand-china.com
 * @Date: 2020/11/30
 * @Since version-1.0
 **/
@Controller
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;

    @RequestMapping("/turnLoginPage")
    public String turnLoginPage(HttpServletRequest request, ModelMap modelMap){
        String returnUrl = request.getParameter("returnUrl");
        if (null != returnUrl && !"".equals(returnUrl)){
            modelMap.addAttribute("returnUrl", request);
            return "login";
        }
        return "404";
    }

    @RequestMapping("/doLogin")
    @ResponseBody
    public String doLogin(User user,HttpServletRequest request,HttpServletResponse response){
        return userService.doLogin(user,redisService,request,response);
    }

    @RequestMapping("/token/{redisKey}")
    @ResponseBody
    public Object checkLogin(@PathVariable String redisKey,String callback) {
        String userString = userService.checkLogin(redisService, redisKey);
        if (!"".equals(userString) && null != userString) {
            if (!"".equals(callback) && null != callback) {
                MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userString);
                mappingJacksonValue.setJsonpFunction(callback);
                return mappingJacksonValue;
            } else {
                return userString;
            }
        }
        return null;
    }
}
