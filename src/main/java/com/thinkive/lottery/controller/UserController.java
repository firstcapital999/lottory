package com.thinkive.lottery.controller;

import com.thinkive.common.authority.entity.Role;
import com.thinkive.common.authority.entity.User;
import com.thinkive.lottery.dao.UserRepository;
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
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

    //用户操作服务
    @Autowired
    private UserRepository userRepository;

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
     * 用户注册
     * @param user
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/regist")
    public String save(@Valid User user, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new RuntimeException();
        }else{
            String password = this.passwordEncoder.encode(user.getPassword());
            user.setPassword(password);
            user.setEnabled("1");
            user.setRegistrationTime(new Date());
            Set<Role> roles = new HashSet<Role>();
            Role role = new Role();
            role.setUser(user);
            role.setRoleCode("admin");
            role.setRoleName("管理员");
            roles.add(role);
            user.setRoles(roles);
            this.userRepository.save(user);
            return "/activity/views/login";
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
