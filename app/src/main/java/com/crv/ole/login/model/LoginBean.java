package com.crv.ole.login.model;

import java.io.Serializable;

/**
 * Created by fanhaoyi on 2017/7/7.
 */

public class LoginBean implements Serializable {
    private String loginId;
    private String password;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
