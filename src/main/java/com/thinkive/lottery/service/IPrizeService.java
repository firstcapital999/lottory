package com.thinkive.lottery.service;

import com.thinkive.lottery.entity.Prize;

import java.util.List;

/**
 * @Describe 奖项服务类
 * @Author dengchangneng
 * @CreateTime 2017年11月5日15:19:06
 */
public interface IPrizeService {

    /**
     * @param activityId
     * @return
     * @Describe 根据活动id查询该活动的所有奖品信息
     */
    public List<Prize> getPrizeListByActivityId(Long activityId);
}
