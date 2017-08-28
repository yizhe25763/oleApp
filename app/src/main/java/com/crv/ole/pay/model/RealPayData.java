package com.crv.ole.pay.model;

import java.io.Serializable;
import java.util.List;

/**
 * 作用描述：
 * 创建者： wj_wsf
 * 创建时间： 2017/8/4 16:59.
 */

public class RealPayData implements Serializable {
    private List<String> aliasCodes;
    private List<PayMethodItemData> payments;
    private String total;

    public List<String> getAliasCodes() {
        return aliasCodes;
    }

    public void setAliasCodes(List<String> aliasCodes) {
        this.aliasCodes = aliasCodes;
    }

    public List<PayMethodItemData> getPayments() {
        return payments;
    }

    public void setPayments(List<PayMethodItemData> payments) {
        this.payments = payments;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
