package com.crv.sdk.event;

/**
 * 通用的event类
 * Created by Administrator on 2016-04-15.
 */
public class BaseEvent {
    private int params;

    public BaseEvent(int params) {
        this.params = params;
    }

    public int getParams() {
        return params;
    }

    public void setParams(int params) {
        this.params = params;
    }
}
