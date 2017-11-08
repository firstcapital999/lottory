package com.thinkive.common.authority.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Descibe 权限验证控制器
 * @Author dengchangneng
 * @CreateTime 2017年10月9日09:30:36
 */
@RestController
public class AuthorityController {

    //获取请求缓存
    private RequestCache requestCache = new HttpSessionRequestCache();

    //重定向策略类
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /**
     * 验证方法：还没使用，针对不同的设备请求，返回不同的数据
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/authentication/require")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public String requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            System.out.println("引发跳转的请求是：" + targetUrl);
            if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
                redirectStrategy.sendRedirect(request, response, "/login.html");
            } else {

            }
        }
        return null;
    }
}
