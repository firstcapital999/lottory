package com.thinkive.common.util;

import com.thinkive.common.constant.ExceptionConstant;
import com.thinkive.common.entity.Result;

/**
 * Created by thinkive on 2017/10/11.
 */
public class ResultUtil {


    public static Result success(Object object) {
        Result result = new Result();
        result.setCode(ExceptionConstant.SUCCESS_CODE);
        result.setMsg(ExceptionConstant.SUCCESS);
        result.setData(object);
        return result;
    }

    public static Result success() {
        return success(null);
    }

    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static Result error(Integer code, String msg,Object object) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(object);
        return result;
    }
}


