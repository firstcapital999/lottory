package com.thinkive.lottery.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Activity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 32)
    private String activityCode;

    @Column(length = 1024)
    private String activityName;

    @Column(columnDefinition = "char",length = 1)
    private String status;

    @Column(columnDefinition = "timestamp default CURRENT_TIMESTAMP", nullable = false)
    private Date startTime;

    @Column(columnDefinition = "timestamp default CURRENT_TIMESTAMP", nullable = false)
    private Date endTime;

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
