package com.crv.ole.trial.model;

import java.io.Serializable;

/**
 * 试用活动基础数据
 * Created by zhangbo on 2017/8/17.
 */

public class ActiveObj implements Serializable {
    private String beginTime;//开始时间
    private double createTime;//创建时间戳
    private String description;//活动说明
    private String endTime;//结束时间
    private String headImage;//图片URL
    private String id;//活动ID
    private String state;//状态
    private String title;//标题
    private String introduction;//介绍


    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public double getCreateTime() {
        return createTime;
    }

    public void setCreateTime(double createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
