package com.crv.ole.personalcenter.model;

import java.io.Serializable;

/**
 * 	买家评论状态 br100：未评论 br101: 已评论
 * Created by zhangbo on 2017/8/16.
 */

public class BuyerReviewInfo implements Serializable {

    private String name;
    private String state;

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
