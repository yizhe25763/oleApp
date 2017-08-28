package com.crv.ole.pay.model;

import java.io.Serializable;
import java.util.List;

/**
 * 作用描述：确认订单的信息
 * 创建者： wj_wsf
 * 创建时间： 2017/8/7 09:50.
 */

public class OrderConfirmInfo implements Serializable {
    private String buyerMobile, integralBalance, saveCertificate;
    private float integralMoneyRatio;
    private OrderConfirmAddressData deliveryAddress;
    private List<OrderConfirmOCSData> ocs;

    public String getBuyerMobile() {
        return buyerMobile;
    }

    public void setBuyerMobile(String buyerMobile) {
        this.buyerMobile = buyerMobile;
    }

    public String getIntegralBalance() {
        return integralBalance;
    }

    public void setIntegralBalance(String integralBalance) {
        this.integralBalance = integralBalance;
    }

    public float getIntegralMoneyRatio() {
        return integralMoneyRatio;
    }

    public void setIntegralMoneyRatio(float integralMoneyRatio) {
        this.integralMoneyRatio = integralMoneyRatio;
    }

    public String getSaveCertificate() {
        return saveCertificate;
    }

    public void setSaveCertificate(String saveCertificate) {
        this.saveCertificate = saveCertificate;
    }

    public OrderConfirmAddressData getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(OrderConfirmAddressData deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public List<OrderConfirmOCSData> getOcs() {
        return ocs;
    }

    public void setOcs(List<OrderConfirmOCSData> ocs) {
        this.ocs = ocs;
    }
}
