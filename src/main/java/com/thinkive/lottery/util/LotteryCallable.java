package com.thinkive.lottery.util;

import com.thinkive.common.entity.Result;
import com.thinkive.lottery.service.ILotteryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

/**
 * 抽奖线程
 *
 * @auther AN dengchangneng
 * @create 2017/11/14
 */
@Component
public class LotteryCallable implements Callable<Result> {

    private final static Logger logger = LoggerFactory.getLogger(LotteryCallable.class);

    //活动ID
    @Value("${activity.id}")
    private String activityId;

    //抽奖服务类
    @Autowired
    private ILotteryService lotteryService;

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Result call() throws Exception {
        logger.info("副线程开始");
        UserDetails user = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        Result result = this.lotteryService.lotteryMain(user.getUsername(), this.activityId);
        logger.info("副线程结束");
        return result;
    }
}
