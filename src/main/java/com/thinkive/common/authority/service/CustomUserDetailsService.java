package com.thinkive.common.authority.service;

import com.thinkive.common.authority.entity.SecurityUser;
import com.thinkive.lottery.dao.UserRepository;
import com.thinkive.common.authority.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @Describe 登录校验类
 * @Author dengchangnegn
 * @CreateTime 2017年10月8日11:10:35
 */
@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired  //业务服务类
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //根据用户名查找用户信息
        System.out.println("登录用户名："+userName);
        User user = userRepository.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("UserName " + userName + " not found");
        }
        // SecurityUser实现UserDetails并将SysUser的name映射为username
/*        user.setPassword(passwordEncoder.encode("123456"));*/
        SecurityUser securityUser = new SecurityUser(user);
        return  securityUser;
    }

}