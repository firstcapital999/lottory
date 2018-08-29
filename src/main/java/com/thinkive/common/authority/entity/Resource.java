package com.thinkive.common.authority.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Describe 资源实体类
 * @Author dengchangneng
 * @CreateTime 2017年10月9日09:45:34
 */
@Entity
public class Resource implements Serializable {

    //主键ID
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    //url
    @Column(name = "url", length = 1000)
    private String url;

    //资源ID
    @Column(length = 50)
    private String resourceId;

    //备注
    @Column(length = 200)
    private String remark;

    //资源名称
    @Column(length = 400)
    private String resourceName;

    //资源所对应的方法名
    @Column(length = 400)
    private String methodName;

    //资源所对应的包路径
    @Column(length = 1000)
    private String methodPath;

    public Resource() {
    }

    public Resource(String url, String resourceId, String remark, String resourceName, String methodName, String methodPath) {
        this.url = url;
        this.resourceId = resourceId;
        this.remark = remark;
        this.resourceName = resourceName;
        this.methodName = methodName;
        this.methodPath = methodPath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodPath() {
        return methodPath;
    }

    public void setMethodPath(String methodPath) {
        this.methodPath = methodPath;
    }
}
