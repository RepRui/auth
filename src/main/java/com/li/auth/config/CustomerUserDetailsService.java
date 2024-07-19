package com.li.auth.config;

import com.li.auth.pojo.SysPermission;
import com.li.auth.pojo.SysUser;
import com.li.auth.service.SysPermissionService;
import com.li.auth.service.SysUserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * 自定义UserDetailsService 用于认证和授权
 * 此处把用户的信息和权限交给spring security
 * spring security会对用户的信息和权限信息进行管理
 * @author hffan
 * serDetailService接口主要定义了一个方法 l
 * oadUserByUsername(String username)用于完成用户信息的查询，
 * 其中username就是登录时的登录名称，登录认证时，需要自定义一个实现类实现UserDetailService接口，
 * 完成数据库查询，该接口返回UserDetail。
 */

@Component
public class CustomerUserDetailsService implements UserDetailsService {
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysPermissionService sysPermissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = sysUserService.getUserInfoByUsername(username);
        // 如果用户不存在
        if (user == null){
            throw new UsernameNotFoundException("用户名不存在！");
        }
        List<SysPermission> permissions = sysPermissionService.getPermissionsByUserName(username);
        // 取出权限中配置code
        List<String> collect = permissions.stream().filter(Objects::nonNull)
                .map(SysPermission::getAuthorize)
                .filter(Objects::nonNull)
                .toList();
        // 转为数据
        String[] strings = collect.toArray(new String[0]);
        List<GrantedAuthority> authorizes = AuthorityUtils.createAuthorityList(strings);
        // 配置权限
        user.setAuthorities(authorizes);
        return user;
    }
}
