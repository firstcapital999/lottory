package com.thinkive.lottery.queue;

import com.thinkive.lottery.constant.RedisConstant;
import com.thinkive.lottery.entity.LotteryDetail;
import com.thinkive.lottery.service.ILotteryDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class AsyncServiceImpl implements AsyncService {

    //活动ID
    @Value("${activity.id}")
    private String activityId;

    //redis模板类
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ILotteryDetailService lotteryDetailService;

    @Override
    @Async("asyncServiceExecutor")
    public void executeAsync() {
        while (true) {
            Map<String, Object> map = (Map<String, Object>) this.redisTemplate.opsForList().rightPop(RedisConstant.ACTIVITY_AWARD_QUEUE_PREFIX_KEY + this.activityId);
            if (map != null) {
                LotteryDetail lotteryDetail = new LotteryDetail();
                lotteryDetail.setActivityId(Long.parseLong((String) map.get("activityId")));
                lotteryDetail.setAwardStatus((String) map.get("awardStatus"));
                lotteryDetail.setCreateTime((Date) map.get("createTime"));
                lotteryDetail.setPrizeId(((Integer)map.get("prizeId")).longValue());
                lotteryDetail.setUserId((Long) map.get("userId"));
                this.lotteryDetailService.save(lotteryDetail);
            }
        }
    }
}
