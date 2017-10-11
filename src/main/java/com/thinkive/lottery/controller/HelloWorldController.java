package com.thinkive.lottery.controller;

import com.thinkive.lottery.dao.UserRepository;
import com.thinkive.lottery.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by thinkive on 2017/10/8.
 */
@Controller
public class HelloWorldController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/findAll")
    @ResponseBody
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


}
