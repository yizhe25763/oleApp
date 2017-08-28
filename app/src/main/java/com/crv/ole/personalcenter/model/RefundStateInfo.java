package com.crv.ole.personalcenter.model;

import java.io.Serializable;

/**
 * 退款状态
 * Created by zhangbo on 2017/8/20.
 */

public class RefundStateInfo implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
