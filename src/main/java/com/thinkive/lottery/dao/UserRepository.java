package com.thinkive.lottery.dao;

import com.thinkive.common.authority.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Describe 用户数据操作JPA服务接口类
 * @Author dengchangneng
 * @CreateTime 2017年10月8日10:35:36
 */
public interface UserRepository extends JpaRepository<User,Long> {

    /**
     * @Describe 通过用户名查询用户
     * @param userName 用户名
     * @return
     */
    User findByUserName(String userName);

}
