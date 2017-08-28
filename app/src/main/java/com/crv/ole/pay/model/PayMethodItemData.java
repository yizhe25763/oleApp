package com.crv.ole.pay.model;

import java.io.Serializable;

/**
 * 作用描述：支付方式实体类
 * 创建者： wj_wsf
 * 创建时间： 2017/8/4 17:00.
 */

public class PayMethodItemData implements Serializable {
    private String isMobile, logoUrl, payInterfaceId, paymentName;

    public String getIsMobile() {
        return isMobile;
    }

    public void setIsMobile(String isMobile) {
        this.isMobile = isMobile;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getPayInterfaceId() {
        return payInterfaceId;
    }

    public void setPayInterfaceId(String payInterfaceId) {
        this.payInterfaceId = payInterfaceId;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }
}
