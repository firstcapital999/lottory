/*
package com.thinkive.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public class TimeAspect {

    @Around("execution(* com.thinkive.lottery.controller.HelloWorldController.* (..))")
    public Object handlerControllerMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("Time aspect start");
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg :args){
            System.out.println("arg is"+arg);
        }

        long start = new Date().getTime();
        Object object = proceedingJoinPoint.proceed();

        System.out.println("time aspect 耗时："+(new Date().getTime()-start));
        System.out.println("time aspect end");
        return null;
    }

}
*/
