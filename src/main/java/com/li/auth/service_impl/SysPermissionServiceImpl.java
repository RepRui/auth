package com.li.auth.service_impl;

import com.li.auth.mapper.SysPermissionMapper;
import com.li.auth.pojo.SysPermission;
import com.li.auth.service.SysPermissionService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysPermissionServiceImpl implements SysPermissionService {
    @Resource
    private SysPermissionMapper sysPermissionMapper;
    @Override
    public SysPermission getPermissionInfoByCode(String code) {
        return sysPermissionMapper.getPermissionInfoByCode(code);
    }

    @Override
    public List<SysPermission> getPermissionsByRoleCode(String roleCode) {
        return sysPermissionMapper.getPermissionsByRoleCode(roleCode);
    }

    @Override
    public List<SysPermission> getPermissionsByUserName(String username) {
        return sysPermissionMapper.getPermissionsByUserName(username);
    }
}
