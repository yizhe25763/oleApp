package com.crv.ole.home.activity;

import android.content.Intent;
import android.os.Bundle;

import com.crv.ole.R;
import com.crv.ole.base.BaseAppCompatActivity;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.BaseWebView;
import com.crv.ole.base.JSHook;
import com.crv.ole.base.JsCallbackLister;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.home.model.FetchBean;
import com.crv.ole.home.model.HwProductBean;
import com.crv.ole.home.model.ToogleLoading;
import com.crv.ole.login.activity.LoginActivity;
import com.crv.ole.personalcenter.tools.CollectionUtils;
import com.crv.ole.shopping.activity.ProductDetailActivity;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.crv.ole.utils.ToolUtils;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * 电子价签详情页
 */
public class BarCodeDetailActivity extends BaseAppCompatActivity {
    private BaseWebView mWebView;
    private String url = "https://www.pgyer.com/ole_android";
    JSHook jsHook = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String barCode = this.getIntent().getStringExtra(OleConstants.BundleConstant.ARG_PARAMS_0);
        boolean isLogin = ToolUtils.isLoginStatus(mContext);
        //6936587579933
        url = OleConstants.BASE_HOST + "/img/oleH5/dist/index.html#/ProductsFromScanCode?barCode=" + barCode + "&isLogin=" + (isLogin ? "1" : "0");
       // url = OleConstants.BASE_HOST + "/img/oleH5/dist/index.html#/ProductsFromScanCode?barCode=" + "6936587579933" + "&isLogin=" + (isLogin ? "1" : "0");
        //url ="http://10.0.147.163/img/oleH5/dist/index.html#/ProductsFromScanCode?barCode=6935317708346&isLogin=1";
        initWebView();
    }

    private void initWebView() {
        mWebView = (BaseWebView) this.findViewById(R.id.webview);
        jsHook = new JSHook(new JsCallbackLister() {
            @Override
            public void handleCollect(String json) {
                Log.i("收藏:" + json);
                HwProductBean productBean = new Gson().fromJson(json, HwProductBean.class);
                Log.i("收藏bean:" + productBean.toString());
                String callbackStr = productBean.getCallback();
                CollectionUtils.getInstance().setOnCollectionListener2(new CollectionUtils.OnCollectionListener2() {
                    @Override
                    public void onCollection(Object responseObject) {
                        String jsonStr = new Gson().toJson(responseObject);
                        Log.i("thread:" + Thread.currentThread());
                        if (callbackStr != null) {
                            String callbackFuntion = "";
                            if (productBean.getCallbackData() != null) {
                                callbackFuntion = "javascript:H5." + callbackStr + "(" + jsonStr + "," + productBean.getCallbackData() + ")";
                            } else {
                                callbackFuntion = "javascript:H5." + callbackStr + "(" + jsonStr + ")";
                            }
                            Log.i("callback是:" + callbackFuntion);
                            String finalCallbackFuntion = callbackFuntion;
                            mWebView.post(new Runnable() {
                                @Override
                                public void run() {
                                    mWebView.loadUrl(finalCallbackFuntion);
                                }
                            });
                        }
                    }
                });
                ArrayList<String> list = new ArrayList<>();
                list.add(productBean.getProductId());
                CollectionUtils.getInstance().showCollectionDialog(BarCodeDetailActivity.this, CollectionUtils.CollectionTypeEnum.GOODS_COLLECTION, list);
            }

            @Override
            public void goTo(String json) {
                Log.i("立刻购买" + json);
                HwProductBean productBean = new Gson().fromJson(json, HwProductBean.class);
                Log.i("立刻购买Bean:" + productBean.toString());
                if (productBean != null) {
                    Intent intent = new Intent(mContext, ProductDetailActivity.class);
                    intent.putExtra("productId", productBean.getParams().get("productId"));
                    intent.putExtra("skuId", productBean.getParams().get("skuId"));
                    intent.putExtra("state", "buyNow");
                    startActivity(intent);
                }
            }

            @Override
            public void fetch(String json) {

                FetchBean fetchBean = new Gson().fromJson(json, FetchBean.class);
                Log.i("fetch结果是:" + fetchBean.toString());
                String callbackStr = fetchBean.getCallback();
                if ("1".equals(fetchBean.getRequireLogin()) && !ToolUtils.isLoginStatus(BarCodeDetailActivity.this)) {
                    Intent intent = new Intent(BarCodeDetailActivity.this, LoginActivity.class);
                    String callbackFuntion = "javascript:H5.callback.login('loginsuccess')";
                    intent.putExtra("jscallback", callbackFuntion);
                    startActivityForResult(intent, OleConstants.noticeJsLogInSuccess);
                    return;
                }
                Log.i("apiId:" + fetchBean.getApiId());

                ServiceManger.getInstance().fetchData(fetchBean.getApiId(), fetchBean.getParams(), new BaseRequestCallback<String>() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("成功:" + response);
                        if (callbackStr != null) {
                            String callbackFuntion = "";
                            if (fetchBean.getCallbackData() != null) {
                                callbackFuntion = "javascript:H5." + callbackStr + "(" + response + "," + fetchBean.getCallbackData() + ")";
                            } else {
                                callbackFuntion = "javascript:H5." + callbackStr + "(" + response + ")";
                            }
                            Log.i("callback是:" + callbackFuntion);
                            String finalCallbackFuntion = callbackFuntion;
                            mWebView.post(new Runnable() {
                                @Override
                                public void run() {
                                    Log.e(TAG, "URL====:" + finalCallbackFuntion);
                                    mWebView.loadUrl(finalCallbackFuntion);
                                }
                            });
                        }

                    }

                    @Override
                    public void onFailed(String msg) {
                        ToastUtil.showToast(msg);
                    }
                });

            }

            @Override
            public void showToast(String json) {
                ToogleLoading toogleLoading = new Gson().fromJson(json, ToogleLoading.class);
                if (toogleLoading.getContent() != null) {
                    ToastUtil.showToast(toogleLoading.getContent());
                }
            }

            @Override
            public void toogleLoading(String json) {
                ToogleLoading toogleLoading = new Gson().fromJson(json, ToogleLoading.class);
                Log.i("toogleLoading:" + toogleLoading.toString());
                if (toogleLoading.getVisible().equals("1")) {
                    showProgressDialog("加载中...", null);
                } else {
                    dismissProgressDialog();
                }
            }
        });
        mWebView.loadUrl(url);
        mWebView.addJavascriptInterface(jsHook, "NATIVE");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bar_code_detail;
    }
}
