package com.thinkive.lottery.dao;

import com.thinkive.lottery.entity.LotteryDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Describe 抽奖明细数据服务接口
 * @Author dengchangneng
 * @CreateTime 2017年11月13日23:15:20
 */
public interface LotteryDetailRepository extends JpaRepository<LotteryDetail,Long> {

}
