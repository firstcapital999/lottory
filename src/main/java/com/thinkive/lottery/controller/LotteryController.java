package com.thinkive.lottery.controller;

import com.thinkive.common.entity.Result;
import com.thinkive.lottery.service.ILotteryService;
import com.thinkive.lottery.util.LotteryCallable;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Callable;

/**
 * @Describe 抽奖控制器类
 * @auther AN dengchangneng
 * @createTime 2017年10月26日12:27:53
 */
@RestController
@Api("抽奖相关API")
public class LotteryController {

    private final static Logger logger = LoggerFactory.getLogger(LotteryController.class);

    //活动ID
    @Value("${activity.id}")
    private String activityId;

    //抽奖服务类
    @Autowired
    private ILotteryService lotteryService;

    //抽奖线程
    @Autowired
    private LotteryCallable lotteryCallable;

    /**
     *
     * @return
     * @Describe 抽奖
     */
    /*@GetMapping(value = "/lottery")*/
    @ApiOperation(
            value = "抽奖活动-抽奖 ",
            notes = "抽奖活动-抽奖"
    )
    @RequestMapping(value = "/lottery", method = RequestMethod.POST)
    public Callable<Result> lottery() {

        logger.info("主线程启动");
        Callable<Result> result = this.lotteryCallable;
        logger.info("主线程结束");
        return result;
    }

    /**
     * 获取redis中的奖品信息
     * @param start 开始位置
     * @param end 结束位置
     * @return
     */
    @ApiOperation(
            value = "抽奖活动-获取redis中的奖品信息",
            notes = "抽奖活动-获取redis中的奖品信息"
    )
    @ApiImplicitParams({@ApiImplicitParam(
            name = "start",
            value = "start",
            required = true,
            paramType = "query",
            dataType = "Long"
    ),
            @ApiImplicitParam(
                    name = "end",
                    value = "end",
                    required = true,
                    paramType = "query",
                    dataType = "Long"
            )})
    @PostMapping(value = "/getLatestAwardList")
    public Result getLatestAwardList(@RequestParam("start") long start, @RequestParam("end") long end) {

        return this.lotteryService.getLatestAwardList(this.activityId, start, end);

    }
}
