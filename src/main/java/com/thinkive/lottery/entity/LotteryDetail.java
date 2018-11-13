package com.thinkive.lottery.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Describe 中奖明细表
 * @auther dengchangneng
 * @createTime 2017年11月1日15:37:34
 */
@Entity
public class LotteryDetail implements Serializable {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 活动id
     */
    private Long activityId;

    /**
     * 奖品ID
     */
    private Long prizeId;

    /**
     * 中奖时间
     */
    @Column(columnDefinition = "timestamp default CURRENT_TIMESTAMP", nullable = false)
    private Date createTime;

    //中奖状态
    @Column(columnDefinition = "char", length = 1)
    private String awardStatus;


    public LotteryDetail() {
    }

    public LotteryDetail(Long userId, Long activityId, Long prizeId, Date createTime, String awardStatus) {
        this.userId = userId;
        this.activityId = activityId;
        this.prizeId = prizeId;
        this.createTime = (Date) createTime.clone();
        this.awardStatus = awardStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(Long prizeId) {
        this.prizeId = prizeId;
    }

    public Date getCreateTime() {
        Date temp = createTime;
        return temp;
    }

    public void setCreateTime(Date createTime) {
        Date temp = createTime;
        this.createTime = (Date) temp.clone();
    }

    public String getAwardStatus() {
        return awardStatus;
    }

    public void setAwardStatus(String awardStatus) {
        this.awardStatus = awardStatus;
    }
}
