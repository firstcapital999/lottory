package com.thinkive.common.authority.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Describe 资源角色关系映射实体
 * @Author dengchangnegn
 * @CreateTime 2017年10月9日09:38:32
 */
@Entity
public class ResourceRole {

    //主键ID
    @Id
    @GeneratedValue
    private Long id;

    //角色ID
    @Column(length = 50)
    private String roleId;

    //资源ID
    @Column(length = 50)
    private String resourceId;

    //更新时间
    @Column(columnDefinition = "timestamp default CURRENT_TIMESTAMP", nullable = false)
    private Date updateTime;

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
