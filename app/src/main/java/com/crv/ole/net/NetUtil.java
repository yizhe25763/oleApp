package com.crv.ole.net;

import android.os.Environment;
import android.util.Log;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.HashMap;

/**
 * Created by yanghongjun on 17/6/20.
 */

public class NetUtil {
    /**
     * 普通的get请求
     *
     * @param url
     * @param paramsMap
     * @param onResponseListener
     */

    public static void getRequest(String url, HashMap<String, String> paramsMap, final OnResponseListener onResponseListener) {

        RequestParams requestParams = new RequestParams(url);

        if (paramsMap != null && paramsMap.size() > 0) {
            for (String mapKey : paramsMap.keySet()) {
                requestParams.addQueryStringParameter(mapKey, paramsMap.get(mapKey));
            }
        }
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                onResponseListener.onSuccess(result);
            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                onResponseListener.onError(ex.getMessage());
            }

            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
                onResponseListener.onCancelled(cex);
            }

            @Override
            public void onFinished() {
                onResponseListener.onFinished();
            }
        });
    }

    /**
     * post请求
     *
     * @param url                请求url
     * @param isMultipart        是否文件上传
     * @param paramsMap          请求参数
     * @param onResponseListener 请求回调
     */
    public static void postRequest(String url, boolean isMultipart, HashMap<String, String> paramsMap, final OnResponseListener onResponseListener) {
        RequestParams requestParams = new RequestParams(url);
        requestParams.setMultipart(isMultipart);
        if (paramsMap != null && paramsMap.size() > 0) {
            for (String mapKey : paramsMap.keySet()) {
                requestParams.addBodyParameter(mapKey, paramsMap.get(mapKey));
            }
        }
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                onResponseListener.onSuccess(result);
            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                onResponseListener.onError(ex.getMessage());
            }

            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
                onResponseListener.onCancelled(cex);
            }

            @Override
            public void onFinished() {
                onResponseListener.onFinished();
            }
        });

    }

    /**
     * 上传文件请求
     *
     * @param url
     * @param paramsMap
     * @param onResponseListener
     */
    public static void uploadRequest(String url, HashMap<String, String> paramsMap, final OnResponseListener onResponseListener) {
        postRequest(url, true, paramsMap, onResponseListener);
    }

    /**
     * 文件下载:例如下载apk
     *
     * @param url
     * @param paramsMap
     * @param onResponseListener
     */
    public static void downLoadRequest(String url, HashMap<String, String> paramsMap, final OnResponseListener onResponseListener) {
        RequestParams params = new RequestParams(url);
        params.setSaveFilePath(Environment.getExternalStorageDirectory() + "/myapp/");
        params.setAutoRename(true);
        x.http().post(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(File result) {
                onResponseListener.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                onResponseListener.onError(ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

                onResponseListener.onCancelled(cex);
            }

            @Override
            public void onFinished() {
                onResponseListener.onFinished();
            }

            //网络请求之前回调
            @Override
            public void onWaiting() {
                onResponseListener.onWaiting();
            }

            //网络请求开始的时候回调
            @Override
            public void onStarted() {
                onResponseListener.onStarted();
            }

            //下载的时候不断回调的方法
            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                //当前进度和文件总大小
                Log.i("JAVA", "current：" + current + "，total：" + total);
                onResponseListener.onLoading(total, current, isDownloading);
            }
        });
    }

}
