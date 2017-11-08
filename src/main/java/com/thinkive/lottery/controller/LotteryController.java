package com.thinkive.lottery.controller;

import com.thinkive.common.constant.ExceptionConstant;
import com.thinkive.common.entity.Result;
import com.thinkive.common.util.ResultUtil;
import com.thinkive.lottery.service.ILotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 抽奖控制器类
 *
 * @auther AN dengchangneng
 * @create 2017/10/26
 */
@RestController
@EnableSwagger2
public class LotteryController {

    @Value("${activity.id}")
    private String activityId;

    @Autowired
    private ILotteryService lotteryService;

    @GetMapping(value = "/lottery")
    public Result lottery(@AuthenticationPrincipal UserDetails user){
        //查询是否已经抽过奖

        //查询是否是在活动期间内注册的
        if(user!=null){

            return this.lotteryService.lotteryMain(user.getUsername(),this.activityId);

        }else{
            return ResultUtil.error(ExceptionConstant.USER_NOT_LOGIN_CODE,ExceptionConstant.USER_NOT_LOGIN);
        }



    }
}
