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
 * 用户登录注册控制器
 * @author dengchengneng
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
    private RequestCache requestCache = new HttpSessionRequestCache();


    /**
     * 验证用户是否登录
     * @param user
     * @return
     */
    @RequestMapping(value = "checkUser")
    @ResponseBody
    public String checkUserLogin(@AuthenticationPrincipal UserDetails user){
        if(user!=null){
            System.out.println("用户不为空");
        }else{
            System.out.println("用户为空");
        }
        return null;
    }

    /**
     * @Describe 用户注册
     * @param user 用户
     * @param bindingResult 绑定的结果
     * @return String
     */
    @PostMapping(value = "/regist")
    @ResponseBody
    public  Result<User> save(@Valid User user, BindingResult bindingResult){
        Result<User> result = new Result<User>();
        if (bindingResult.hasErrors()) {
            return ResultUtil.error(ExceptionConstant.ERROR_CODE,ExceptionConstant.CHECK_ERROR);
        }else{
            //注册用户
             result  =this.userService.registerUser(user);

            return result;
        }
    }

    /**
     * 用户首页
     * @return
     */
    @GetMapping(value = "/")
    public String getIndex(){

        return "/activity/views/detail";
    }

    /**
     * 登录页面
     * @return
     */
    @GetMapping(value = "/login")
    public String login(){
        return "/activity/views/login";
    }

    /**
     * 注册页面
     * @return
     */
    @GetMapping(value = "/register")
    public String register(){
        return "/activity/views/register";
    }
}
