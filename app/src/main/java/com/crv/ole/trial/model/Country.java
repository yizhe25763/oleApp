package com.crv.ole.trial.model;

import java.io.Serializable;

/**
 * 国家
 * Created by zhangbo on 2017/8/18.
 */

public class Country implements Serializable {

    private String CName;//中文名
    private String imgUrl; //国家图标
    private String name;//英文名

    public String getCName() {
        return CName;
    }

    public void setCName(String CName) {
        this.CName = CName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
