package com.crv.ole.trial.model;

import java.io.Serializable;

/**
 * 备注信息
 * Created by zhangbo on 2017/8/20.
 */

public class DeliveryInfoExt implements Serializable{
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
