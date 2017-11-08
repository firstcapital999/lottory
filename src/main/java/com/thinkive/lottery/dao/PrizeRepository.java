package com.thinkive.lottery.dao;

import com.thinkive.lottery.entity.Prize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by thinkive on 2017/11/5.
 */
public interface PrizeRepository extends JpaRepository<Prize,Long> {

    List<Prize> findByActivityId(Long activityId);

}
