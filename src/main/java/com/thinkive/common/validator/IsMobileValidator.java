package com.thinkive.common.validator;


import com.thinkive.common.constant.ExceptionConstant;
import com.thinkive.common.entity.Result;
import com.thinkive.common.util.ValidatorUtil;
import org.springframework.util.StringUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Description 手机号码验证实现类
 * @Author dengchangneng
 * @Create 2018-04-03-10:06
 **/
public class IsMobileValidator implements ConstraintValidator<IsMobile,String>{

    private boolean required = false;

    @Override
    public void initialize(IsMobile isMobile) {
        required = isMobile.required();

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(required){
            Result<String> result = ValidatorUtil.isMobile(s);
            if(ExceptionConstant.ERROR_CODE.equals(result.getCode())){
                return false;
            }else{
                return true;
            }
        }else{
            if(StringUtils.isEmpty(s)){
                return true;
            }else{
                Result<String> result = ValidatorUtil.isMobile(s);
                if(ExceptionConstant.ERROR_CODE.equals(result.getCode())){
                    return false;
                }else{
                    return true;
                }
            }

        }

    }
}
