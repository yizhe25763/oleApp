package com.crv.ole.register.model;

/**
 * Created by Fairy on 2017/7/19.
 * 验证短信验证码相关
 */

public class CheckPhoneCodeBean {
    private String phoneValidatingCode;
    private String phoneNumber;

    public String getPhoneValidatingCode() {
        return phoneValidatingCode;
    }

    public void setPhoneValidatingCode(String phoneValidatingCode) {
        this.phoneValidatingCode = phoneValidatingCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
