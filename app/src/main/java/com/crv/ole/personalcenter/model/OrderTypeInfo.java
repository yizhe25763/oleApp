package com.crv.ole.personalcenter.model;

import java.io.Serializable;

/**
 * 订单类型
 * Created by zhangbo on 2017/8/16.
 */

public class OrderTypeInfo implements Serializable{
    private String name;//类型描述
    private String orderType;//订单类型

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
