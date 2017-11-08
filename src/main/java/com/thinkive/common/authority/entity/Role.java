package com.thinkive.common.authority.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Describe 角色实体类
 * @Author dengchangneng
 * @CreateTime 2017年10月9日09:40:33
 */
@Entity
public class Role implements Serializable {

    //主键ID
    @Id
    @GeneratedValue
    private Long id;

    //用户
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "u_id", nullable = false)
    private User user;

    //角色名称
    private String roleName;

    //角色编码
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
