package com.crv.ole.utils;

import com.crv.ole.net.RequestData;

import java.util.HashMap;

/**
 * 请求参数全部在这里封装
 * 调用接口之前，设置完相关参数后，最后调用一次setSign方法，否则可能会出错
 * <p>
 * 如果调用网络请求是使用ServerApi.request()方法的话，可以不调用setSign()方法，request方法中会自己调用
 * 但是需要判断request方法的返回值是否为null，如果setSign()方法中参数不完整，request方法就会返回null
 * Created by wj_wsf on 2017/7/6 16:45.
 */

public class RequestParamsUtils<T> {

    private volatile static RequestParamsUtils requestParamsUtils;

    private RequestData requestData;

    private RequestParamsUtils() {
        requestData = new RequestData();
    }

    public static RequestParamsUtils getInstance() {
        if (requestParamsUtils == null) {
            synchronized (RequestParamsUtils.class) {
                if (requestParamsUtils == null) {
                    requestParamsUtils = new RequestParamsUtils();
                }
            }
        }
        return requestParamsUtils;
    }

    /**
     * 获取 RequestData
     *
     * @param params
     * @param Api_id
     * @return
     */
    public RequestData getRequestData(HashMap<String,String> params, String Api_id) {
        requestData.getRequestAttrsInstance().setApi_ID(Api_id);
        requestData.setREQUEST_DATA(params);
        return requestData;
    }

    public RequestData getRequestData(Object params, String Api_id) {
        requestData.getRequestAttrsInstance().setApi_ID(Api_id);
        requestData.setREQUEST_DATA(params);
        return requestData;
    }


}
