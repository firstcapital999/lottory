package com.thinkive.common.authority.service;

import com.thinkive.common.authority.util.SecurityUser;
import com.thinkive.lottery.dao.UserRepository;
import com.thinkive.lottery.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired  //业务服务类
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //根据用户名查找用户信息
        System.out.println("登录用户名："+userName);
        User user = userRepository.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("UserName " + userName + " not found");
        }
        // SecurityUser实现UserDetails并将SysUser的name映射为username
        user.setPassword(passwordEncoder.encode("123456"));
        SecurityUser securityUser = new SecurityUser(user);
        return  securityUser;
    }

}