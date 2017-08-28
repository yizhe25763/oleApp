package com.crv.ole.trial.model;

import java.io.Serializable;

/**
 * 试用报告模版
 * Created by zhangbo on 2017/8/25.
 */

public class TrialReportInputDate implements Serializable {
    private long createTime;
    private String id;
    private String statement1;//#一句话试用总结#
    private String statement2;//#收到时的心情#
    private String statement3;//#该商品触感如何#
    private String statement4;//#和你用过的商品相比较#
    private String statement5;//#自由发言...#

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatement1() {
        return statement1;
    }

    public void setStatement1(String statement1) {
        this.statement1 = statement1;
    }

    public String getStatement2() {
        return statement2;
    }

    public void setStatement2(String statement2) {
        this.statement2 = statement2;
    }

    public String getStatement3() {
        return statement3;
    }

    public void setStatement3(String statement3) {
        this.statement3 = statement3;
    }

    public String getStatement4() {
        return statement4;
    }

    public void setStatement4(String statement4) {
        this.statement4 = statement4;
    }

    public String getStatement5() {
        return statement5;
    }

    public void setStatement5(String statement5) {
        this.statement5 = statement5;
    }
}
