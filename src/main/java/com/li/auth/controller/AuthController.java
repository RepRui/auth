package com.li.auth.controller;

import com.alibaba.fastjson2.JSONObject;
import com.li.auth.dto.LoginUser;
import com.li.auth.dto.Result;
import com.li.auth.pojo.SysUser;
import com.li.auth.utils.RedisUtil;
import com.li.auth.utils.TokenUtil;
import com.li.auth.utils.ToolsUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "登录注销")
@RestController
public class AuthController {

    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private RedisUtil redisUtil;

    @Operation(summary = "用户登录")
    @PostMapping(value = {"/login"},consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")//consumes参数设置了接受的Content-Type，produces参数设置了返回的Content-Type。
    public Result login(HttpServletRequest request,@RequestBody @Valid LoginUser loginUser){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
        String token = TokenUtil.genAccessToken(loginUser.getUsername());
        redisUtil.put(request.getRemoteAddr(),token);
       /*SysUser user = (SysUser) authentication.getPrincipal();
        if(redisUtil.get(request.getRemoteAddr())!=null){
            SysUser u = JSONObject.parseObject(redisUtil.get(request.getRemoteAddr()),SysUser.class);
            if(u.getUsername().equals(loginUser.getUsername())){
                return Result.getInstance(false,200,"已经在其处登录",token);
            }else {
                return Result.getInstance(false,200,"有其他用户登录请先注销该用户",token);
            }
        }
        redisUtil.put(request.getRemoteAddr(), JSONObject.toJSONString(user));
        redisUtil.put(user.getUsername(),token );*/
        return Result.getInstance(true,200,"登录成功",token);
    }

    @Operation(summary = "用户登录")
    @PostMapping(value = {"/logout"})
    public Result logout(HttpServletRequest request){
        //String token = ToolsUtil.getLoginToken(request);
        if(redisUtil.delete(request.getRemoteAddr())){
            return Result.getInstance(true,200,"注销成功",null);
        }else {
            return Result.getInstance(false,200,"注销失败",null);
        }

    }
}
