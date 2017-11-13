package com.thinkive.lottery.constant;

/**
 * @Describe redis的常量类
 * @Author dengchangneng
 * @CreateTime 2017年10月9日12:03:42
 */
public class RedisConstant {

    public RedisConstant() {
    }

    public static final String USER_PREFIX_KEY = "user@";

    //用户中奖信息key前缀
    public static final String USER_AWARD_PREFIX_KEY = "userAward@";

    //奖品列表key前缀
    public static final String AWARD_LIST_PREFIX_KEY = "awardList@";

    //奖品数量key前缀
    public static final String AWARD_POOL_NUM_PREFIX_KEY = "awardPoolNum@";

    //活动中奖信息key前缀
    public static final String ACTIVITY_AWARD_LIST_PREFIX_KEY = "activityAwardList@";

    //活动奖品信息队列
    public static final String ACTIVITY_AWARD_QUEUE_PREFIX_KEY = "activityAwardQueue@";


}
