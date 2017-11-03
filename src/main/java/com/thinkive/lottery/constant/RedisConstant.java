package com.thinkive.lottery.constant;

public class RedisConstant {

    public RedisConstant() {
    }

    public static final String USER_PREFIX_KEY="user@";

    //用户中奖信息key前缀
    public static final String USER_AWARD_PREFIX_KEY="userAward@";

    //奖品列表key前缀
    public static final String AWARD_LIST_PREFIX_KEY = "awardList@";

    //奖品数量key前缀
    public static final String AWARD_POOL_NUM_PREFIX_KEY = "awardPoolNum@";

    public static final String WX_STATE_LIST = "wx_state_list";
    public static String WX_LOCATION_LIST = "wx_location_list";
    public static String WX_CREATE_QRCODE_EXTENSION = "wx_create_qrcode_extension";
    public static final String activityDayTotelOfParticipationCacheKey = "wx_tpd@";
    public static final String activityWeekTotelOfParticipationCacheKey = "wx_tpw@";
    public static final String activityMonthTotelOfParticipationCacheKey = "wx_tpm@";
    public static final String activityMoneyTotelCacheKey = "wx_mtc@";
    public static final String activityAccessStatCacheKey = "wx_asc@";
    public static final String activityAccessCycleCacheKey = "wx_acc@";
    public static final String merchantCacheKey = "amp_mchc@";
    public static final String activityCacheKey = "wx_c@";
    public static final String activityOfPublicCacheKey = "wx_aopc@";
    public static final String activityStatusCacheKey = "wx_sc@";
    public static final String prizeCacheKey = "wx_pc@";
    public static final String participantCacheKey = "wx_ppc@";
    public static final String prizePoolCacheKey = "wx_ppoc@";
    public static final String prizeDrawnCahceKey = "wx_pdc@";
    public static final String luckyPlayersCacheKey = "wx_lpc@";
    public static final String participantLotteryFlowRecord = "wx_plfrc@";
    public static final String beGrantVIPMemberCardRecord = "wx_bgvc@";
    public static final String luckyPlayersAndPrizesInfoCacheKey = "wx_lpapic@";
    public static final String participantAndResultsCacheKey = "wx_parc@";
    public static final String customerSyncLocked = "wx_customer_sync";
    public static String WX_SYSTEM_QUEUE = "wx_system_queue";
    public static String WX_TEMPLATE_MSG_QUEUE = "wx_template_msg_queue";
    public static String UPDATE_WX_INFO_QUEUE = "update_wx_info_queue";
    public static String WX_COMMON_TEMP_QUEUE = "wx_common_temp_queue";
    public static String WX_MSG_PUSH = "wx_msg_push";
    public static String WX_MSG_DELETE = "wx_msg_delete";
}
