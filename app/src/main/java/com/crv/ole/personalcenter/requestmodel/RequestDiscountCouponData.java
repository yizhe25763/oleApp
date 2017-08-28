package com.crv.ole.personalcenter.requestmodel;

import java.io.Serializable;

/**
 * 作用描述：请求优惠券列表参数实体类
 * 创建者： wj_wsf
 * 创建时间： 2017/7/21
 */

public class RequestDiscountCouponData implements Serializable {
    private String voucherType;//used:使用过的券，expired:过期的券，unuse:未使用的券
    private int start, limit;

    public RequestDiscountCouponData() {

    }

    public RequestDiscountCouponData(String voucherType, int start, int limit) {
        this.voucherType = voucherType;
        this.start = start;
        this.limit = limit;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
