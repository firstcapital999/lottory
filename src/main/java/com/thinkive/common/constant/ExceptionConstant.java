package com.thinkive.common.constant;

/**
 * @Describe 错误类型常量类
 * @auther dengchangneng
 * @createTime 2017年11月1日14:11:06
 */
public class ExceptionConstant {

    public ExceptionConstant() {
    }

    //错误代码
    public static final Integer ERROR_CODE=1;

    public static final String ERROR="错误";

    //成功代码
    public static final Integer SUCCESS_CODE=0;

    public static final String SUCCESS="成功";

    //非活动期间内注册错误 码
    public static final Integer NON_ACTIVITY_PERIOD_CODE=3;

    public static final String NON_ACTIVITY_PERIOD="非活动期间注册";

    //表单验证不合法错误码
    public static final Integer CHECK_ERROR_CODE=4;

    public static final String CHECK_ERROR="表单验证不合法";

    //已抽奖错误码
    public static final Integer HAS_DRAW_CODE=5;

    public static final String HAS_DRAW="已抽奖";

    public static final Integer BEGINDATE_PARSE_ERROR_CODE=6;

    public static final String BEGINDATE_PARSE_ERROR = "活动开始日期转化错误";

    public static final Integer ENDDATE_PARSE_ERROR_CODE = 7;

    public static final String ENDDATE_PARSE_ERROR = "活动结束日期转化错误";

    public static final Integer QUERY_NO_DATA_CODE = 8;

    public static final  String QUERY_NO_DATA = "查询不到数据";


    public static final Integer QUERY_NO_ACTIVITY_AWARD_LIST_CODE = 9;

    public static final String QUERY_NO_ACTIVITY_AWARD_LIST = "没有获取到活动对应奖品信息";

    public static final Integer INIT_ERROR_PROBABILITY_CODE=10;

    public static final String INIT_ERROR_PROBABILITY = "初始化抽奖概率失败";

    public static final Integer USER_NOT_LOGIN_CODE = 11;

    public static final String USER_NOT_LOGIN = "用户没有登录";

    public static final Integer NO_PRIZE_CODE= 12;

    public static final String NO_PRIZE = "没有奖品可抽";

    public static final Integer USER_EXIST_CODE=13;

    public static final String USER_EXIST = "用户名已存在";



}
