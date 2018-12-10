package com.thinkive.lottery.service.impl;

import com.thinkive.lottery.dao.LotteryDetailRepository;
import com.thinkive.lottery.entity.LotteryDetail;
import com.thinkive.lottery.service.ILotteryDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @Describe 抽奖明细服务实现类
 * @Author dengchangneng
 * @CreateTime 2017年11月13日23:13:10
 */
@SuppressWarnings("ALL")
@Service
public class LotteryDetailServiceImpl implements ILotteryDetailService {

    @Autowired
    private LotteryDetailRepository lotteryDetailRepository;

    /**
     * @param lotteryDetail 抽奖明细
     * @return 抽奖明细
     * @Describe 保存抽奖明细
     */
    @SuppressWarnings("AlibabaTransactionMustHaveRollback")
    @Override
    @Transactional
    public LotteryDetail save(LotteryDetail lotteryDetail) {
        return this.lotteryDetailRepository.save(lotteryDetail);
    }
}
