package com.crv.ole.trial.model;

import com.crv.ole.net.BaseResponseData;

import java.io.Serializable;

/**
 * 作用描述：
 * 创建者： wj_wsf
 * 创建时间： 2017/8/26 15:31.
 */

public class TrialReportGoodsItemData extends BaseResponseData implements Serializable {
    private String name, productId, productImage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}
