package com.li.auth.pojo;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public class SysRole implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String code;//code

    private String name;//name

    private String description;//description 备注

    private int enabled;//用户状态0禁用，1启用，2废弃

    private Date createTime;//create_time

    private Date updateTime;//update_time

    private String createUser;

    private String updateUser;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
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
}
