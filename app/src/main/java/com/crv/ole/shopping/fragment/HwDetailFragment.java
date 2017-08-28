
package com.crv.ole.shopping.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.crv.ole.R;
import com.crv.ole.base.BaseFragment;
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
import com.crv.ole.shopping.activity.HwDetailActivity;
import com.crv.ole.shopping.activity.ProductDetailActivity;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.crv.ole.utils.ToolUtils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class HwDetailFragment extends BaseFragment {
    private Context mContext;
    ProgressBar pg1 = null;
    JSHook jsHook = null;

    public BaseWebView webView = null;
    private String url = "";
    // private String url = "http://10.233.72.101:3007/dist/index.html#/RecommendedProducts?activeId=50001";

    public static HwDetailFragment newInstance(String detailUrl) {
        HwDetailFragment f = new HwDetailFragment();
        f.url = detailUrl;
        Log.i("好物详情url:" + detailUrl);
        return f;
    }

    @SuppressLint("JavascriptInterface")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.hw_fragment, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View rootView) {
        jsHook = new JSHook(new JsCallbackLister() {
            @Override
            public void handleCollect(String json) {
                if (!ToolUtils.isLoginStatus(getActivity())) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                Log.i("收藏:" + json);
                HwProductBean productBean = new Gson().fromJson(json, HwProductBean.class);
                Log.i("收藏bean:" + productBean.toString());
                String callbackStr = productBean.getCallback();
                CollectionUtils.getInstance().setOnCollectionListener2(new CollectionUtils.OnCollectionListener2() {
                    @Override
                    public void onCollection(Object responseObject) {
                        String jsonStr = new Gson().toJson(responseObject);
                        Log.i("收藏结果是:"+jsonStr);
                        if (callbackStr != null) {
                            String callbackFuntion = "";
                            if (productBean.getCallbackData() != null) {
                                callbackFuntion = "javascript:H5." + callbackStr + "(" + jsonStr + "," + productBean.getCallbackData() + ")";
                            } else {
                                callbackFuntion = "javascript:H5." + callbackStr + "(" + jsonStr + ")";
                            }
                            Log.i("callback是:" + callbackFuntion);
                            String finalCallbackFuntion = callbackFuntion;
                            webView.post(new Runnable() {
                                @Override
                                public void run() {
                                    webView.loadUrl(finalCallbackFuntion);
                                }
                            });
                        }
                    }
                });
                ArrayList<String> list = new ArrayList<>();
                list.add(productBean.getProductId());
                CollectionUtils.getInstance().showCollectionDialog(getActivity(), CollectionUtils.CollectionTypeEnum.GOODS_COLLECTION, list);
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
                if ("1".equals(fetchBean.getRequireLogin()) && !ToolUtils.isLoginStatus(getActivity())) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
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
                            webView.post(new Runnable() {
                                @Override
                                public void run() {
                                    webView.loadUrl(finalCallbackFuntion);
                                }
                            });

                            if (OleConstants.CART_ADD.equals(fetchBean.getApiId())) {
                                EventBus.getDefault().post(OleConstants.REFRESH_GWC_COUNT);
                            }
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
                HwDetailActivity activity = (HwDetailActivity) getActivity();
                if (activity == null) {
                    return;
                }
                if (toogleLoading.getVisible().equals("1")) {
                    activity.showProgressDialog("加载中...", null);
                } else {
                    activity.dismissProgressDialog();
                }
            }
        });
        webView = (BaseWebView) rootView.findViewById(R.id.webview1);
        pg1 = (ProgressBar) rootView.findViewById(R.id.progressBar1);
        webView.loadUrl(url);
        webView.addJavascriptInterface(jsHook, "NATIVE");
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    pg1.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    pg1.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    pg1.setProgress(newProgress);//设置进度值
                }
            }
        });
        webView.setOnScrollChangeListener(new BaseWebView.OnScrollChangeListener() {
            @Override
            public void onPageEnd(int l, int t, int oldl, int oldt) {
                Log.i("好物到底了");
                EventBus.getDefault().post(OleConstants.WEB_SCROLL_BOTTOM);
            }

            @Override
            public void onPageTop(int l, int t, int oldl, int oldt) {
            }

            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
            }
        });
    }

}
