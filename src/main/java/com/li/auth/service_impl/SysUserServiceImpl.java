package com.li.auth.service_impl;

import com.li.auth.mapper.SysUserMapper;
import com.li.auth.pojo.SysUser;
import com.li.auth.service.SysUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser getUserInfoByUsername(String username) {
        return sysUserMapper.getUserInfoByUsername(username);
    }

    @Override
    public int insertSysUser(SysUser sysUser) {
        return sysUserMapper.insertSysUser(sysUser);
    }
}
