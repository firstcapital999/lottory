package com.thinkive.lottery.service;

import com.thinkive.common.entity.Result;

/**
 * @Describe 抽奖接口
 * @Author dengchangneng
 * @CreateTime 2017年11月1日11:27:04
 */
public interface ILotteryService {


    /**
     * @param userName
     * @param activityId
     * @return
     * @Describe 抽奖主操作
     */
    public Result lotteryMain(String userName, String activityId);

    /**
     * @param activityId
     * @return
     * @throws Exception
     * @Describe 抽奖
     */
    public Result lotteryDraw(String activityId);
}
