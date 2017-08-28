package com.crv.ole.information.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.crv.ole.BaseApplication;
import com.crv.ole.R;
import com.crv.ole.base.BaseFragment;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.BaseWebView;
import com.crv.ole.base.JSHook;
import com.crv.ole.base.JsCallbackLister;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.databinding.FragmentWebViewBinding;
import com.crv.ole.home.model.FetchBean;
import com.crv.ole.home.model.ToogleLoading;
import com.crv.ole.information.model.ArticleBean;
import com.crv.ole.information.model.ArticleImageBean;
import com.crv.ole.information.model.LikeBean;
import com.crv.ole.information.model.SpecialDerailResult;
import com.crv.ole.login.activity.LoginActivity;
import com.crv.ole.personalcenter.tools.CollectionUtils;
import com.crv.ole.shopping.activity.LookPicActivity;
import com.crv.ole.shopping.model.PhotoInfo;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.SerializableManager;
import com.crv.ole.utils.ToastUtil;
import com.crv.ole.utils.ToolUtils;
import com.crv.ole.utils.UmengUtils;
import com.crv.sdk.utils.TextUtil;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by lihongshi on 2017/8/24.
 */

public class WebViewFragment extends BaseFragment implements View.OnClickListener {
    private FragmentWebViewBinding mDataBinding;
    private String url;
    private String id;
    private String cacheKey;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getArguments().getString(OleConstants.BundleConstant.ARG_PARAMS_0, "");
        id = getArguments().getString(OleConstants.BundleConstant.ARG_PARAMS_1, "");
        //  url = "http://www.jianshu.com/p/cb5b012d1d85";
        cacheKey = "article_" + BaseApplication.getInstance().getUserId() + "_" + id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_web_view, container, false);
        return mDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        showView(SerializableManager.readSerializable(getContext(), cacheKey));
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        mDataBinding.baseWebView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mDataBinding.baseWebView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDataBinding.baseWebView.destroy();
    }

    public static WebViewFragment newInstant(String id, String url) {
        WebViewFragment webViewFragment = new WebViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(OleConstants.BundleConstant.ARG_PARAMS_0, url);
        bundle.putString(OleConstants.BundleConstant.ARG_PARAMS_1, id);
        webViewFragment.setArguments(bundle);
        return webViewFragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back: { //后退
                getActivity().finish();
                break;
            }
            case R.id.pl_layout: { //评论
                comment((SpecialDerailResult) v.getTag());
                break;
            }
            case R.id.sc_layout: { //收藏
                shouCang((SpecialDerailResult) v.getTag());
                break;
            }
            case R.id.dz_layout: { //点赞
                dianZan((SpecialDerailResult) v.getTag());
                break;
            }
            case R.id.shape: { //分享
                shape((SpecialDerailResult) v.getTag());
                break;
            }
        }
    }


    JSHook jsHook = new JSHook(new JsCallbackLister() {
        @Override
        public void goTo(String json) {
            Log.i("GoTo另外一篇文章:" + json);
            ArticleBean articleBean = new Gson().fromJson(json, ArticleBean.class);
            Log.i("结果是:"+articleBean.toString());
        }

        @Override
        public void showPictureCollection(String json) {
            ArrayList<PhotoInfo> imgsList = new ArrayList<PhotoInfo>();
            ArticleImageBean articleImageBean = new Gson().fromJson(json, ArticleImageBean.class);
            for (String url : articleImageBean.getImgs()) {
                PhotoInfo info = new PhotoInfo();
                info.setSourcePath(url);
                imgsList.add(info);
            }
            Log.i("图片集是:" + json);
            Intent intent = new Intent(mContext, LookPicActivity.class);
            intent.putExtra("EXTRA_IMAGE_LIST", imgsList);
            intent.putExtra("EXTRA_CURRENT_IMG_POSITION", articleImageBean.getIndex());
            mContext.startActivity(intent);
        }

        @Override
        public void fetch(String json) {
            FetchBean fetchBean = new Gson().fromJson(json, FetchBean.class);
            Log.i("fetch结果是:" + fetchBean.toString());
            String callbackStr = fetchBean.getCallback();
            if ("1".equals(fetchBean.getRequireLogin()) && !ToolUtils.isLoginStatus(mContext)) {
                Intent intent = new Intent(mContext, LoginActivity.class);
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
                        mDataBinding.baseWebView.post(new Runnable() {
                            @Override
                            public void run() {
                                mDataBinding.baseWebView.loadUrl(finalCallbackFuntion);
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
            if (toogleLoading.getVisible().equals("1")) {

             //   showProgressDialog("加载中...", null);
            } else {
               // dismissProgressDialog();
            }
        }
    });


    private void initView() {
        //mDataBinding.bottomLayout.setVisibility(View.GONE);
        mDataBinding.back.setOnClickListener(this);
        mDataBinding.plLayout.setOnClickListener(this);
        mDataBinding.scLayout.setOnClickListener(this);
        mDataBinding.dzLayout.setOnClickListener(this);
        mDataBinding.shape.setOnClickListener(this);

        mDataBinding.baseWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });

        mDataBinding.baseWebView.setOnScrollChangeListener(new BaseWebView.OnScrollChangeListener() {
            @Override
            public void onPageEnd(int l, int t, int oldl, int oldt) {
                mDataBinding.bottomLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageTop(int l, int t, int oldl, int oldt) {
                mDataBinding.bottomLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onScrollChanged(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if ((scrollY - oldScrollY) > 0) {//向下滑动
                    mDataBinding.bottomLayout.setVisibility(View.GONE);
                } else if ((oldScrollY - scrollY) > 0) {//向上滑动
                    mDataBinding.bottomLayout.setVisibility(View.VISIBLE);
                }
                mDataBinding.baseWebView.setAnimationView(mDataBinding.bottomLayout);
            }
        });

        Log.e(TAG, "url:" + url);
        mDataBinding.baseWebView.setWebChromeClient(new WebChromeClient());
        mDataBinding.baseWebView.addJavascriptInterface(jsHook, "NATIVE");
        mDataBinding.baseWebView.loadUrl(url);
    }


    /**
     * 获取文章详情
     */
    private void initData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);
        ServiceManger.getInstance().articleDetail(map, new BaseRequestCallback<SpecialDerailResult>() {
            @Override
            public void onSuccess(SpecialDerailResult response) {
                if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                    SerializableManager.saveSerializable(getContext(), response, cacheKey);
                    showView(response);
                }
            }
        });
    }

    private void showView(SpecialDerailResult response) {
        if (response == null || response.getRETURN_DATA() == null || response.getRETURN_DATA().getInfoDetail() == null) {
            return;
        }
        mDataBinding.plLayout.setTag(response);
        mDataBinding.plTxt.setText(String.valueOf(response.getRETURN_DATA().getInfoDetail().getCommentCount()));

        boolean isSc = response.getRETURN_DATA().getInfoDetail().isWhetherFavorite();
        mDataBinding.scLayout.setTag(response);
        mDataBinding.scImage.setTag(isSc);
        mDataBinding.scImage.setImageResource(isSc ? R.drawable.btn_dblsc_ysc : R.drawable.btn_dblsc_normal);
        mDataBinding.scTxt.setText(String.valueOf(response.getRETURN_DATA().getInfoDetail().getFavoriteCount()));

        boolean isDz = response.getRETURN_DATA().getInfoDetail().isWhetherLike();
        mDataBinding.dzLayout.setTag(response);
        mDataBinding.dzTxt.setText(String.valueOf(response.getRETURN_DATA().getInfoDetail().getLikeCount()));
        mDataBinding.dzImage.setTag(isDz);
        mDataBinding.dzImage.setImageResource(isDz ? R.drawable.btn_dbldz_ydz : R.drawable.btn_dbldz_normal);
        mDataBinding.shape.setTag(response);
    }

    /**
     * 点赞收藏相关操作
     *
     * @param articleId 文章Id
     * @param isComment 0 添加评价，1文章点赞 2 文章取消点赞，3文章收藏，4 取消收藏，5 评论点赞，6取消评论点赞，7删除收藏文件夹
     * @param contextSS 收藏文件夹名，收藏操作时要传 取消收藏时不用
     */
    private void addCommentOrLike(String articleId, String isComment, String contextSS) {
        HashMap<String, String> map = new HashMap<>();
        map.put("articleId", articleId);
        map.put("isComment", isComment);
        if (!contextSS.equals("")) {
            map.put("articleFileName", contextSS);
        }
        ServiceManger.getInstance().zanComment(map, new BaseRequestCallback<LikeBean>() {
            @Override
            public void onSuccess(LikeBean response) {
                if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                    mDataBinding.scTxt.setText(response.getRETURN_DATA().getCollections() + "");
                    mDataBinding.dzTxt.setText(response.getRETURN_DATA().getLikes() + "");
                    return;
                }
                ToastUtil.showToast("失败:" + response.getRETURN_DESC());
            }
        });
    }

    //跳转到评论列表
    private void comment(SpecialDerailResult response) {
        if (!ToolUtils.isLoginStatus(mContext)) {
            startActivity(new Intent(getContext(), LoginActivity.class));
            return;
        }
        if (response != null && !TextUtil.isEmpty(response.getRETURN_DATA().getInfoDetail().getId())) {
            Intent intent = new Intent();
            intent.setClass(getContext(), SpecialCommentActivity.class);
            intent.putExtra("id", response.getRETURN_DATA().getInfoDetail().getId());
            startActivity(intent);
        }
    }

    private void shouCang(SpecialDerailResult response) {
        if (!ToolUtils.isLoginStatus(mContext)) {
            startActivity(new Intent(getContext(), LoginActivity.class));
            return;
        }

        //如果已经收藏，取消收藏
        if (true == (boolean) mDataBinding.scImage.getTag()) {
            addCommentOrLike(id, "4", ""); //取消收藏
            mDataBinding.scImage.setImageResource(R.drawable.btn_dblsc_normal);
            mDataBinding.scImage.setTag(false);
            return;
        }

        CollectionUtils.getInstance().showCollectionDialog(getActivity(),
                CollectionUtils.CollectionTypeEnum.INFORMATION_COLLECTION, id, "文章");
        CollectionUtils.getInstance().setOnCollectionListener(new CollectionUtils.OnCollectionListener() {
            @Override
            public void onCollection(int like, int collections) {
                mDataBinding.scImage.setImageResource(R.drawable.btn_dblsc_ysc);
                mDataBinding.scImage.setTag(true);
                mDataBinding.scTxt.setText(collections + "");
                mDataBinding.dzTxt.setText(like + "");
            }

            @Override
            public void onFailed(String msg) {
                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void dianZan(SpecialDerailResult response) {
        if (!ToolUtils.isLoginStatus(mContext)) {
            startActivity(new Intent(getContext(), LoginActivity.class));
            return;
        }

        if ((boolean) mDataBinding.dzImage.getTag() == true) {
            mDataBinding.dzImage.setTag(false);
            mDataBinding.dzImage.setImageResource(R.drawable.btn_dbldz_normal);
            addCommentOrLike(id, "2", "");
            return;
        }
        mDataBinding.dzImage.setTag(true);
        mDataBinding.dzImage.setImageResource(R.drawable.btn_dbldz_ydz);
        addCommentOrLike(id, "1", "");//1文章点赞
    }


    private void shape(SpecialDerailResult response) {
        String shareUrl = "http://www.baidu.com";
        String sharetitle = "太好了";
        String shareContent = "是真的吗";
        String shareImage = "http://imgsrc.baidu.com/imgad/pic/item/267f9e2f07082838b5168c32b299a9014c08f1f9.jpg";

        if (response != null && response.getRETURN_DATA().getInfoDetail() != null) {
            sharetitle = response.getRETURN_DATA().getInfoDetail().getTitle();
            shareContent = response.getRETURN_DATA().getInfoDetail().getContent();
            shareImage = response.getRETURN_DATA().getInfoDetail().getCoverImg();
        }

        UmengUtils.shareUrl(getActivity(), sharetitle, shareContent, shareUrl, shareImage, R.drawable.logo_ole, new UmengUtils.ShareCallBack() {
            @Override
            public void onStart(UmengUtils.ShareType type) {

            }

            @Override
            public void onResult(UmengUtils.ShareType type) {
                ToastUtil.showToast(type + "分享成功");
            }

            @Override
            public void onError(UmengUtils.ShareType type, Throwable throwable) {
                ToastUtil.showToast(type + "分享出错");
            }

            @Override
            public void onCancel(UmengUtils.ShareType type) {
                ToastUtil.showToast("取消分享");
            }
        });
    }


}
