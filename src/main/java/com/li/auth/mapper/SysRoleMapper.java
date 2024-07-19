package com.li.auth.mapper;

import com.li.auth.pojo.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper {
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
