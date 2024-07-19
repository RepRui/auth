package com.li.auth.mapper;


import com.li.auth.pojo.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface SysUserMapper  {

    /**
     * 根据用户名账号获取用户信息
     *
     * @param username 用户信息
     * @return 用户信息
     */
    SysUser getUserInfoByUsername(@Param("username") String username);

    /**
     * 新增用户信息
     * @param sysUser
     * @return
     */
    public int insertSysUser(SysUser sysUser);
}
