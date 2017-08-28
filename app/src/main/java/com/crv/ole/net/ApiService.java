package com.crv.ole.net;

import java.io.File;
import java.util.HashMap;

/**
 * Created by yanghongjun on 17/6/20.
 */

public class ApiService {

    private static ApiService instance;

    public static ApiService getInstance() {
        synchronized (ApiService.class) {
            if (instance == null) {
                instance = new ApiService();
            }
        }
        return instance;
    }

    /**
     * 登录
     * @param url
     * @param requestMap
     * @param responseListener
     */
    public void login(String url, HashMap<String, String> requestMap, final OnResponseListener responseListener) {

        NetUtil.getRequest(url, requestMap, new OnResponseListener<String>() {

            public void onSuccess(String result) {
                responseListener.onSuccess(result);
            }

            public void onError(String errorMessage) {
                responseListener.onError(errorMessage);
            }

            public void onCancelled(Exception e) {
                responseListener.onCancelled(e);
            }

            public void onFinished() {
                responseListener.onFinished();
            }
        });

    }

    /**
     * 下载apk
     * @param url
     * @param requestMap
     * @param responseListener
     */

    public void downLoadApp(String url, final HashMap<String, String> requestMap, final OnResponseListener responseListener) {

        NetUtil.downLoadRequest(url, requestMap, new OnResponseListener<File>() {
            @Override
            public void onSuccess(File result) {
                responseListener.onSuccess(result);
            }

            @Override
            public void onError(String errorMessage) {
                responseListener.onError(errorMessage);
            }

            @Override
            public void onWaiting() {
                responseListener.onWaiting();
            }

            @Override
            public void onStarted() {
                responseListener.onStarted();
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                responseListener.onLoading(total,current,isDownloading);
            }
        });

    }


}
