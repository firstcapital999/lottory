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
 * @Describe 抽奖控制器类
 * @auther AN dengchangneng
 * @createTime 2017年10月26日12:27:53
 */
@RestController
@EnableSwagger2
public class LotteryController {

    //活动ID
    @Value("${activity.id}")
    private String activityId;

    //抽奖服务类
    @Autowired
    private ILotteryService lotteryService;

    /**
     * @param user 验证用户是否登录
     * @return
     * @Describe 抽奖
     */
    @GetMapping(value = "/lottery")
    public Result lottery(@AuthenticationPrincipal UserDetails user) {

        //如果user为空，则未登录，不为空则已登录

        if (user != null) {

            //执行抽奖程序，返回抽奖的结果集
            return this.lotteryService.lotteryMain(user.getUsername(), this.activityId);

        } else {
            //返回未登录的错误提示
            return ResultUtil.error(ExceptionConstant.USER_NOT_LOGIN_CODE, ExceptionConstant.USER_NOT_LOGIN);
        }


    }
}
