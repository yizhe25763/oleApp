package com.crv.ole.personalcenter.model;

import com.crv.ole.net.BaseResponseData;

import java.io.Serializable;
import java.util.List;

/**
 * 订单列表
 * Created by zhangbo on 2017/8/16.
 */

public class OrderListResult implements Serializable {

    private List<OrderData> orderList;

    private String total;//符合条件的订单总数

    public List<OrderData> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderData> orderList) {
        this.orderList = orderList;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
