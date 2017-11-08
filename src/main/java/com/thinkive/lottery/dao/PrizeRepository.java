package com.thinkive.lottery.dao;

import com.thinkive.lottery.entity.Prize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Describe 奖项数据操作JPA接口类
 * @Author dengchangneng
 * @CreateTime 2017年11月5日10:34:35
 */
public interface PrizeRepository extends JpaRepository<Prize,Long> {

    /**
     * @Describe 通过活动ID获取奖项集合
     * @param activityId
     * @return
     */
    List<Prize> findByActivityId(Long activityId);

}
