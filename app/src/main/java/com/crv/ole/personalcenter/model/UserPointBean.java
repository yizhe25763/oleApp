package com.crv.ole.personalcenter.model;

/**
 * Created by lihongshi on 2017/7/26.
 * 用户总积分
 */

public class UserPointBean {

    private String point;//会员积分
    private String lastusedate;//最近消费日期

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getLastusedate() {
        return lastusedate;
    }

    public void setLastusedate(String lastusedate) {
        this.lastusedate = lastusedate;
    }

    @Override
    public String toString() {
        return "UserPointResponseData{" +
                "point='" + point + '\'' +
                ", lastusedate='" + lastusedate + '\'' +
                '}';
    }
}
