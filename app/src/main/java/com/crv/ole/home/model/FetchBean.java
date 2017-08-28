package com.crv.ole.home.model;

import java.util.HashMap;

/**
 * Created by yanghongjun on 17/8/17.
 */

public class FetchBean {

    String apiId;
    HashMap<String, Object> params;
    String callback;
    String requireLogin;
    String callbackData;


    public String getApiId() {
        return apiId;
    }
    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public HashMap<String, Object> getParams() {
        return params;
    }
    public void setParams(HashMap<String, Object> params) {
        this.params = params;
    }

    public String getCallbackData() {
        return callbackData;
    }
    public void setCallbackData(String callbackData) {
        this.callbackData = callbackData;
    }

    @Override
    public String toString() {
        return "FetchBean{" +
                "apiId='" + apiId + '\'' +
                ", params=" + params +
                ", callback='" + callback + '\'' +
                ", requireLogin='" + requireLogin + '\'' +
                ", callbackData='" + callbackData + '\'' +
                '}';
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getRequireLogin() {
        return requireLogin;
    }

    public void setRequireLogin(String requireLogin) {
        this.requireLogin = requireLogin;
    }


}
