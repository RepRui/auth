package com.li.auth.controller;

import com.li.auth.dto.RequestUsername;
import com.li.auth.dto.Result;
import com.li.auth.pojo.SysUser;
import com.li.auth.service.SysUserService;
import com.li.auth.utils.TokenUtil;
import com.li.auth.utils.ToolsUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户中心",description = "用户管理")
@RestController
@RequestMapping("/user")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    @PreAuthorize("hasAuthority('auth:user:add')")
    @Operation(summary = "用户信息注册")
    @PostMapping(value = "/add",consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")//consumes参数设置了接受的Content-Type，produces参数设置了返回的Content-Type。
    public Result add(HttpServletRequest request,@RequestBody @Valid SysUser sysUser){// @Valid 参数校验 参数有问题会直接去GlobalExceptionHandler配置
        Result result = new Result();

        SysUser had = sysUserService.getUserInfoByUsername(sysUser.getUsername());
        if(had != null){
            result.setSuccess(false);
            result.setMsg("用户已存在");
            return result;
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        sysUser.setPassword(bCryptPasswordEncoder.encode(sysUser.getPassword()));//密码加密
        String token = ToolsUtil.getLoginToken(request);
        String username = TokenUtil.getUserFromToken(token);
        sysUser.setCreateUser(username);
        int i = sysUserService.insertSysUser(sysUser);
        if(i>0){
            result.setSuccess(true);
            result.setMsg("添加成功");
        }else {
            result.setSuccess(false);
            result.setMsg("添加失败");
        }
        result.setCode(200);
        return result;
    }
    @PreAuthorize("hasAuthority('auth:user:update')")
    @Operation(summary = "用户信息修改")
    @PostMapping(value = "/update",consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")//consumes参数设置了接受的Content-Type，produces参数设置了返回的Content-Type。
    public Result update(@RequestBody @Valid SysUser sysUser){

        return new Result();
    }
    @PreAuthorize("hasAuthority('auth:user:find')")
    @Operation(summary = "用户信息查询")
    @PostMapping(value = "/find",consumes = "application/json;charset=UTF-8",produces = "application/json;charset=UTF-8")
    public Result find(@RequestBody @Valid RequestUsername requestUsername){
        Result result = new Result();
        SysUser sysUser = sysUserService.getUserInfoByUsername(requestUsername.getUsername());
        result.setSuccess(true);
        result.setMsg("查询成功");
        result.setData(sysUser);
        result.setCode(200);
        return result;
    }
}
