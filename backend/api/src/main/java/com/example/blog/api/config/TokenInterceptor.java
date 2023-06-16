package com.example.blog.api.config;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.example.blog.base.exception.CodeAndMsg;
import com.example.blog.base.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@CrossOrigin
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        String curToken = request.getHeader("token");
        try {
            StpUtil.checkActivityTimeout();
        } catch (NotLoginException e) {
            log.info("token expired");
            e.printStackTrace();
            R r = R.error(CodeAndMsg.NEED_LOGIN.getCode(), CodeAndMsg.NEED_LOGIN.getMsg());
            response.getWriter().append(JSON.toJSONString(r));
            return false;
        }
        StpUtil.getTokenInfo().getTokenValue();
        if (StpUtil.isLogin() && curToken.equals(StpUtil.getTokenInfo().getTokenValue())) {
            return true;
        }
        try {
            R r = R.error(CodeAndMsg.NEED_LOGIN.getCode(), CodeAndMsg.NEED_LOGIN.getMsg());
            response.getWriter().append(JSON.toJSONString(r));
            log.info("Login failed");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500);
            return false;
        }
        return false;
    }
}
