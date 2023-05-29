package com.swu.audit.org.config;

import com.swu.audit.common.helper.JwtHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@Component
public class GlobalInterceptor implements HandlerInterceptor {

    //拦截系统中的非登录请求，通过token判断是否登录成功
    @Override
    @CrossOrigin(allowCredentials="true")
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("authorization");
        if (token == null || token.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is missing");
            return false;
        }
        String check = JwtHelper.isValdate(token);
        if(!check.equals("pass")){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is wrong");
            return false;
        }
        request.setAttribute("token", token);
        return true;
    }



}
