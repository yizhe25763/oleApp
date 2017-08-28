package com.crv.ole.personalcenter.model;

import java.io.Serializable;

/**
 * Created by yanghongjun on 17/7/18.
 */

public class BindMessage  implements Serializable{

    String phoneNumber;
    String phoneValidatingCode;//手机验证码
    String email;
    String validateCode;//邮箱验证码

    public String getValidateCode() {
        return validateCode;
    }
    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public String getPhoneValidatingCode() {
        return phoneValidatingCode;
    }

    public void setPhoneValidatingCode(String phoneValidatingCode) {
        this.phoneValidatingCode = phoneValidatingCode;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
