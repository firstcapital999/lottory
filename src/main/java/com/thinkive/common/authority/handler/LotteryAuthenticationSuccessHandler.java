package com.thinkive.common.authority.handler;


import com.thinkive.common.authority.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Describe 登录成功后的处理类
 * @Author dengchangneng
 * @CreateTime 2017年10月8日10:44:53
 */
@Component(value = "lotteryAuthenticationSuccessHandler")
public class LotteryAuthenticationSuccessHandler extends
        SavedRequestAwareAuthenticationSuccessHandler {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private RequestCache requestCache = new HttpSessionRequestCache();


    /**
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     * @Describe 登录成功后执行方法
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication) throws IOException,
            ServletException {
        //获得授权后可得到用户信息   可使用SUserService进行数据库操作
        User userDetails = (User) authentication.getPrincipal();
       /* Set<SysRole> roles = userDetails.getSysRoles();*/
        //输出登录提示信息
        System.out.println("管理员 " + userDetails.getUserName() + " 登录");

        System.out.println("IP :" + getIpAddress(request));

        SavedRequest savedRequest = requestCache.getRequest(request, response);


        redirectStrategy.sendRedirect(request, response, "/home");


/*        super.onAuthenticationSuccess(request, response, authentication);*/
    }

    /**
     * @param request
     * @return
     * @Describe 获取IP地址
     */
    public String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
