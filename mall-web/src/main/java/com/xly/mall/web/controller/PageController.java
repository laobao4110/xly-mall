package com.xly.mall.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/pageController")
public class PageController {

    /**
     * 到注册页
     *
     * @param
     * @return Result
     */
    @RequestMapping(value = "toRegister", method = RequestMethod.GET)
    public String toRegister() {
        return "/user/register";
    }

    /**
     * 到登录页
     *
     * @param
     * @return Result
     */
    @RequestMapping(value = "toLoginPage")
    public String toLoginPage() {
        return "login";
    }

    /**
     * 到主页
     *
     * @param
     * @return Result
     */
    @RequestMapping(value = "toHome", method = RequestMethod.POST)
    public String toHome() {
        return "home";
    }

    /**
     * 到主页
     *
     * @param
     * @return Result
     */
    @RequestMapping(value = "toBaidu")
    public String toBaidu() {
        return "forward:www.baidu.com";
    }
}
