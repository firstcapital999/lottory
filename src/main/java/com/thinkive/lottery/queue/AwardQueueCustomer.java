package com.thinkive.lottery.queue;

import com.thinkive.lottery.constant.RedisConstant;
import com.thinkive.lottery.entity.LotteryDetail;
import com.thinkive.lottery.service.ILotteryDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @Describe 奖品队列消费者
 * @Author dengchangneng
 * @CreateTime 2017年11月13日23:21:51
 */
@Component
public class AwardQueueCustomer implements Runnable {

    //活动ID
    @Value("${activity.id}")
    private String activityId;

    //redis模板类
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ILotteryDetailService lotteryDetailService;

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
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
