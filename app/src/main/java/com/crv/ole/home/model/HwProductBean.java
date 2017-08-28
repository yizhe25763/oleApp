package com.crv.ole.home.model;

import java.util.HashMap;

/**
 * Created by yanghongjun on 17/8/17.
 */

public class HwProductBean {
    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getCallbackData() {
        return callbackData;
    }

    public void setCallbackData(String callbackData) {
        this.callbackData = callbackData;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    String pageName;
    HashMap<String, String> params;
    String callback;
    String callbackData;
    String productId;

    public HashMap<String, String> getParams() {
        return params;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }


    @Override
    public String toString() {
        return "HwProductBean{" +
                "pageName='" + pageName + '\'' +
                ", parmas=" + params +
                '}';
    }

}
