package com.li.auth.service;

import com.li.auth.pojo.SysPermission;

import java.util.List;

public interface SysPermissionService {
    /**
     * 主键编码查询
     * @param code
     * @return
     */
    SysPermission getPermissionInfoByCode(String code);

    /**
     * 查询角色的权限
     * @param roleCode
     * @return
     */
    List<SysPermission> getPermissionsByRoleCode(String roleCode);

    /**
     * 查询用户的权限 Permission的path
     * @param username
     * @return
     */
    List<SysPermission> getPermissionsByUserName(String username);

}
