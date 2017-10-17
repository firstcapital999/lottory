package com.thinkive.lottery.controller;

import com.thinkive.common.entity.Result;
import com.thinkive.common.util.ResultUtil;
import com.thinkive.lottery.dao.UserRepository;
import com.thinkive.lottery.entity.Role;
import com.thinkive.lottery.entity.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by thinkive on 2017/10/8.
 */
@Controller
@EnableSwagger2
public class HelloWorldController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping(value = "/findAll")
    @ResponseBody
    @ApiOperation(value = "查询所有的方法")
    public List<User> userList(){
        return this.userRepository.findAll();
    }

    @PostMapping(value = "/insert")
    @ResponseBody
    @Transactional
    public User userAdd(@RequestParam("userName") String userName,@RequestParam("password") String password){
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        user.setEnabled("1");
        user.setRegistrationTime(new Date());
        return userRepository.save(user);

    }

   @GetMapping(value = "/home")
    public String getHome(){

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

    @GetMapping(value = "/testShort")
    public String testShort(){
        return "redirect:http://www.baidu.com";
    }

    //测试短链接
    @GetMapping(value = "/yourls/{id}")
    public String shortUrl(@PathVariable("id") Integer id) {
        return "redirect:http://www.baidu.com";
    }

    @PostMapping(value = "/regist")
    @ResponseBody
    public Result<User> save(@Valid User user, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResultUtil.error(1, bindingResult.getFieldError().getDefaultMessage());
        }else{
            String password = this.passwordEncoder.encode(user.getPassword());
            user.setPassword(password);
            user.setRegistrationTime(new Date());
            Set<Role> roles = new HashSet<Role>();
            Role role = new Role();
            role.setUser(user);
            role.setRoleCode("admin");
            role.setRoleName("管理员");
            roles.add(role);
            user.setRoles(roles);
            return ResultUtil.success(this.userRepository.save(user));
        }
    }




}
