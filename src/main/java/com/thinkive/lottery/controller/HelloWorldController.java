package com.thinkive.lottery.controller;

import com.thinkive.lottery.dao.UserRepository;
import com.thinkive.common.authority.entity.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * @Describe 测试控制器类
 * @Author dengchangnegn
 * @CreateTime 2017年10月8日12:19:36
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
    public List<User> userList() {
        return this.userRepository.findAll();
    }

    @PostMapping(value = "/insert")
    @ResponseBody
    @Transactional
    public User userAdd(@RequestParam("userName") String userName, @RequestParam("password") String password) {
        User user = new User();
        user.setUsername(userName);
        user.setPassword(password);
        user.setEnabled("1");
        user.setRegistrationTime(new Date());
        return userRepository.save(user);

    }

    @GetMapping(value = "/home")
    public String getHome() {

        return "/activity/views/detail";
    }


    @GetMapping(value = "/testShort")
    public String testShort() {
        return "redirect:http://www.baidu.com";
    }

    //测试短链接
    @GetMapping(value = "/yourls/{id}")
    public String shortUrl(@PathVariable("id") Integer id) {
        return "redirect:http://www.baidu.com";
    }


}
