package com.crv.ole.personalcenter.model;

import java.io.Serializable;

/**
 * 订单支付状态信息  p200：待支付 p201: 已支付
 * Created by zhangbo on 2017/8/16.
 */

public class PayStateInfo implements Serializable {

    private String name;//支付状态描述
    private String state;//支付状态编码

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
