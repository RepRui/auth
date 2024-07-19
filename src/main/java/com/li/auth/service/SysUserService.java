package com.li.auth.service;

import com.li.auth.pojo.SysUser;


public interface SysUserService {

    /**
     * 根据用户id获取用户信息（包含用户具备的权限信息）
     *
     * @param username 用户信息
     * @return
     */
    public SysUser getUserInfoByUsername(String username);

    /**
     * 新增用户信息
     * @param sysUser
     * @return
     */
    public int insertSysUser(SysUser sysUser);
}