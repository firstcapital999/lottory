package com.thinkive.lottery.service.impl;

import com.alibaba.fastjson.JSON;
import com.thinkive.common.authority.entity.Role;
import com.thinkive.common.authority.entity.User;
import com.thinkive.common.constant.ExceptionConstant;
import com.thinkive.common.entity.Result;
import com.thinkive.common.util.ResultUtil;
import com.thinkive.lottery.constant.RedisConstant;
import com.thinkive.lottery.constant.UserConstant;
import com.thinkive.lottery.dao.UserRepository;
import com.thinkive.lottery.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @Describe 用户相关服务类
 * @auther  dengchangneng
 * @create 2017年11月1日13:25:22
 */
@Service
public class UserServiceImpl implements IUserService{

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    //用户表操作JPA接口
    @Autowired
    private UserRepository userRepository;

    //密码加解密服务
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    //redis操作模板类
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @Descibe 用户信息保存
     * @param user
     * @return User
     */
    @Override
    public User save(User user) {
        return this.userRepository.save(user);
    }

    /**
     * @Describe 注册用户信息
     * @param user
     * @return Result<User>
     */
    @Override
    public Result registerUser(User user) {
        //对密码进行加密
        String password = this.passwordEncoder.encode(user.getPassword());
        //将user的明文密码替换成加密后的密码
        user.setPassword(password);
        //初始化默认注册参数
        user = this.initDefaultRegisterParams(user);
        //保存用户信息
        user = this.userRepository.save(user);
        //将注册的用户数据存放到redis
        this.saveUserToRedis(user);
        return ResultUtil.success();
    }

    /**
     * @Describe 通过用户名查询用户信息
     * @param userName 用户名
     * @return User
     */
    @Override
    public Result<User> getUserForRedis(String userName) {
        Object userObject = this.redisTemplate.opsForValue().get(RedisConstant.USER_PREFIX_KEY+userName);
        if(userObject !=null){
            User user = JSON.parseObject(userObject.toString(),User.class);

            return ResultUtil.success(user);
        }else{
            return ResultUtil.error(ExceptionConstant.QUERY_NO_DATA_CODE,ExceptionConstant.QUERY_NO_DATA);
        }
    }

    /**
     * @Describe 初始化注册参数
     * @param user
     * @return
     */
    private User initDefaultRegisterParams(User user){
        //默认注册设置账号可用
        user.setEnabled(UserConstant.ACCOUNT_ENABLED);
        //记录服务器时间为注册时间
        user.setRegistrationTime(new Date());
        Set<Role> roles = new HashSet<Role>();
        Role role = new Role();
        role.setUser(user);
        role.setRoleCode(UserConstant.ADMIN_CODE);
        role.setRoleName(UserConstant.ADMIN_NAME);
        roles.add(role);
        user.setRoles(roles);
        return user;
    }

    /**
     * @Describe 保存用户信息到redis中
     * @param user 用户信息
     * @return
     */
    private void saveUserToRedis(User user){

        this.redisTemplate.opsForValue().set(RedisConstant.USER_PREFIX_KEY+user.getUserName(), JSON.toJSONString(user));


    }


    public static void main(String args[]){
        User user = new User();
        user.setPassword("12323");
        //默认注册设置账号可用
        user.setEnabled(UserConstant.ACCOUNT_ENABLED);
        //记录服务器时间为注册时间
        user.setRegistrationTime(new Date());
        Set<Role> roles = new HashSet<Role>();
        Role role = new Role();
        role.setUser(user);
        role.setRoleCode(UserConstant.ADMIN_CODE);
        role.setRoleName(UserConstant.ADMIN_NAME);
        roles.add(role);
        user.setRoles(roles);
        System.out.println(JSON.toJSONString(user));
    }


}
