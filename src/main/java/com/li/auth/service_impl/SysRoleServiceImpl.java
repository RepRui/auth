package com.li.auth.service_impl;

import com.li.auth.mapper.SysRoleMapper;
import com.li.auth.pojo.SysRole;
import com.li.auth.service.SysRoleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Override
    public SysRole getRoleInfoByCode(String code) {
        return sysRoleMapper.getRoleInfoByCode(code);
    }

    @Override
    public List<SysRole> getRolesByUserName(String userName) {
        return sysRoleMapper.getRolesByUserName(userName);
    }
}
