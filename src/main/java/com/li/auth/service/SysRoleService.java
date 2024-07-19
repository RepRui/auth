package com.li.auth.service;

import com.li.auth.pojo.SysRole;

import java.util.List;

public interface SysRoleService {
    /**
     * 主键编码查询
     * @param code
     * @return
     */
    SysRole getRoleInfoByCode(String code);

    /**
     * 查询用户的角色
     * @param userName
     * @return
     */
    List<SysRole> getRolesByUserName(String userName);
}
