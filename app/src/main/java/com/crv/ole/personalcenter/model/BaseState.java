package com.crv.ole.personalcenter.model;

import java.io.Serializable;

/**
 * 基本状态类
 * Created by zhangbo on 2017/8/25.
 */

public class BaseState implements Serializable {
    private String desc;//描述
    private String state;//状态码

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
