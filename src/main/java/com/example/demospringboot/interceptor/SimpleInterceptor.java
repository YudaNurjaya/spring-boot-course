package com.example.demospringboot.interceptor;

import com.example.demospringboot.utils.validation.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class SimpleInterceptor implements HandlerInterceptor {
    @Autowired
    JwtUtil jwtUtil;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getRequestURI().contains("login") || request.getRequestURI().contains("registration")){
            return true;
        }
        String token = request.getHeader("Authorization");
        if(token==null)throw new RuntimeException("Token not found");
        String jwtToken = token.substring(7);
        return jwtUtil.validateToken(jwtToken);
//        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("POST HANDLE");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("COMPLETE");
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
