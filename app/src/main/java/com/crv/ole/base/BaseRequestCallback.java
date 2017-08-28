package com.crv.ole.base;

/**
 * 回调接口
 * Created by zhangbo on 2017/8/5.
 */

public abstract class BaseRequestCallback<T> {

    //请求开始 弹进度框
    public void onStart() {}

    //请求成功返回数据
    public abstract void onSuccess(T data);

    //请求失败
    public void onFailed(String msg) {}

    //请求结束 隐藏进度框
    public void onEnd() {}
}
