package com.crv.ole.pay.model;

import java.io.Serializable;

/**
 * 作用描述：确认订单信息中支付方式
 * 创建者： wj_wsf
 * 创建时间： 2017/8/7 09:41.
 */

public class OrderConfirmPayMehtodData implements Serializable {
    private String desc, id, name, payInterfaceId;
    private boolean isCod, isOnline;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPayInterfaceId() {
        return payInterfaceId;
    }

    public void setPayInterfaceId(String payInterfaceId) {
        this.payInterfaceId = payInterfaceId;
    }

    public boolean isCod() {
        return isCod;
    }

    public void setCod(boolean cod) {
        isCod = cod;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }
}
