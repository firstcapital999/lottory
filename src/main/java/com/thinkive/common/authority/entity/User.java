package com.thinkive.common.authority.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by thinkive on 2017/10/8.
 */
@Entity
public class User implements Serializable{

    @Id
    @GeneratedValue
    private Long id;

    private String userName;

    private String password;

    private String nickName;

    @Column(columnDefinition = "timestamp default CURRENT_TIMESTAMP", nullable = false)
    private Date registrationTime;

    private String enabled;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
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