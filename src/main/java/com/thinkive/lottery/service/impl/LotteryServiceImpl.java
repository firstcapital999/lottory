package com.thinkive.lottery.service.impl;

import com.alibaba.fastjson.JSON;
import com.thinkive.common.authority.entity.User;
import com.thinkive.common.constant.ExceptionConstant;
import com.thinkive.common.entity.Result;
import com.thinkive.common.util.ResultUtil;
import com.thinkive.lottery.constant.RedisConstant;
import com.thinkive.lottery.service.ILotteryService;
import com.thinkive.lottery.service.IUserService;
import com.thinkive.lottery.util.AliasMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Describe 抽奖服务实现类
 * @Author dengchangneng
 * @CreateTime 2017年10月8日14:57:22
 */
@Service
public class LotteryServiceImpl implements ILotteryService {

    //日志类
    private Logger logger = LoggerFactory.getLogger(LotteryServiceImpl.class);

    //redis模板类
    @Autowired
    private RedisTemplate redisTemplate;

    //用户服务类
    @Autowired
    private IUserService userService;

    //从配置文件中读取活动开始时间
    @Value("${activity.beginDate}")
    private String activityBeginDate;

    //从配置文件中读取活动结束时间
    @Value("${activity.endDate}")
    private String activityEndDate;


    /**
     * @param userName   用户名
     * @param activityId 活动ID
     * @return
     * @Describe 抽奖主程序
     */
    @Override
    public Result lotteryMain(String userName, String activityId) {
        //获取用户信息
        Result<User> userResult = this.userService.getUserForRedis(userName);
        if (ExceptionConstant.SUCCESS_CODE != userResult.getCode()) {
            return userResult;
        }
        //获取用户信息
        User userRedis = userResult.getData();

        //判断用户是否有抽奖的资格
        Result lotterAuthResult = this.validLotteryAuth(userRedis, activityId);
        if (ExceptionConstant.SUCCESS_CODE != lotterAuthResult.getCode()) {
            return lotterAuthResult;
        }

        //执行抽奖操作

        Result lotteryResult = this.lotteryDraw(activityId);
        if (ExceptionConstant.SUCCESS_CODE != lotteryResult.getCode()) {
            return lotteryResult;
        }
        Map<String, Object> prize = (Map<String, Object>) lotteryResult.getData();
        //插入中奖记录
        Map<String, Object> prizeDetailMap = new HashMap<String, Object>();
        prizeDetailMap.put("activityId", activityId);
        prizeDetailMap.put("userId", userRedis.getId());
        prizeDetailMap.put("prizeId", prize.get("id"));
        prizeDetailMap.put("awardStatus", "1");
        prizeDetailMap.put("createTime", new Date());
        this.redisTemplate.opsForList().leftPush(RedisConstant.USER_AWARD_PREFIX_KEY + activityId + userRedis.getId(), prizeDetailMap);
        return ResultUtil.success(prizeDetailMap);
    }

