package com.thinkive.lottery.service.impl;

import com.thinkive.lottery.dao.PrizeRepository;
import com.thinkive.lottery.entity.Prize;
import com.thinkive.lottery.service.IPrizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Describe 奖项服务实现类
 * @Author dengchangneng
 * @CreateTime 2017年11月5日15:16:48
 */
@Service
public class PrizeServiceImpl implements IPrizeService {

    //奖项数据操作JPA接口类
    @Autowired
    private PrizeRepository prizeRepository;


    /**
     * @param activityId 活动ID
     * @return
     * @Describe 根据活动id查询奖项列表
     */
    @Override
    public List<Prize> getPrizeListByActivityId(Long activityId) {
        List<Prize> prizeList = this.prizeRepository.findByActivityId(activityId);
        return prizeList;
    }
}
