package com.xly.mall.common.base;

import java.io.Serializable;
import java.util.Date;

public class BaseDO implements Serializable {

    private static final long serialVersionUID = 281608583054364506L;
    protected String createUser;
    protected Date createTime;
    protected String updateUser;
    protected Date updateTime;

    public BaseDO() {
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return this.updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
