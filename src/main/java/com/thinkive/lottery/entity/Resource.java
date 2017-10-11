package com.thinkive.lottery.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by thinkive on 2017/10/9.
 */
@Entity
public class Resource implements Serializable{

    @Id
    @GeneratedValue
    private Long id;

    @Column(name="url",length=1000)
    private String url;//url

    @Column(length=50)
    private String resourceId;//资源ID

    @Column(length=200)
    private String remark;//备注

    @Column(length=400)
    private String resourceName;//资源名称

    @Column(length=400)
    private String methodName;//资源所对应的方法名

    @Column(length=1000)
    private String methodPath;//资源所对应的包路径

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
