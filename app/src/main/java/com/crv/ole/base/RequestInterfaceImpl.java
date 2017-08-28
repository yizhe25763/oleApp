package com.crv.ole.base;


import com.crv.ole.net.RequestData;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.PreferencesUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 请求接口实现类
 * Created by zhangbo on 2017/8/5.
 */

public class RequestInterfaceImpl<T> implements BaseRequestInterface<T> {


    private CompositeDisposable compositeDisposable;

    private static Map<String, Disposable> disposableMap = new HashMap<>();

    private static String currentKey;

    public RequestInterfaceImpl() {
        compositeDisposable = new CompositeDisposable();
    }


    @Override
    public void request(RequestData requestData, Class clazz, BaseRequestCallback callback, boolean isSaveCache) {
        requestData.setSign(PreferencesUtils.getInstance().getString(OleConstants.KEY.REQUEST_SIGN_KEY));
        HttpClientManager.getInstance().getPostRequest(requestData, clazz, isSaveCache)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    callback.onStart();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {
                    callback.onEnd();
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        if (compositeDisposable == null) {
                            compositeDisposable = new CompositeDisposable();
                        }
                        compositeDisposable.add(d);
                        disposableMap.put(requestData.getRequestAttrsInstance().getApi_ID(), d);
                        currentKey = requestData.getRequestAttrsInstance().getApi_ID();
                    }

                    @Override
                    public void onNext(@NonNull T t) {
                        callback.onSuccess(t);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("RequestInterfaceImpl", e.getMessage());
                        callback.onFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void get(String url, Class<T> clazz, BaseRequestCallback<T> callback) {
        HttpClientManager.getInstance().getGetRequest(url, clazz)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    callback.onStart();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {
                    callback.onEnd();
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        if (compositeDisposable == null) {
                            compositeDisposable = new CompositeDisposable();
                        }
                        compositeDisposable.add(d);
                        disposableMap.put(url, d);
                        currentKey = url;
                    }

                    @Override
                    public void onNext(@NonNull T t) {
                        callback.onSuccess(t);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        callback.onFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void upload(String url, List<File> files, Class<T> clazz, BaseRequestCallback<T> callback) {
        HttpClientManager.getInstance().getUpLoadRequest(url, files, clazz)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    callback.onStart();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {
                    callback.onEnd();
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        if (compositeDisposable == null) {
                            compositeDisposable = new CompositeDisposable();
                        }
                        compositeDisposable.add(d);
                        disposableMap.put(url, d);
                        currentKey = url;
                    }

                    @Override
                    public void onNext(@NonNull T t) {
                        callback.onSuccess(t);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        callback.onFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 清除订阅
     */
    @Override
    public void clear() {
        if (compositeDisposable != null) {
            Iterator entries = disposableMap.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry entry = (Map.Entry) entries.next();
                String key = (String) entry.getKey();
                Disposable value = (Disposable) entry.getValue();
                if (!currentKey.equals(key)) {
                    compositeDisposable.delete(value);
                    entries.remove();
                }
            }
        }
    }
}
