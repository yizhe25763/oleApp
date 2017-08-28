package com.crv.ole.pay.model;

import com.crv.ole.net.BaseResponseData;

import java.io.Serializable;

/**
 * 作用描述：
 * 创建者： wj_wsf
 * 创建时间： 2017/8/7 09:24.
 */

public class OrderConfirmInfoData extends BaseResponseData implements Serializable {
    private OrderConfirmInfo RETURN_DATA;

    public OrderConfirmInfo getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(OrderConfirmInfo RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }
}