    /**
     * @param activityId
     * @return
     * @throws Exception
     * @Describe 抽奖
     */
    @Override
    public Result lotteryDraw(String activityId) {
         /* 奖品概率集 */
        List<Double> prob = new ArrayList<Double>();
        /* 奖品结果集 */
        List<Map<String, Object>> selectAwardList = new ArrayList<Map<String, Object>>();
        //获取活动奖品列表
        Result<Map<String, Object>> activityAwardList = this.getActivityAwardList(activityId);
        if (ExceptionConstant.SUCCESS_CODE != activityAwardList.getCode()) {
            return activityAwardList;
        }
        Map<String, Object> awardList = activityAwardList.getData();
        //获取可抽取的奖品及其的概率集合
        Map<String, Object> optionalAwardMap = this.getAllAwardProbability(awardList, activityId);
        //获取可选的奖品集合
        selectAwardList = (List<Map<String, Object>>) optionalAwardMap.get("selectAwardList");
        //获取可选奖品集合的总概率
        double probabilityTotal = (double) optionalAwardMap.get("probabilityTotal");
        if (probabilityTotal < 100) {
            //当中奖概率不等于100%时，补充默认奖品
            selectAwardList = this.supplementDefaultAward(selectAwardList, probabilityTotal);
        }
        //生成抽奖用的概率集合
        Result<List<Double>> probabilityResult = this.generatorAwardProbability(selectAwardList);
        if (ExceptionConstant.SUCCESS_CODE != probabilityResult.getCode()) {
            return probabilityResult;
        }
        prob = probabilityResult.getData();

        /* 实例化抽奖算法,并抽奖 */
        AliasMethod aliasMethod = new AliasMethod(prob);

            /* 开始抽奖 */
        int index = aliasMethod.next();

        // 返回抽中奖品
        Map<String, Object> selectAward = selectAwardList.get(index);
        if (selectAward != null) {
            //判断是否是默认的奖品，如果不是，进行更新库存操作
            String prizeType = (String) selectAward.get("prizeType");
            if (!"1".equals(prizeType)) {
                Long limit = this.redisTemplate.opsForHash().increment(RedisConstant.AWARD_POOL_NUM_PREFIX_KEY, String.valueOf(selectAward.get("id")), -1);
                if (limit < 0) {
                    return ResultUtil.error(ExceptionConstant.NO_PRIZE_CODE, ExceptionConstant.NO_PRIZE);
                }
            }


        }

        return ResultUtil.success(selectAward);
    }


    /**
     * @param selectAwardList 奖品选项
     * @return
     * @Describe 生成奖品概率
     */
    private Result<List<Double>> generatorAwardProbability(List<Map<String, Object>> selectAwardList) {
        List<Double> prob = new ArrayList<Double>();
        try {
                /* 遍历所有奖品,取得奖品概率生成随机算法计算规则 */
            for (Map<String, Object> ps : selectAwardList) {
                double rate = ((BigDecimal) ps.get("prizeRate")).doubleValue();
                double tmp = rate / 100;
                prob.add(tmp);
            }
            return ResultUtil.success(prob);

        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error(ExceptionConstant.INIT_ERROR_PROBABILITY_CODE, ExceptionConstant.INIT_ERROR_PROBABILITY);
        }


    }


    /**
     * @param selectAwardList
     * @param probabilityTotal
     * @return
     * @Describe 补充默认的奖品
     */
    private List<Map<String, Object>> supplementDefaultAward(List<Map<String, Object>> selectAwardList, double probabilityTotal) {
        Map<String, Object> pullPrize = new HashMap<String, Object>();
        pullPrize.put("activityId", "1"); //补充活动ID
        pullPrize.put("enabled", "1");
        pullPrize.put("prizeName", "会员体验");
        pullPrize.put("prizeType", "1");
        BigDecimal bigDecimal = new BigDecimal(100 - probabilityTotal);
        pullPrize.put("prizeRate", bigDecimal);
        pullPrize.put("id", 9);
        selectAwardList.add(pullPrize);
        return selectAwardList;
    }


    /**
     * @param awardList  奖品集合
     * @param activityId 活动id
     * @return
     * @Describe 获取所有的奖品概率
     */
    private Map<String, Object> getAllAwardProbability(Map<String, Object> awardList, String activityId) {
        //返回的结果集
        Map<String, Object> result = new HashMap<String, Object>();


         /* 中奖率累计 */
        double probabilityTotal = 0.0;

        /* 奖品结果集 */
        List<Map<String, Object>> selectAwardList = new ArrayList<Map<String, Object>>();
        for (Map.Entry<String, Object> entry : awardList.entrySet()) {
            String obj = (String) entry.getValue();
            Map<String, Object> awrad = JSON.parseObject(obj);
            String awardId = String.valueOf(awrad.get("id"));
            //查询奖品剩余的数量
            int limit = this.getAwardsNum(awardId, activityId);
            if (limit > 0) {
                // 取出当前概率
                double probability = ((BigDecimal) awrad.get("prizeRate")).doubleValue();
                probabilityTotal += probability;
                selectAwardList.add(awrad);
            } else {
                continue;
            }
        }
        result.put("probabilityTotal", probabilityTotal);
        result.put("selectAwardList", selectAwardList);
        return result;
    }


