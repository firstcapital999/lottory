package com.thinkive.common.authority.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by thinkive on 2017/10/9.
 */
@Entity
public class Role implements Serializable{

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "u_id", nullable = false)
    private User user;

    private String roleName;

    private String roleCode;

    public Role() {
    }

    public Role(User user, String roleName, String roleCode) {
        this.user = user;
        this.roleName = roleName;
        this.roleCode = roleCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
}
