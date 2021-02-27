package com.aaa.lee.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Company AAA软件教育
 * @Author Seven Lee
 * @Date Create in 2019/11/16 9:48
 * @Description
 **/
@Controller
public class BuyController {

    /**
     * @author Seven Lee
     * @description
     *      跳转到主页面
     * @param []
     * @date 2019/11/16
     * @return java.lang.String
     * @throws 
    **/
    @RequestMapping("/")
    public String turnIndexPage() {
        return "index";
    }

    /**
     * @author Seven Lee
     * @description
     *      跳转到购买手机的页面
     * @param []
     * @date 2019/11/16
     * @return java.lang.String
     * @throws 
    **/
    @RequestMapping("/buyPhone")
    public String buyPhone() {
        return "buy_phone";
    }

}
