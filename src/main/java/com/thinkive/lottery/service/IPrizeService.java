package com.thinkive.lottery.service;

import com.thinkive.lottery.entity.Prize;

import java.util.List;

/**
 * Created by thinkive on 2017/11/5.
 */
public interface IPrizeService {

    /**
     * @Describe 根据活动id查询该活动的所有奖品信息
     * @param activityId
     * @return
     */
    public List<Prize> getPrizeListByActivityId(Long activityId);
}
