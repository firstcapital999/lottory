/*
package com.thinkive.lottery.service.impl;

import com.thinkive.lottery.constant.RedisConstant;
import com.thinkive.lottery.service.ILotteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class LotteryServiceImpl implements ILotteryService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map<String, Object> lottery(RedisTemplate redisTemplate, String activityId) {
        */
/* 奖品概率集 *//*

        List<Double> prob = new ArrayList<Double>();

        */
/* 奖品结果集 *//*

        List<Map<String,Object>> selectAwardList = new ArrayList<Map<String,Object>>();

        */
/* 根据活动信息,获取到缓存中所有奖品信息 *//*

        try
        {
            */
/* 中奖率累计 *//*

            double probabilityTotal = 0.0;
            */
/* 获取活动对应奖品集 *//*

            try
            {
                // 获取所有活动奖品键
                String awardListKey = RedisConstant.prizeCacheKey + "_" + activityId;
                redisTemplate.getHashKeySerializer();
                List<DataRow> awardList = redisClient.hgetListObj(awardListKey, Map.class);
                if ( awardList.size() == 0 )
                {
                    logger.info("没有获取到活动对应奖品信息. 活动唯一键[" + activityId + "].");
                    return null;
                }

                for (DataRow awrad : awardList)
                {
                    String awardId = awrad.getString("id");

                    */
/* 过滤奖品数量,存量如果<=0 不再参与到抽奖环节 *//*

                    String awardPoolKey = RedisQueueConstants.prizePoolCacheKey + "_" + activityId;

                    //验证数量
                    String amount = redisClient.getStringFromHget(awardPoolKey, awardId);
                    if ( amount == null || Integer.parseInt(amount) <= 0 )
                    {
                        continue;
                    }

                    */
/* 过滤活动周期限制,周期内抽奖次数达到上限的奖品类别,不参与抽奖 *//*

                    String cycle = awrad.getString("win_unit"); // 中奖频率
                    // 0
                    // 月、1
                    // 天数
                    // 、2
                    // 周
                    String limitWinNum = awrad.getString("win_total"); // 限定中奖名额
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

                                        */
/* 抽奖频率 次/天 *//*

                                        String date = DateFormatUtils.format(calendar, "yyyyMMdd");
                                        String day = RedisQueueConstants.activityDayTotelOfParticipationCacheKey + "_"
                                                + activityId + "_" + awrad.getString("id") + "_" + date;
                                        String dn = redisClient.getString(day);
                                        if ( dn != null && dn.matches("\\d+") )
                                        {
                                            if ( Integer.parseInt(dn) >= limit )
                                            {
                                                continue;
                                            }
                                        }
                                        break;
                                    case 2:

                                        */
/* 抽奖频率 次/周 *//*

                                        int week = calendar.get(Calendar.WEEK_OF_YEAR);
                                        String cacheKeyW = RedisQueueConstants.activityWeekTotelOfParticipationCacheKey
                                                + "_" + activityId + "_" + awrad.getString("id") + "_" + week;
                                        String wn = redisClient.getString(cacheKeyW);
                                        if ( wn != null && wn.matches("\\d+") )
                                        {
                                            if ( Integer.parseInt(wn) >= limit )
                                            {
                                                continue;
                                            }
                                        }
                                        break;
                                    case 0:

                                        */
/* 抽奖频率 次/月 *//*

                                        int month = calendar.get(Calendar.MONTH) + 1;
                                        String cacheKeyM = RedisQueueConstants.activityMonthTotelOfParticipationCacheKey
                                                + "_" + activityId + "_" + awrad.getString("id") + "_" + month;
                                        String mn = redisClient.getString(cacheKeyM);
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
                    double probability = awrad.getDouble("probability");

                    */
/* 获取该奖品的中奖率,累加中奖率 *//*

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
                DataRow pullPrize = new DataRow();
                pullPrize.set("id", "20"); //补充活动ID
                pullPrize.set("money", 0); // 设置为0元
                pullPrize.set("amount", 0); // 设置数量0
                pullPrize.set("name", "开心奖");
                pullPrize.set("introduce", "3688钻石会员资讯包");
                pullPrize.set("probability", (100 - probabilityTotal));
                pullPrize.set("pic_url", "../wx/images/redIcon/VIP.png");
                pullPrize.set("is_hb", 0); // 无价值奖品
                pullPrize.set("is_default", 1);
                pullPrize.set("orderline", "0"); //默认奖品
                pullPrize.set("rank", 0); //排名
                pullPrize.set("type", "0");//奖品类型(1为大将，0为小奖
                selectAwardList.add(pullPrize);
            }

            if ( selectAwardList.size() == 0 )
            {
                logger.info("获取到奖品集合,但所有奖品均超过周期内抽奖次数! 活动唯一键[" + activityId + "].");
                return null;
            }

            try
            {
                */
/* 遍历所有奖品,取得奖品概率生成随机算法计算规则 *//*

                for (DataRow ps : selectAwardList)
                {
                    double rate = ps.getDouble("probability");
                    double tmp = rate / 100;
                    prob.add(tmp);
                }

            }
            catch (Exception e)
            {
                throw new IllegalAccessException("初始化抽奖算法规则异常! 活动唯一ID:" + activityId);
            }

            */
/* 实例化抽奖算法,并抽奖 *//*

            AliasMethod aliasMethod = new AliasMethod(prob);

            */
/* 开始抽奖 *//*

            int index = aliasMethod.next();

            // 返回抽中奖品
            DataRow selectAward = selectAwardList.get(index);

            //            logger.error("抽奖列表1："+JSON.toJSONString(selectAwardList));
            //            logger.error("抽奖列表2："+JSON.toJSONString(prob));
            //            logger.error("抽奖列表3："+JSON.toJSONString(selectAward));
            return selectAward;
        }
        catch (Exception e)
        {
            logger.error("活动抽奖业务服务接口出现异常!", e);
            throw new InvokeException("活动抽奖业务服务接口出现异常! 异常信息 :[" + e.getMessage() + "]", 3);
        }
    }
}
*/
