package com.crv.ole.base;

import com.crv.ole.net.RequestData;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;

/**
 * 请求基础接口
 * Created by zhangbo on 2017/8/5.
 */

public interface BaseRequestInterface<T> {

    void request(RequestData requestData, Class<T> clazz, BaseRequestCallback<T> callback, boolean isSaveCache);

    void get(String url, Class<T> clazz, BaseRequestCallback<T> callback);

    void upload(String url, List<File> files, Class<T> clazz, BaseRequestCallback<T> callback);

    void clear();
}
