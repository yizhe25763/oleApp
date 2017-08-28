package com.crv.ole.shopping.model;

import com.crv.ole.net.BaseResponseData;

/**
 * Created by lihongshi on 2017/8/5.
 * 购物车商品数量
 */

public class CartCountResponseData extends BaseResponseData {
    private RETURN_DATA RETURN_DATA;

    public RETURN_DATA getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(RETURN_DATA RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }

    public class RETURN_DATA {

        private String count;

        public void setCount(String count) {
            this.count = count;
        }

        public String getCount() {
            return count;
        }

    }

}
