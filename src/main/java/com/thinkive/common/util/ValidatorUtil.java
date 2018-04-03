package com.thinkive.common.util;

import com.thinkive.common.constant.ExceptionConstant;
import com.thinkive.common.entity.Result;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description 校验工具类
 * @Author dengchangneng
 * @Create 2018-04-03-10:16
 **/
public class ValidatorUtil {

    /**
     * @Description 验证手机号码格式是否正确
     * @param value 手机号码
     * @return Result<String>
     */
    public static Result<String> isMobile(String value){
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
        if(value.length() != 11){
            return ResultUtil.error(ExceptionConstant.ERROR_CODE,"手机号码应为11位数");
        }else{
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(value);
            boolean isMatch = m.matches();
            if(isMatch){
                return ResultUtil.success();
            } else {
                return ResultUtil.error(ExceptionConstant.ERROR_CODE,"您的手机号码格式错误");
            }
        }
    }

}
