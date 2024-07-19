package com.li.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.io.Serial;
import java.io.Serializable;

public class LoginUser implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "用户账户不能为空")
    @Schema(description = "用户账户",requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @NotBlank(message = "用户密码不能为空")
    @Schema(description = "用户密码",requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    public @NotBlank(message = "用户账户不能为空") String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank(message = "用户账户不能为空") String username) {
        this.username = username;
    }

    public @NotBlank(message = "用户密码不能为空") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "用户密码不能为空") String password) {
        this.password = password;
    }
}
