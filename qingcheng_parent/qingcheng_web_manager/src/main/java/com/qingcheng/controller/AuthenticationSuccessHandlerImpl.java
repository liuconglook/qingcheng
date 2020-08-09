package com.qingcheng.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qingcheng.pojo.system.LoginLog;
import com.qingcheng.service.system.LoginLogService;
import com.qingcheng.util.WebUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Reference
    private LoginLogService loginLogService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("登录成功，记录一下");
        String loginName = authentication.getName();
        String ip = request.getRemoteAddr();
        LoginLog loginLog = new LoginLog();
        loginLog.setLoginName(loginName);
        loginLog.setLoginTime(new Date());
        loginLog.setIp(ip);
        loginLog.setLocation(WebUtil.getCityByIP(ip)); // 地区
        String agent = request.getHeader("user-agent");
        loginLog.setBrowserName(WebUtil.getBrowserName(agent)); // 浏览器名称
        loginLogService.add(loginLog);
        request.getRequestDispatcher("/main.html").forward(request,response);
    }
}
