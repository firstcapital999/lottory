package com.thinkive.lottery.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

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

    public User(String userName, String password, String nickName, Date registrationTime, String enabled) {
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
        this.registrationTime = registrationTime;
        this.enabled = enabled;
    }

    public User() {
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
}
