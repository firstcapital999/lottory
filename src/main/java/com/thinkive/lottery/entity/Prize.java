package com.thinkive.lottery.entity;

import javax.persistence.*;

/**
 * @Describe 奖项实体表
 * @Author dengchangneng
 * @CreateTime 2017年10月8日14:48:11
 */
@Entity
public class Prize {

    //主键ID
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    //活动
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;

    //奖项
    @Column(columnDefinition = "char", length = 1)
    private String prizeType;

    //奖项名称
    @Column(length = 1024)
    private String prizeName;

    //奖项总数
    private Long prizeTotalNum;

    //剩余奖品数
    private Long remainingPrize;

    //中奖概率
    @Column(columnDefinition = "double(4,2) default '0.00'")
    private Double prizeRate;

    //是否可用，0表示可不用，1表示可用
    @Column(columnDefinition = "char", length = 1)
    private String enabled;

    public Prize() {
    }

    public Prize(Activity activity, String prizeType, String prizeName, Long prizeTotalNum, Long remainingPrize, Double prizeRate, String enabled) {
        this.activity = activity;
        this.prizeType = prizeType;
        this.prizeName = prizeName;
        this.prizeTotalNum = prizeTotalNum;
        this.remainingPrize = remainingPrize;
        this.prizeRate = prizeRate;
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String getPrizeType() {
        return prizeType;
    }

    public void setPrizeType(String prizeType) {
        this.prizeType = prizeType;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public Long getPrizeTotalNum() {
        return prizeTotalNum;
    }

    public void setPrizeTotalNum(Long prizeTotalNum) {
        this.prizeTotalNum = prizeTotalNum;
    }

    public Long getRemainingPrize() {
        return remainingPrize;
    }

    public void setRemainingPrize(Long remainingPrize) {
        this.remainingPrize = remainingPrize;
    }

    public Double getPrizeRate() {
        return prizeRate;
    }

    public void setPrizeRate(Double prizeRate) {
        this.prizeRate = prizeRate;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }
}
