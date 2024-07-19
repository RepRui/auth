package com.li.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.io.Serial;
import java.io.Serializable;

public class RequestUsername implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "id不能为空")
    @Schema(description = "主键ID",requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;


    public @NotBlank(message = "id不能为空") String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank(message = "id不能为空") String username) {
        this.username = username;
    }
}
