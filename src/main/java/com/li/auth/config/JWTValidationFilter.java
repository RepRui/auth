package com.li.auth.config;

import com.alibaba.fastjson2.JSONObject;
import com.li.auth.basic.AccessInfo;
import com.li.auth.pojo.SysUser;
import com.li.auth.utils.RedisUtil;
import com.li.auth.utils.TokenUtil;
import com.li.auth.utils.ToolsUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class JWTValidationFilter extends OncePerRequestFilter {

    @Resource
    private RedisUtil redisUtil;
   /* @Resource
    private CustomerUserDetailsService customerUserDetailsService;*/
    @Resource
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;
    /*如果你想要保持父类约定，可以这样标记@NonNull*/
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (AccessInfo.isMatch(request.getRequestURI())){
            // 是白名单请求放行
            filterChain.doFilter(request, response);
        }else {
            /*String token = ToolsUtil.getLoginToken(request);
            String session = redisUtil.get(request.getRemoteAddr());
            if(session == null || token == null ){
                resolver.resolveException(request,response,null,new InsufficientAuthenticationException("TOKEN无效"));
                return;
            }
            if(!session.equals(token)){
                resolver.resolveException(request,response,null,new InsufficientAuthenticationException("TOKEN无效"));
                return;
            }*/
            String token = ToolsUtil.getLoginToken(request);
            if( token == null || redisUtil.get(token) == null ){
                resolver.resolveException(request,response,null,new InsufficientAuthenticationException("TOKEN无效"));
                return;
            }
            //解析token
            String username = TokenUtil.getUserFromToken(token);
            if(StringUtils.isEmpty(username)){
                resolver.resolveException(request,response,null,new InsufficientAuthenticationException("TOKEN无效"));
                return;
            }
            if(TokenUtil.getExpirationTime(token)==0){
                resolver.resolveException(request,response,null,new InsufficientAuthenticationException("TOKEN过期"));
                return;
            }
            //获取用户信息
            //UserDetails user = customerUserDetailsService.loadUserByUsername(username);
            String u = redisUtil.get(token);
            if(u == null){
                resolver.resolveException(request,response,null,new InsufficientAuthenticationException("TOKEN无效"));
                return;
            }
            try{
                UserDetails user =  JSONObject.parseObject(u, SysUser.class);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //设置到spring security上下文
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                redisUtil.expire(token);
                filterChain.doFilter(request, response);
            }catch (Exception ex){
                resolver.resolveException(request,response,null,ex);
            }

        }
    }
}
