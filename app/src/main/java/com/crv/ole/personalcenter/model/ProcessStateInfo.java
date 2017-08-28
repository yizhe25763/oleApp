package com.crv.ole.personalcenter.model;

import java.io.Serializable;

/**
 * 订单处理状态信息 p101：待出库 p102：已发货 p112：已签收
 * Created by zhangbo on 2017/8/16.
 */

public class ProcessStateInfo implements Serializable {

    private String name;//状态描述
    private String state;//状态编码

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
