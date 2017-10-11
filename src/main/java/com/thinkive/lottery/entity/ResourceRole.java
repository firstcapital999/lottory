package com.thinkive.lottery.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by thinkive on 2017/10/9.
 */
@Entity
public class ResourceRole {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length=50)
    private String roleId; //角色ID

    @Column(length=50)
    private String resourceId;//资源ID

    @Column(columnDefinition = "timestamp default CURRENT_TIMESTAMP", nullable = false)
    private Date updateTime;//更新时间

    public ResourceRole() {
    }

    public ResourceRole(String roleId, String resourceId, Date updateTime) {
        this.roleId = roleId;
        this.resourceId = resourceId;
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
