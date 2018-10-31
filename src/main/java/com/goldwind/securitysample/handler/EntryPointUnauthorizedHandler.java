package com.goldwind.securitysample.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义身份认证失败的返回值
 * @author macbook37349
 * @Date: 2018/10/20 14:55
 * @Description: 自定义认证拦截器
 */
@Component
public class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint {

	@Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setStatus(401);
    }
}

