package com.crv.ole.personalcenter.model;

import java.io.Serializable;

/**
 * 订单支付记录
 * Created by zhangbo on 2017/8/16.
 */

public class PayRecs implements Serializable {

    private String paymentName;//	支付方式名 微信支付
    private String stateName;//支付状态 已支付

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}
