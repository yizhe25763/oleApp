package com.crv.ole.classfiy.model;

import com.crv.ole.net.BaseResponseData;

import java.io.Serializable;

/**
 * Created by zhangbo on 2017/8/5.
 */

public class ProductClassfiyResult extends BaseResponseData implements Serializable {
    private ProductData RETURN_DATA;

    public ProductData getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(ProductData RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }
}
