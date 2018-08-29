package com.thinkive.common.authority.entity;

import com.thinkive.common.validator.IsMobile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;


/**
 * @Describe 用户实体表
 * @Author dengchangneng
 * @CreateTime 2017年10月8日10:43:53
 */
@Entity
public class User implements Serializable {

    //主键ID
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    //用户名
    @NotNull
    @IsMobile
    private String userName;

    //密码
    @NotNull
    private String password;

    //昵称
    private String nickName;

    //注册时间
    @Column(columnDefinition = "timestamp default CURRENT_TIMESTAMP", nullable = false)
    private Date registrationTime;

    //是否可用，0表示不可用，1表示可用
    private String enabled;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    private Set<Role> roles;

    public User() {
    }

    public User(String userName, String password, String nickName, Date registrationTime, String enabled, Set<Role> roles) {
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
        this.registrationTime = registrationTime;
        this.enabled = enabled;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Date getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Date registrationTime) {
        this.registrationTime = registrationTime;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
