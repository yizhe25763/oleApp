package com.crv.ole.trial.model;

import java.io.Serializable;

/**
 * 试用支付邮费信息
 * Created by zhangbo on 2017/8/24.
 */

public class PostageInfo implements Serializable {

    private double cash;//所需现金
    private double integral;//所需积分

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public double getIntegral() {
        return integral;
    }

    public void setIntegral(double integral) {
        this.integral = integral;
    }
}
