package com.crv.ole.personalcenter.model;

import com.crv.ole.net.BaseResponseData;

/**
 * 获取订单列表返回数据
 * Created by zhangbo on 2017/8/16.
 */

public class OrderListResponse extends BaseResponseData {

    private OrderListResult RETURN_DATA;

    public OrderListResult getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(OrderListResult RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }
}
