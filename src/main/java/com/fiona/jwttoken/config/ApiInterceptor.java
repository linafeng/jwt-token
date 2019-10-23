package com.fiona.jwttoken.config;

import com.fiona.jwttoken.security.TokenService;
import com.fiona.jwttoken.util.SendMsgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fiona fung
 * 拦截器
 */
@Component
public class ApiInterceptor implements HandlerInterceptor {
    @Autowired
    TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle");
        System.out.println("参数" + request.getParameter("name"));
        System.out.println("token：" + request.getHeader("auth_token"));
        boolean isValidToken = tokenService.isValidToken(request.getHeader("auth_token"));
        if (!isValidToken) {
            SendMsgUtil.sendNoAuthMessage(response);
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        System.out.println("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion");
    }
}