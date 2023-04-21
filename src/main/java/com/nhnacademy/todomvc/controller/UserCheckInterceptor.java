package com.nhnacademy.todomvc.controller;

import com.nhnacademy.todomvc.exception.AccessDeniedException;
import com.nhnacademy.todomvc.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Slf4j
public class UserCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(Objects.isNull(request.getHeader("x-user-id"))){
            throw new UnauthorizedException();
        }
        if(!request.getHeader("x-user-id").equals("jeong")){
            throw new AccessDeniedException();
        }
        return true;
    }
}

