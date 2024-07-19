package com.li.auth.config;

import com.alibaba.fastjson2.JSONObject;
import com.li.auth.dto.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
/**
 * 匿名用户访问资源处理器
 *
 401 Unauthorized：这个错误通常意味着请求需要用户验证（如用户名和密码）。如果服务器配置为要求HTTP身份验证，而请求中未提供正确的凭据，就会返回401错误。解决这个问题的方法包括输入正确的用户名和密码，或者如果适用，关闭服务器的密码验证功能。
 403 Forbidden：这个错误表明服务器理解了请求，但是拒绝执行它。这可能是因为服务器配置规则拒绝了请求，通常是由于服务器或服务权限配置不当。解决这个问题的方法可能包括确保请求的资源存在、服务器运行用户具有适当的权限、以及检查服务器配置，确保权限设置正确。
 */
@Component
public class LoginAuthenticationHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        out.write(JSONObject.toJSONString(Result.getInstance(false,401,"匿名用户没有权限进行访问",null)).getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
    }
}
