package com.thinkive.lottery.controller;

import com.thinkive.lottery.dao.UserRepository;
import com.thinkive.common.authority.entity.Role;
import com.thinkive.common.authority.entity.User;
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

@Controller
public class UserController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    private RequestCache requestCache = new HttpSessionRequestCache();


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

    @GetMapping(value = "/")
    public String getIndex(){

        return "/activity/views/detail";
    }

    @GetMapping(value = "/login")
    public String login(){
        return "/activity/views/login";
    }

    @GetMapping(value = "/register")
    public String register(){
        return "/activity/views/register";
    }
}
