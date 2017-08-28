package com.crv.sdk.event;

/**
 * Created by Administrator on 2016-04-15.
 */
public class OpenNetEvent {
    private String apiName;

    public OpenNetEvent(String apiName) {
        this.apiName = apiName;
    }

    public String getApiName() {
        return apiName;
    }
}