    /**
     * @param awardId
     * @param activityId
     * @return
     * @Describe 获取redis奖品数量池中剩余的奖品数
     */
    private int getAwardsNum(String awardId, String activityId) {
        String awardPoolKey = RedisConstant.AWARD_POOL_NUM_PREFIX_KEY + activityId;
        //默认返回数量为0
        int num = 0;
        String count = (String) redisTemplate.opsForHash().get(awardPoolKey, awardId);
        if (count == null) {
            return num;
        } else {
            try {
                num = Integer.parseInt(count);
            } catch (Exception e) {
                e.printStackTrace();
                num = 0;
            }
            return num;
        }
    }


    /**
     * @param user
     * @return
     * @Describe 验证用户抽奖权限
     */
    private Result validLotteryAuth(User user, String activityId) {
        //第一步校验用户是否活动期间内注册用户
        Result activityAuthResult = this.validUserRegisterTime(user);
        if (ExceptionConstant.SUCCESS_CODE != activityAuthResult.getCode()) {
            return activityAuthResult;
        }
        Result userPrizeResult = this.validUserPrizeForRedis(user, activityId);
        if (ExceptionConstant.SUCCESS_CODE != userPrizeResult.getCode()) {
            return userPrizeResult;
        }
        return ResultUtil.success();
    }


    /**
     * @param user
     * @return
     * @Describe 验证用户是否是活动期间内注册的
     */
    private Result validUserRegisterTime(User user) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginDate = null;
        Date endDate = null;
        try {
            beginDate = format.parse(this.activityBeginDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return ResultUtil.error(ExceptionConstant.BEGINDATE_PARSE_ERROR_CODE, ExceptionConstant.BEGINDATE_PARSE_ERROR);
        }
        try {
            endDate = format.parse(this.activityEndDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return ResultUtil.error(ExceptionConstant.ENDDATE_PARSE_ERROR_CODE, ExceptionConstant.ENDDATE_PARSE_ERROR);
        }

        Date registerTime = user.getRegistrationTime();

        if (registerTime.getTime() <= endDate.getTime() && registerTime.getTime() >= beginDate.getTime()) {
            return ResultUtil.success();
        } else {
            return ResultUtil.error(ExceptionConstant.NON_ACTIVITY_PERIOD_CODE, ExceptionConstant.NON_ACTIVITY_PERIOD);
        }

    }


    /**
     * @param user
     * @return
     * @Describe 检查用户是否抽奖
     */
    private Result validUserPrizeForRedis(User user, String activityId) {
        Object awardRecordObject = this.redisTemplate.opsForValue().get(RedisConstant.USER_AWARD_PREFIX_KEY + activityId + user.getId());
        if (awardRecordObject != null) {
            return ResultUtil.error(ExceptionConstant.HAS_DRAW_CODE, ExceptionConstant.HAS_DRAW, awardRecordObject);
        } else {
            return ResultUtil.success();
        }
    }


    /**
     * @param activityId 活动ID
     * @return
     * @Describe 获取活动对应的奖品列表
     */
    private Result<Map<String, Object>> getActivityAwardList(String activityId) {

        String awardListKey = RedisConstant.AWARD_LIST_PREFIX_KEY + activityId;
        Map<String, Object> awardList = redisTemplate.opsForHash().entries(awardListKey);
        if (awardList.size() == 0) {
            return ResultUtil.error(ExceptionConstant.QUERY_NO_ACTIVITY_AWARD_LIST_CODE, ExceptionConstant.QUERY_NO_ACTIVITY_AWARD_LIST);
        } else {
            return ResultUtil.success(awardList);
        }
    }


}
