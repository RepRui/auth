package com.li.auth.config;


import com.alibaba.fastjson2.JSONObject;
import com.li.auth.dto.Result;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 认证用户访问无权限处理器
 */
@Component
public class LoginAccessDefineHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        out.write(JSONObject.toJSONString(Result.getInstance(false,403,"您没有开通对应的权限，请联系管理员",null)).getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
    }
}
