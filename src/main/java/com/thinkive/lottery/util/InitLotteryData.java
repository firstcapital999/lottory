package com.thinkive.lottery.util;

import com.alibaba.fastjson.JSON;
import com.thinkive.lottery.constant.RedisConstant;
import com.thinkive.lottery.entity.Prize;
import com.thinkive.lottery.filter.FastJSONFilter;
import com.thinkive.lottery.service.IPrizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Describe 初始化抽奖数据
 * @Author dengchangneng
 * @Create 2017年11月5日11:05:53
 */
@Component
public class InitLotteryData implements CommandLineRunner {

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${activity.id}")
    private Long activityId;

    @Autowired
    private IPrizeService prizeService;

    @Autowired
    private FastJSONFilter fastJSONFilter;

    @Override
    public void run(String... strings) throws Exception {
        //初始化奖品数据
        String awardListKey = RedisConstant.AWARD_LIST_PREFIX_KEY + this.activityId;
        Map<String, Object> awardList = redisTemplate.opsForHash().entries(awardListKey);
        if (awardList.size() == 0) {
            List<Prize> prizeList = this.prizeService.getPrizeListByActivityId(this.activityId);
            if (prizeList == null || prizeList.size() <= 0) {
                throw new RuntimeException("程序初始化数据异常！");
            } else {
                //将奖品数据存入redis
                this.setPrizeDataToRedis(awardListKey,prizeList);
                //将库存数据存入redis
                String awardPoolNumKey = RedisConstant.AWARD_POOL_NUM_PREFIX_KEY+this.activityId;
                this.setPrizePoolNumToRedis(awardPoolNumKey,prizeList);


            }
        }


        System.out.println("数据加载完成");
    }


    private void setPrizeDataToRedis(String awardListKey ,List<Prize> prizeList){
        List<Map<String,Object>> prizeMaps = BeanMapUtil.objectsToMaps(prizeList);
        for(int i = 0;i<prizeMaps.size();i++){
            String key = String.valueOf(prizeMaps.get(i).get("id"));
            String prize = JSON.toJSONString(prizeMaps.get(i),fastJSONFilter);
            System.out.print(prize);
            this.redisTemplate.opsForHash().put(awardListKey,key,prize);
        }
    }

    private void setPrizePoolNumToRedis(String awardPoolNumKey,List<Prize> prizeList){
        for(int i = 0;i<prizeList.size();i++){
            Long remainingNum = prizeList.get(i).getRemainingPrize();
            String key = String.valueOf(prizeList.get(i).getId());
            this.redisTemplate.opsForHash().increment(awardPoolNumKey,key,remainingNum);
        }
    }





}
