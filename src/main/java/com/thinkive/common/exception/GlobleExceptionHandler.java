/*
package com.thinkive.common.exception;

import com.thinkive.common.constant.ExceptionConstant;
import com.thinkive.common.entity.Result;
import com.thinkive.common.util.ResultUtil;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

*/
/**
 * @Description ${DESCRIPTION}
 * @Author dengchangneng
 * @Create 2018-04-03-11:02
 **//*

@ControllerAdvice
@ResponseBody
public class GlobleExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result<String>  exceptionHandler(HttpServletRequest request,Exception e){
        if(e instanceof BindException){
            BindException bindException = (BindException) e;
            List<ObjectError> list = bindException.getAllErrors();


            return ResultUtil.error(ExceptionConstant.ERROR_CODE,"参数绑定错误");
        }

    }
}
*/
