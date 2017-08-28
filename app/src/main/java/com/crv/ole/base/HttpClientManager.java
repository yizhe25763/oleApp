package com.crv.ole.base;


import com.crv.ole.BaseApplication;
import com.crv.ole.net.RequestData;
import com.crv.ole.net.convertor.JsonConvert;
import com.crv.ole.utils.OleConstants;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okrx2.adapter.ObservableBody;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;

/**
 * 请求客户端管理
 * Created by zhangbo on 2017/8/5.
 */

public class HttpClientManager<T> {
    private volatile static HttpClientManager mHttpClientManager;

    private PostRequest<T> post;

    private GetRequest<T> get;

    private PostRequest<T> upload;

    private HttpClientManager() {

    }

    public static HttpClientManager getInstance() {
        if (mHttpClientManager == null) {
            synchronized (HttpClientManager.class) {
                if (mHttpClientManager == null) {
                    mHttpClientManager = new HttpClientManager();
                }
            }
        }
        return mHttpClientManager;
    }


    public Observable<T> getPostRequest(RequestData requestData, Class<T> clazz, boolean isCache) {
        post = OkGoPostConfig.builder()
                .url(OleConstants.request_url)
                .cookie(BaseApplication.getInstance().getRequestCookieParams())
                .cacheKey(requestData.getRequestAttrsInstance().getApi_ID())
                .isSaveCache(isCache)
                .json(requestData)
                .converter(new JsonConvert<T>(clazz))
                .build().getPost();
        return post.adapt(new ObservableBody<T>());
    }

    public Observable<T> getGetRequest(String url, Class<T> clazz) {
        get = OkGoGetConfig.builder()
                .url(url)
                .converter(new JsonConvert<T>(clazz))
                .build().get();
        return get.adapt(new ObservableBody<T>());
    }


    public Observable<T> getUpLoadRequest(String url, List<File> files, Class<T> clazz) {
        upload = OkGoUploadConfig.builder()
                .url(url)
                .files(files)
                .converter(new JsonConvert<T>(clazz))
                .build().upload();
        return upload.adapt(new ObservableBody<T>());
    }


}
