/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlzy
 * 版    本：1.0
 * 创建日期：16/9/30
 * 描    述：
 * 修订历史：
 * ================================================
 */
package com.crv.ole.net;

import android.content.Context;
import android.text.TextUtils;

import com.crv.ole.BaseApplication;
import com.crv.ole.net.convertor.JsonConvert;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.sdk.utils.PreferencesHelper;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.convert.FileConvert;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpMethod;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.base.Request;
import com.lzy.okrx2.adapter.ObservableBody;
import com.lzy.okrx2.adapter.ObservableResponse;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * ================================================
 * 接口调用方法，根据不同接口或者要求调用不同的方法，也可以自己增加
 * ================================================
 */
public class ServerApi {

    public static Observable<String> getString(String url, HttpHeaders headers, HttpParams params) {
        //这个RxUtils的封装其实没有必要，只是有些人喜欢这么干，我就多此一举写出来了。。
        return RxUtils.request(HttpMethod.GET, url, String.class, params, headers);
    }

    public static <T> Observable<T> getData(HttpMethod httpMethod, Type type, String url, HttpHeaders headers, HttpParams params) {
        //这个RxUtils的封装其实没有必要，只是有些人喜欢这么干，我就多此一举写出来了。。
        return RxUtils.request(httpMethod, url, type, params, headers);
    }

    /**
     * 此方法为通用调用接口方法，会设置cookie，所以会话保护的接口一定要调用此方法
     *
     * @param isSaveCache 是否需要缓存
     * @param requestData
     * @param clazz
     * @param <T>
     * @return 测试并发时，此接口有bug
     */
    public static <T> Observable<T> request(boolean isSaveCache, RequestData requestData, Class<T> clazz, String sign) {
        if (requestData.setSign(sign)) {
            PostRequest<T> post = OkGo.<T>post(OleConstants.request_url)
                    .headers("Cookie", BaseApplication.getInstance().getRequestCookieParams())
                    .cacheKey(OleConstants.KEY.OKGO_CACHE_KEY)//这里完全同okgo的配置一样
                    .cacheMode(isSaveCache ? CacheMode.FIRST_CACHE_THEN_REQUEST : CacheMode.NO_CACHE)
                    .upJson(new Gson().toJson(requestData))
                    .converter(new JsonConvert<T>(clazz));
            return post.adapt(new ObservableBody<T>()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }

        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                emitter.onError(new Throwable("请求参数不完整"));
                Log.i("请求参数不完整");
            }
        });

    }


    public static Observable<Response<File>> getFile(String url, HttpHeaders headers, HttpParams params) {
        Log.e("文件地址：" + url);
        return OkGo.<File>get(url)//
                .converter(new FileConvert())//
                .adapt(new ObservableResponse<File>());
    }

    /**
     * 此方法为商品和资讯创建或者编辑收藏夹调用接口方法，会设置cookie，不会保存缓存
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Observable<T> requestUploadFile(String url, HttpParams params, Class<T> clazz) {
        Log.v("收藏文件夹相关参数:" + params.toString());
        PostRequest<T> post = OkGo.<T>post(url)
                .headers("Cookie", BaseApplication.getInstance().getRequestCookieParams())
                .params(params)
                .converter(new JsonConvert<T>(clazz));
        return post.adapt(new ObservableBody<T>());
    }


    private static void postRequest(RequestData requestData, String cacheKey, String sign, StringCallback callback) {
        if (requestData.setSign(sign)) {
            StringCallback stringCallback = new StringCallback() {
                @Override
                public void onError(Response<String> response) {
                    super.onError(response);
                    callback.onError(response);
                }

                @Override
                public void onStart(Request<String, ? extends Request> request) {
                    super.onStart(request);
                    callback.onStart(request);
                }

                @Override
                public void onCacheSuccess(Response<String> response) {
                    super.onCacheSuccess(response);
                    callback.onCacheSuccess(response);
                }

                @Override
                public void onFinish() {
                    super.onFinish();
                    callback.onFinish();
                }

                @Override
                public void onSuccess(Response<String> response) {
                    Log.d("响应的headers：" + response.headers().toString());
                    if (!TextUtils.isEmpty(response.headers().get("Cookie")) && response.headers().get("Cookie").indexOf("isid") != -1) {
                        BaseApplication.getInstance().setRequestCookieParams(response.headers().get("Cookie"));
                    }
                    callback.onSuccess(response);
                }
            };
            OkGo.<String>post(OleConstants.request_url)//
                    .headers("Cookie", BaseApplication.getInstance().getRequestCookieParams())
                    .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)//
                    .cacheKey(cacheKey)//
                    .upJson(new Gson().toJson(requestData))
                    .execute(stringCallback);
        }
    }

    /**
     * @param context  上下文
     * @param Api_ID   接口ID
     * @param paramMap 参数
     * @param callback 回调
     */
    public static void postRequest(Context context, String Api_ID, Map<String, String> paramMap, StringCallback callback) {
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(Api_ID);
        requestData.setREQUEST_DATA(paramMap == null ? new HashMap<String, String>() : paramMap);
        String sign = new PreferencesHelper(context).getString(OleConstants.KEY.REQUEST_SIGN_KEY);
        postRequest(requestData, requestData.getREQUEST_ATTRS().getApi_ID(), sign, callback);
    }

    /**
     * @param context  上下文
     * @param Api_ID   接口ID
     * @param paramMap 参数
     * @param callback 回调
     */
    public static void postRequest(Context context, String cacheKey, String Api_ID, Map<String, String> paramMap, StringCallback callback) {
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(Api_ID);
        requestData.setREQUEST_DATA(paramMap);
        String sign = new PreferencesHelper(context).getString(OleConstants.KEY.REQUEST_SIGN_KEY);
        postRequest(requestData, cacheKey, sign, callback);
    }

    /**
     * 此方法为通用调用接口方法，会设置cookie，所以会话保护的接口一定要调用此方法
     *
     * @param context
     * @param Api_ID
     * @param paramMap
     * @param <T>
     */
    public static <T> Observable<T> request(Context context, String Api_ID, Map<String, String> paramMap, Class<T> clazz) {
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(Api_ID);
        requestData.setREQUEST_DATA(paramMap == null ? new HashMap<String, String>() : paramMap);
        String sign = new PreferencesHelper(context).getString(OleConstants.KEY.REQUEST_SIGN_KEY);
        if (requestData.setSign(sign)) {
            PostRequest<T> post = OkGo.<T>post(OleConstants.request_url)
                    .headers("Cookie", BaseApplication.getInstance().getRequestCookieParams())
                    .cacheKey(Api_ID)//这里完全同okgo的配置一样
                    .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                    .upJson(new Gson().toJson(requestData))
                    .converter(new JsonConvert<T>(clazz));
            return post.adapt(new ObservableBody<T>()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }

        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                emitter.onError(new Throwable("请求参数不完整"));
                Log.i("请求参数不完整");
            }
        });

    }
}
