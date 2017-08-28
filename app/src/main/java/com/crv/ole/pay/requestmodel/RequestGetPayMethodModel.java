package com.crv.ole.pay.requestmodel;

import java.util.List;

/**
 * 作用描述：获取支付方式需要的参数
 * 创建者： wj_wsf
 * 创建时间： 2017/8/3 16:01.
 */

public class RequestGetPayMethodModel {
    private String orderIds;

    public RequestGetPayMethodModel(String orderId) {
        this.orderIds = orderId;
    }

    public String getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(String orderIds) {
        this.orderIds = orderIds;
    }
}
