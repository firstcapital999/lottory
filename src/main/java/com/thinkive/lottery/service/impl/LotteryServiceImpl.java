package com.thinkive.lottery.service.impl;

import com.alibaba.fastjson.JSON;
import com.thinkive.common.authority.entity.User;
import com.thinkive.common.entity.Result;
import com.thinkive.common.util.ResultUtil;
import com.thinkive.common.constant.ExceptionConstant;
import com.thinkive.lottery.constant.RedisConstant;
import com.thinkive.lottery.service.ILotteryService;
import com.thinkive.lottery.service.IUserService;
import com.thinkive.lottery.util.AliasMethod;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class LotteryServiceImpl implements ILotteryService {

    private Logger logger = LoggerFactory.getLogger(LotteryServiceImpl.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IUserService userService;

    @Value("${activity.beginDate}")
    private String activityBeginDate;

    @Value("${activity.endDate}")
    private String activityEndDate;


    @Override
    public Map<String, Object> lottery(String activityId) throws Exception {
        /* 奖品概率集 */
        List<Double> prob = new ArrayList<Double>();

        /* 奖品结果集 */
        List<Map<String,Object>> selectAwardList = new ArrayList<Map<String,Object>>();

        /* 根据活动信息,获取到缓存中所有奖品信息 */
        try
        {
            /* 中奖率累计 */
            double probabilityTotal = 0.0;
            /* 获取活动对应奖品集 */
            try
            {
                // 获取所有活动奖品键
                String awardListKey = RedisConstant.prizeCacheKey + "_" + activityId;
                Map<String,Object> awardList =  redisTemplate.opsForHash().entries(awardListKey);
/*

                List<DataRow> awardList = redisClient.hgetListObj(awardListKey, Map.class);*/
                if ( awardList.size() == 0 )
                {
                    logger.info("没有获取到活动对应奖品信息. 活动唯一键[" + activityId + "].");
                    return null;
                }

                for(Map.Entry<String, Object> entry : awardList.entrySet()){
                    String obj = (String) entry.getValue();
                    Map<String,Object> awrad = JSON.parseObject(obj);
                    String awardId = (String) awrad.get("id");

                    String awardPoolKey = RedisConstant.prizePoolCacheKey + "_" + activityId;

                    String amount = (String) redisTemplate.opsForHash().get(awardPoolKey,awardId);

                    if ( amount == null || Integer.parseInt(amount) <= 0 )
                    {
                        continue;
                    }
                      /* 过滤活动周期限制,周期内抽奖次数达到上限的奖品类别,不参与抽奖 */
                    String cycle = (String) awrad.get("win_unit"); // 中奖频率
                    // 0
                    // 月、1
                    // 天数
                    // 、2
                    // 周
                    String limitWinNum = (String) awrad.get("win_total"); // 限定中奖名额
                    if ( cycle != null && !"".equals(cycle) )
                    { // 存在频率限制类型
                        if ( limitWinNum != null && !"".equals(limitWinNum) )
                        { // 存在频率限制数量
                            Calendar calendar = Calendar.getInstance();
                            try
                            {
                                int type = Integer.parseInt(cycle);
                                int limit = Integer.parseInt(limitWinNum);
                                switch (type)
                                {
                                    case 1:

                                        /* 抽奖频率 次/天 */
                                        String date = DateFormatUtils.format(calendar, "yyyyMMdd");
                                        String day = RedisConstant.activityDayTotelOfParticipationCacheKey + "_"
                                                + activityId + "_" + awrad.get("id") + "_" + date;

                                        String dn = (String) redisTemplate.opsForValue().get(day);
                                        if ( dn != null && dn.matches("\\d+") )
                                        {
                                            if ( Integer.parseInt(dn) >= limit )
                                            {
                                                continue;
                                            }
                                        }
                                        break;
                                    case 2:

                                        /* 抽奖频率 次/周 */
                                        int week = calendar.get(Calendar.WEEK_OF_YEAR);
                                        String cacheKeyW = RedisConstant.activityWeekTotelOfParticipationCacheKey
                                                + "_" + activityId + "_" + awrad.get("id") + "_" + week;
                                        String wn = (String) redisTemplate.opsForValue().get(cacheKeyW);
                                        if ( wn != null && wn.matches("\\d+") )
                                        {
                                            if ( Integer.parseInt(wn) >= limit )
                                            {
                                                continue;
                                            }
                                        }
                                        break;
                                    case 0:

                                        /* 抽奖频率 次/月 */
                                        int month = calendar.get(Calendar.MONTH) + 1;
                                        String cacheKeyM = RedisConstant.activityMonthTotelOfParticipationCacheKey
                                                + "_" + activityId + "_" + awrad.get("id") + "_" + month;
                                        String mn = (String) redisTemplate.opsForValue().get(cacheKeyM);
                                        if ( mn != null && mn.matches("\\d+") )
                                        {
                                            if ( Integer.parseInt(mn) >= limit )
                                            {
                                                continue;
                                            }
                                        }
                                        break;
                                    default:

                                        break;
                                }
                            }
                            catch (Exception e)
                            {
                                logger.error("抽奖频率限制出现异常! 奖品类型[" + awardId + "]", e);
                            }
                        }
                    }
                    // 取出当前概率
                    double probability = (double) awrad.get("probability");

                    /* 获取该奖品的中奖率,累加中奖率 */
                    probabilityTotal += probability;

                    // 选中奖品列表
                    selectAwardList.add(awrad);
                }
            }
            catch (Exception e)
            {
                logger.error("获取活动奖品信息异常!", e);
                throw new IllegalAccessException("获取活动奖品信息异常! 活动唯一ID:" + activityId);
            }

            // 改成那个什么礼包
            if ( probabilityTotal < 100 )
            {
                Map<String,Object> pullPrize = new HashMap<String,Object>();
                pullPrize.put("id", "20"); //补充活动ID
                pullPrize.put("money", 0); // 设置为0元
                pullPrize.put("amount", 0); // 设置数量0
                pullPrize.put("name", "开心奖");
                pullPrize.put("introduce", "3688钻石会员资讯包");
                pullPrize.put("probability", (100 - probabilityTotal));
                pullPrize.put("pic_url", "../wx/images/redIcon/VIP.png");
                pullPrize.put("is_hb", 0); // 无价值奖品
                pullPrize.put("is_default", 1);
                pullPrize.put("orderline", "0"); //默认奖品
                pullPrize.put("rank", 0); //排名
                pullPrize.put("type", "0");//奖品类型(1为大将，0为小奖
                selectAwardList.add(pullPrize);
            }

            if ( selectAwardList.size() == 0 )
            {
                logger.info("获取到奖品集合,但所有奖品均超过周期内抽奖次数! 活动唯一键[" + activityId + "].");
                return null;
            }

            try
            {
                /* 遍历所有奖品,取得奖品概率生成随机算法计算规则 */
                for (Map<String,Object> ps : selectAwardList)
                {
                    double rate = (double) ps.get("probability");
                    double tmp = rate / 100;
                    prob.add(tmp);
                }

            }
            catch (Exception e)
            {
                throw new IllegalAccessException("初始化抽奖算法规则异常! 活动唯一ID:" + activityId);
            }

            /* 实例化抽奖算法,并抽奖 */
            AliasMethod aliasMethod = new AliasMethod(prob);

            /* 开始抽奖 */
            int index = aliasMethod.next();

            // 返回抽中奖品
            Map<String,Object> selectAward = selectAwardList.get(index);

            //            logger.error("抽奖列表1："+JSON.toJSONString(selectAwardList));
            //            logger.error("抽奖列表2："+JSON.toJSONString(prob));
            //            logger.error("抽奖列表3："+JSON.toJSONString(selectAward));
            return selectAward;
        }
        catch (Exception e)
        {
            logger.error("活动抽奖业务服务接口出现异常!", e);
            throw new Exception("活动抽奖业务服务接口出现异常! 异常信息 :[" + e.getMessage() + "]");
        }
    }

    @Override
    public Result lotteryMain(String userName, String activityId) {
        //获取用户信息
        Result<User> userResult = this.userService.getUserForRedis(userName);
        if(ExceptionConstant.SUCCESS_CODE!=userResult.getCode()){
            return userResult;
        }
        //获取用户信息
        User user = userResult.getData();

        //判断用户是否有抽奖的资格
        Result lotterAuthResult = this.validLotteryAuth(user);
        if(ExceptionConstant.SUCCESS_CODE!=lotterAuthResult.getCode()){
            return lotterAuthResult;
        }

        //执行抽奖操作

        return null;
    }


    /**
     * @Describe 验证用户抽奖权限
     * @param user
     * @return
     */
    private Result validLotteryAuth(User user){
        //第一步校验用户是否活动期间内注册用户
        Result activityAuthResult = this.validUserRegisterTime(user);
        if(ExceptionConstant.SUCCESS_CODE!=activityAuthResult.getCode()){
            return activityAuthResult;
        }
        Result userPrizeResult = this.validUserPrizeForRedis(user);
        if(ExceptionConstant.SUCCESS_CODE!=userPrizeResult.getCode()){
            return userPrizeResult;
        }
        return ResultUtil.success();
    }



    /**
     * 验证用户是否是活动期间内注册的
     * @param user
     * @return
     */
    private Result validUserRegisterTime(User user){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginDate = null;
        Date endDate = null;
        try {
            beginDate = format.parse(this.activityBeginDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return ResultUtil.error(ExceptionConstant.BEGINDATE_PARSE_ERROR_CODE,ExceptionConstant.BEGINDATE_PARSE_ERROR);
        }
        try {
            endDate = format.parse(this.activityEndDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return ResultUtil.error(ExceptionConstant.ENDDATE_PARSE_ERROR_CODE,ExceptionConstant.ENDDATE_PARSE_ERROR);
        }

        Date registerTime = user.getRegistrationTime();

        if(registerTime.getTime()<=endDate.getTime() && registerTime.getTime()>=beginDate.getTime()){
            return ResultUtil.success();
        }else{
            return ResultUtil.error(ExceptionConstant.NON_ACTIVITY_PERIOD_CODE, ExceptionConstant.NON_ACTIVITY_PERIOD);
        }

    }


    /**
     * @Describe 检查用户是否抽奖
     * @param user
     * @return
     */
    private Result validUserPrizeForRedis(User user){
        Object awardRecordObject = this.redisTemplate.opsForValue().get(RedisConstant.USER_AWARD_PREFIX_KEY+user.getUserName());
        if(awardRecordObject!=null){
            return ResultUtil.error(ExceptionConstant.HAS_DRAW_CODE,ExceptionConstant.HAS_DRAW,awardRecordObject);
        }else{
            return ResultUtil.success();
        }
    }
}
