package com.thinkive.lottery.service;

import com.thinkive.common.entity.Result;

import java.util.Map;

/**
 * @Describe 抽奖接口
 * @Author dengchangneng
 * @CreateTime 2017年11月1日11:27:04
 */
public interface ILotteryService {

    /**
     * @Describe 抽奖
     * @param activityId
     * @return
     * @throws Exception
     */
    public Map<String,Object> lottery(String activityId) throws Exception;

    public Result lotteryMain(String userName, String activityId);
}
