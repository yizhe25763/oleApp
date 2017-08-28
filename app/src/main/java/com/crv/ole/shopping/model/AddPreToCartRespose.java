package com.crv.ole.shopping.model;

import com.crv.ole.net.BaseResponseData;

/**
 * 添加预售至购物车
 * Created by zhangbo on 2017/8/16.
 */

public class AddPreToCartRespose extends BaseResponseData {

    private AddPreToCartData RETURN_DATA;

    public AddPreToCartData getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(AddPreToCartData RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }
}
