package com.thinkive.lottery.service.impl;

import com.thinkive.common.authority.entity.User;
import com.thinkive.lottery.dao.UserRepository;
import com.thinkive.lottery.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户相关服务类
 *
 * @auther AN dengchangneng
 * @create 2017/10/26
 */
@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        return this.userRepository.save(user);
    }
}
