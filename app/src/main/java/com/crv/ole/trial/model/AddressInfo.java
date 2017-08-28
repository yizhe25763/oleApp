package com.crv.ole.trial.model;

import java.io.Serializable;

/**
 * 地址信息
 * Created by zhangbo on 2017/8/20.
 */

public class AddressInfo implements Serializable {

    private String address;//
    private String userName;//
    private String phone;//

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
