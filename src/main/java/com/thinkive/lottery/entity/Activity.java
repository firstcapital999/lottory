package com.thinkive.lottery.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * @Describe 活动实体类
 * @Author dengchangneng
 * @CreateTime 2017年10月8日14:01:33
 */
@Entity
public class Activity {
    //主键ID
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    //活动编码
    @Column(length = 32)
    private String activityCode;

    //活动名称
    @Column(length = 1024)
    private String activityName;

    //状态 0表示失败，1表示成功
    @Column(columnDefinition = "char", length = 1)
    private String status;

    //活动开始时间
    @Column(columnDefinition = "timestamp default CURRENT_TIMESTAMP", nullable = false)
    private Date startTime;

    //活动结束时间
    @Column(columnDefinition = "timestamp default CURRENT_TIMESTAMP", nullable = false)
    private Date endTime;

    //奖项
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "activity")
    private Set<Prize> prizes;


    public Activity() {
    }

    public Activity(String activityCode, String activityName, String status, Date startTime, Date endTime, Set<Prize> prizes) {
        this.activityCode = activityCode;
        this.activityName = activityName;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
        this.prizes = prizes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Set<Prize> getPrizes() {
        return prizes;
    }

    public void setPrizes(Set<Prize> prizes) {
        this.prizes = prizes;
    }
}
