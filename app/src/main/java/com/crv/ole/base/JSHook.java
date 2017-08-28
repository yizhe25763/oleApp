package com.crv.ole.base;

import android.webkit.JavascriptInterface;

import com.crv.ole.utils.Log;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yanghongjun on 17/8/8.
 */

public class JSHook {

    private JsCallbackLister lister;

    public JSHook(JsCallbackLister callbackLister) {
        this.lister = callbackLister;
    }

    @JavascriptInterface
    public void handleCollect(String json) {// 收藏
        Flowable.create(new FlowableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(FlowableEmitter<Boolean> e) throws Exception {
                if (JSHook.this.lister!=null){
                    e.onNext(true);
                }else{
                    e.onNext(false);
                }
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean value) throws Exception {
                        if (value) {
                            JSHook.this.lister.handleCollect(json);
                        }
                    }
                });
    }
    @JavascriptInterface
    public void goTo(String json){//立即购买
        Flowable.create(new FlowableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(FlowableEmitter<Boolean> e) throws Exception {
                if (JSHook.this.lister!=null){
                    e.onNext(true);
                }else{
                    e.onNext(false);
                }
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean value) throws Exception {
                        if (value) {
                            JSHook.this.lister.goTo(json);
                        }
                    }
                });
    }

    @JavascriptInterface
    public void fetch(String json){//获取原生数据
        Log.i("fetch来了"+json);
        Flowable.create(new FlowableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(FlowableEmitter<Boolean> e) throws Exception {
                if (JSHook.this.lister!=null){
                    e.onNext(true);
                }else{
                    e.onNext(false);
                }
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean value) throws Exception {
                        if (value) {
                            JSHook.this.lister.fetch(json);
                        }
                    }
                });


    }

    @JavascriptInterface
    public void showToast(String json){
        Log.i("toast来了"+json);
        Flowable.create(new FlowableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(FlowableEmitter<Boolean> e) throws Exception {
                if (JSHook.this.lister!=null){
                    e.onNext(true);
                }else{
                    e.onNext(false);
                }
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean value) throws Exception {
                        if (value) {
                            JSHook.this.lister.showToast(json);
                        }
                    }
                });
    }
    @JavascriptInterface
    public void toogleLoading(String json){
        Flowable.create(new FlowableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(FlowableEmitter<Boolean> e) throws Exception {
                if (JSHook.this.lister!=null){
                    e.onNext(true);
                }else{
                    e.onNext(false);
                }
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean value) throws Exception {
                        if (value) {
                            JSHook.this.lister.toogleLoading(json);
                        }
                    }
                });
    }

    @JavascriptInterface
    public void showPictureCollection(String json) {//显示图片浏览器
        Flowable.create(new FlowableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(FlowableEmitter<Boolean> e) throws Exception {
                if (JSHook.this.lister!=null){
                    e.onNext(true);
                }else{
                    e.onNext(false);
                }
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean value) throws Exception {
                        if (value) {
                            JSHook.this.lister.showPictureCollection(json);
                        }
                    }
                });

    }

}
