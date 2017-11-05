package com.thinkive.lottery.service.impl;

import com.thinkive.lottery.dao.PrizeRepository;
import com.thinkive.lottery.entity.Prize;
import com.thinkive.lottery.service.IPrizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by thinkive on 2017/11/5.
 */
@Service
public class PrizeServiceImpl implements IPrizeService{

    @Autowired
    private PrizeRepository prizeRepository;


    @Override
    public List<Prize> getPrizeListByActivityId(Long activityId) {
        List<Prize> prizeList = this.prizeRepository.findByActivityId(activityId);
        return prizeList;
    }
}
