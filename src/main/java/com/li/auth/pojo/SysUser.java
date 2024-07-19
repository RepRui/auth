package com.li.auth.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

public class SysUser implements Serializable, UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;
    @TableId
    @NotBlank(message = "用户账户不能为空")
    @Schema(description = "用户账户",requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;//username
    @NotBlank(message = "用户密码不能为空")
    @Schema(description = "用户密码",requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;//password
    @NotBlank(message = "用户昵称不能为空")
    @Schema(description = "用户昵称",requiredMode = Schema.RequiredMode.REQUIRED)
    private String nickname;//nickname
    @Email(message = "不是有效的邮箱")
    @Schema(description = "用户邮箱",requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String mail;//nickname
    @Schema(hidden = true)
    private boolean enabled;//enabled 用户状态0禁用，1启用，2废弃
    @Schema(hidden = true)
    private Date createTime;//create_time
    @Schema(hidden = true)
    private Date updateTime;//update_time
    @Schema(hidden = true)
    private String createUser;
    @Schema(hidden = true)
    private String updateUser;

    @Schema(hidden = true)
    @TableField(exist = false)
    // 帐户未过期
    private  boolean accountNonExpired ;
    @Schema(hidden = true)
    @TableField(exist = false)
    // 帐户未锁定
    private  boolean accountNonLocked;
    @Schema(hidden = true)
    @TableField(exist = false)
    // 凭证是否过期
    private  boolean credentialsNonExpired;



    /**
     * 认证信息 就是用户配置code
     */
    @Schema(hidden = true)
    @TableField(exist = false)
    Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }



    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }


    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }
}
