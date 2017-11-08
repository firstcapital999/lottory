package com.thinkive.lottery.service;

import com.thinkive.common.authority.entity.User;
import com.thinkive.common.entity.Result;

/**
 * @Describe 用户操作服务接口
 * @Author dengchangneng
 * @CreateTime 2017年11月1日12:59:56
 */
public interface IUserService {

    /**
     * @param user
     * @return
     * @Describe 保存用户信息
     */
    public User save(User user);

    /**
     * @param user
     * @return
     * @Describe 注册用户信息
     */
    public Result registerUser(User user);


    /**
     * @param userName 用户名
     * @return User
     * @Describe 通过用户名查询用户信息
     */
    public Result<User> getUserForRedis(String userName);
}
