package com.crv.ole.net;

/**
 * Created by yanghongjun on 17/6/20.
 */
public abstract class OnResponseListener<T> {
    public abstract void onSuccess(T result);
    public abstract void onError(String errorMessage);
    public void onCancelled(Exception e){}
    public void onFinished() {}
    //网络请求之前回调
    public void onWaiting() {}
    //网络请求开始的时候回调
    public void onStarted() {}
    //下载的时候不断回调的方法
    public void onLoading(long total, long current, boolean isDownloading) {}
}