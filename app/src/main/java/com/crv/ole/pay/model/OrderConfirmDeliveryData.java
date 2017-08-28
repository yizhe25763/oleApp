package com.crv.ole.pay.model;

import java.io.Serializable;

/**
 * 作用描述：可用的配送规则列表
 * 创建者： wj_wsf
 * 创建时间： 2017/8/7 09:37.
 */

public class OrderConfirmDeliveryData implements Serializable {
    private String description, id, name, totalPrice;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
