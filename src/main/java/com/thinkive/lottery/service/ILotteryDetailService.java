package com.thinkive.lottery.service;

import com.thinkive.lottery.entity.LotteryDetail;

/**
 * @Describe 抽奖明细接口类
 * @Author dengchangneng
 * @CreateTime 2017年11月13日23:10:45
 */
public interface ILotteryDetailService {

    /**
     * @Describe 保存抽奖明细
     * @param lotteryDetail 抽奖明细
     * @return 抽奖明细
     */
    public LotteryDetail save(LotteryDetail lotteryDetail);
}
