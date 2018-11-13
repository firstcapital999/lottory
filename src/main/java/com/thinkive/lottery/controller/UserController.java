package com.thinkive.lottery.controller;

import com.thinkive.common.authority.entity.User;
import com.thinkive.common.entity.Result;
import com.thinkive.common.util.ResultUtil;
import com.thinkive.common.constant.ExceptionConstant;
import com.thinkive.lottery.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * @author dengchengneng
 * @Describe 用户登录注册控制器
 * @createDate 2017年10月26日14:15:57
 */
@Controller
public class UserController {

    //密码加解密服务
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

   /* //用户操作服务
    @Autowired
    private UserRepository userRepository;*/

    //用户操作服务
    @Autowired
    private IUserService userService;

    //session获取服务
//    private RequestCache requestCache = new HttpSessionRequestCache();


    /**
     * @param user
     * @return
     * @Describe 验证用户是否登录
     */
    @RequestMapping(value = "checkUser")
    @ResponseBody
    public Result checkUserLogin(@AuthenticationPrincipal UserDetails user) {
        if (user != null) {
            return ResultUtil.success();
        } else {
            return ResultUtil.error(ExceptionConstant.ERROR_CODE,ExceptionConstant.ERROR);
        }
    }

    /**
     * @param user          用户
     * @param bindingResult 绑定的结果
     * @return String
     * @Describe 用户注册
     */
    @PostMapping(value = "/regist")
    @ResponseBody
    public Result save(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultUtil.error(ExceptionConstant.ERROR_CODE, ExceptionConstant.CHECK_ERROR);
        } else {
            //注册用户
            Result result = this.userService.registerUser(user);

            return result;
        }
    }

    /**
     * @return
     * @Describe 用户首页
     */
    @GetMapping(value = "/")
    public String getIndex() {

        return "/activity/views/detail";
    }

    /**
     * @return
     * @Describe 登录页面
     */
    @GetMapping(value = "/login")
    public String login() {
        return "/activity/views/login";
    }

    /**
     * @return
     * @Describe 注册页面
     */
    @GetMapping(value = "/register")
    public String register() {
        return "/activity/views/register";
    }
}
