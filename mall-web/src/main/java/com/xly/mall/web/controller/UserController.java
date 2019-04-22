package com.xly.mall.web.controller;

import com.xly.mall.common.base.result.Result;
import com.xly.mall.common.base.result.ResultGenerator;
import com.xly.mall.common.base.result.ServiceResult;
import com.xly.mall.common.domain.user.pojo.User;
import com.xly.mall.common.util.NetworkUtil;
import com.xly.mall.core.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ResultGenerator resultGenerator;
    @Autowired
    private HttpServletRequest request;

     /**
      * @method  用户登录
      * @description 描述一下方法的作用
      * @date: 2019/4/20/020 17:29
      * @author: xiaoluyu
      */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Result login(User user) {
        ServiceResult<String, User> serviceResult = userService.userLogin(user, NetworkUtil.getIpAddress(request));
        return resultGenerator.generate(serviceResult);
    }

    /**
     * 添加用户
     *
     * @param user
     * @return Result
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Result addUser(User user, BindingResult validResult) {
        ServiceResult<String, Integer> serviceResult = userService.addUser(user);
        return resultGenerator.generate(serviceResult.getErrorCode(), serviceResult.getResult());
    }


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
}
