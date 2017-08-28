package com.crv.ole.trial.model;

import java.io.Serializable;

/**
 * 温控
 * Created by zhangbo on 2017/8/18.
 */

public class TemperatureControl implements Serializable {
    //"des":"常温","state":"1"

    private String des;
    private String state;

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
