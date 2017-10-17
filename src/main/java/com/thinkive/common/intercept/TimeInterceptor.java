package com.thinkive.common.intercept;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/*@Component*/
public class TimeInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        System.out.println("preHandler");
       /* System.out.println(((HandlerMethod)handler).getBean().getClass().getName());
        System.out.println(((HandlerMethod)handler).getMethod().getName());*/
        httpServletRequest.setAttribute("startTime",new Date().getTime());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandler");
        Long start = (Long) httpServletRequest.getAttribute("startTime");
        System.out.println("time interceptor 耗时："+(new Date().getTime()-start));
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) throws Exception {
        System.out.println("afterHandler");
        Long start = (Long) httpServletRequest.getAttribute("startTime");
        System.out.println("time interceptor 耗时："+(new Date().getTime()-start));
/*        System.out.println(e.getMessage());*/
    }
}
