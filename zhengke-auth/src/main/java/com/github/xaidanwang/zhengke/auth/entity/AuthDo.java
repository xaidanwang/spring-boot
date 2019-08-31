package com.github.xaidanwang.zhengke.auth.entity;

import lombok.Data;

import java.util.Date;
@Data
public class AuthDo {
    private Integer id;

    private String phoneid;

    private String token;

    private String updatetime;

    private String createtime;

    private String expiretime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhoneid() {
        return phoneid;
    }

    public void setPhoneid(String phoneid) {
        this.phoneid = phoneid == null ? null : phoneid.trim();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public void setExpiretime(String expiretime) {
        this.expiretime = expiretime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public String getCreatetime() {
        return createtime;
    }

    public String getExpiretime() {
        return expiretime;
    }
}