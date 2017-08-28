package com.crv.ole.personalcenter.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.BaseApplication;
import com.crv.ole.home.model.UserCenterData;
import com.crv.ole.login.activity.LoginActivity;
import com.crv.ole.login.model.LoginResultBean;
import com.crv.ole.net.RequestData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.utils.DESEncryptUtil;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.crv.ole.utils.ToolUtils;
import com.crv.ole.utils.UmengUtils;
import com.google.gson.Gson;

import net.arnx.jsonic.JSON;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 个人中心 - 设置 - 第三方账户绑定页面
 */

public class ThirdBindActivity extends BaseActivity {
    private TextView tvWeiXinState, tvWeiBoState, tvQqState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_bind);
        initTitleViews();
        initBackButton();
        setBarTitle(R.string.set_third_item);
        initView();
        initData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_bind_weixin: {
                umengLogIn(UmengUtils.LogInType.WECHAT);
                break;
            }
            case R.id.ll_bind_weibo: {
                umengLogIn(UmengUtils.LogInType.SINA);
                break;
            }
            case R.id.ll_bind_qq: {
                umengLogIn(UmengUtils.LogInType.QQ);
                break;
            }
        }
    }

    private void initView() {
        this.findViewById(R.id.ll_bind_weixin).setOnClickListener(this);
        this.findViewById(R.id.ll_bind_weibo).setOnClickListener(this);
        this.findViewById(R.id.ll_bind_qq).setOnClickListener(this);
        tvWeiXinState = (TextView) this.findViewById(R.id.set_wxState);
        tvWeiBoState = (TextView) this.findViewById(R.id.set_wbState);
        tvQqState = (TextView) this.findViewById(R.id.set_qqState);
    }

    private void initData() {
        UserCenterData.UserCenter userCenter = BaseApplication.getInstance().getUserCenter();
        if (userCenter==null){
            return;
        }
        if (!TextUtils.isEmpty(userCenter.getWeixinOpenId())) {
            tvWeiXinState.setText("已绑定");
        } else {
            tvWeiXinState.setText("未绑定");
        }
        if (!TextUtils.isEmpty(userCenter.getSinaOpenId())) {
            tvWeiBoState.setText("已绑定");
        } else {
            tvWeiBoState.setText("未绑定");
        }
        if (!TextUtils.isEmpty(userCenter.getQqOpenId())) {
            tvQqState.setText("已绑定");
        } else {
            tvQqState.setText("未绑定");
        }
    }


    /**
     * 友盟三方登录
     *
     * @param type 登录类型
     */
    private void umengLogIn(UmengUtils.LogInType type) {

        UmengUtils.login(this, type, new UmengUtils.LogInCallBack() {
            @Override
            public void onStart(UmengUtils.LogInType type) {
                showProgressDialog("登录中...", null);
            }

            @Override
            public void onComplete(UmengUtils.LogInType type, int i, Map<String, String> map) {
                Log.i("登录成功:" + map.toString());
                logInBind(type, map);
            }

            @Override
            public void onError(UmengUtils.LogInType type, int i, Throwable throwable) {
                dismissProgressDialog();
                ToastUtil.showToast("登录失败" + throwable.getMessage());
            }

            @Override
            public void onCancel(UmengUtils.LogInType type, int i) {
                dismissProgressDialog();
                Log.i("登录取消");
            }
        });

    }


    /**
     * 友盟登录和服务器绑定
     *
     * @param type
     */
    private void logInBind(UmengUtils.LogInType type, Map<String, String> mapInfo) {
        RequestData<HashMap<String, String>> requestData = new RequestData<>();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.OTHER_LOGIN);

        LoginActivity.Info info = new LoginActivity.Info(mapInfo.get("openid"), mapInfo.get("name"), mapInfo.get("iconurl"), type);

        if (type.equals(UmengUtils.LogInType.SINA)){
            info = new LoginActivity.Info(mapInfo.get("id"), mapInfo.get("name"),mapInfo.get("avatar_hd"),type);
        }
        String iv = ToolUtils.getRandomString(8);
        String key = OleConstants.DES_DECODE_KEY;

        Log.i("iv随机数是:" + iv);
        Log.i("ivkey:" + key);
        Log.i("ivjson:" + JSON.encode(info));
        Log.i("des加密后:" + DESEncryptUtil.encSign(key, iv, JSON.encode(info)));

        HashMap<String, String> map = new HashMap<>();
        map.put("iv", iv);
        map.put("data", DESEncryptUtil.encSign(key, iv, JSON.encode(info)));
        requestData.setREQUEST_DATA(map);

        ServerApi.request(false, requestData, LoginResultBean.class, mPreferencesHelper.getString(OleConstants.KEY.REQUEST_SIGN_KEY))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        Log.i("开始请求");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResultBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i("onSubscribe");
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(LoginResultBean response) {
                        dismissProgressDialog();
                        if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                            String json = new Gson().toJson(response);
                            Log.i("三方登录成功:" + json);
                            mPreferencesHelper.put(OleConstants.KEY.LOGIN_STATUS, true);
                            mPreferencesHelper.put(OleConstants.KEY.REQUES_TOKEN, response.getRETURN_DATA().getToken());
                            mPreferencesHelper.put(OleConstants.KEY.USER_ID, response.getRETURN_DATA().getUserId());
                            finish();
                        } else {
                            mPreferencesHelper.remove(OleConstants.KEY.LOGIN_STATUS);
                            mPreferencesHelper.remove(OleConstants.KEY.REQUES_TOKEN);
                            mPreferencesHelper.remove(OleConstants.KEY.USER_ID);
                            ToastUtil.showToast(response.getRETURN_DESC());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dismissProgressDialog();
                    }

                    @Override
                    public void onComplete() {
                        dismissProgressDialog();
                    }
                });

    }


}
