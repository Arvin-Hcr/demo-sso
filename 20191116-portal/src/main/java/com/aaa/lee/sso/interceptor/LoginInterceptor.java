package com.aaa.lee.sso.interceptor;

import com.aaa.lee.sso.service.LoginService;
import com.aaa.lee.sso.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Company AAA软件教育
 * @Author Seven Lee
 * @Date Create in 2019/11/16 9:55
 * @Description
 *      @Service:不涉及到任何的事务，只是单纯的注入使用
 *      在拦截器中使用value注解不被允许(代码守则)
 *      --->只要是Value注解必须要写在service层
 **/
@Service
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        // 再次匹配用户是否处于登录状态
        // 1.从cookie中获取redis的key值
        // 会有两种情况发生:第一种情况cookie的不为null(用户曾经登录过--->需要跳转到登录项目中去验证)
        // 第二种情况cookie的值为null，说明用户根本没有登录(直接需要让用户登录)
        String redisKey = CookieUtil.getCookieValue(httpServletRequest, loginService.cookieKey);
        // 2.判断RedisKey是否存在
        if(null == redisKey || "".equals(redisKey)) {
            // 说明用户根本就没有处于登录状态，需要跳转到登录页面
                // 第一种情况:如果用户在index页面的情况
                // 第二种情况:如果用户在其他的页面，登录成功之后需要返回上一个页面
                // 如果获取当前用户所在页面呢？
            //获取用户当前页面
            StringBuffer currentPage = httpServletRequest.getRequestURL();
            System.out.println(currentPage);
            // localhost:8082/turnLoginPage?returnUrl=currentPage
            //把用户当前页面发送给登录页面
            httpServletResponse.sendRedirect(loginService.baseUrl + loginService.loginUrl + loginService.loginParam + currentPage);
        } else {
            // 说明cookie有值，需要跳转到登录项目中去进行验证
                // cookie有值，说明用户曾经登录过
                // cookie有值，用户已经处于登录状态
            String userString = loginService.checkLogin(redisKey);
            // 3.判断从login项目中的返回值
            if(!"".equals(userString) && null != userString) {
                // 说明用户已经处于登录状态
                return true;
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
