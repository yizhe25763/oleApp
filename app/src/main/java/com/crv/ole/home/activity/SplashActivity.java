package com.crv.ole.home.activity;

import android.os.Bundle;

import com.crv.ole.BaseApplication;
import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.home.model.ConfigData;
import com.crv.ole.home.model.ConfigResponse;
import com.crv.ole.home.model.RegionsModel;
import com.crv.ole.login.model.LoginResultBean;
import com.crv.ole.utils.DESEncryptUtil;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.PreferencesUtils;
import com.crv.ole.utils.SerializableManager;
import com.crv.ole.utils.ToastUtil;
import com.crv.ole.view.CustomDiaglog;
import com.crv.sdk.utils.FileUtils;
import com.crv.sdk.utils.NetWorkUtil;
import com.crv.sdk.utils.TextUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;


/**
 * 启动页
 */
public class SplashActivity extends BaseActivity {
    private final String cacheKey = "appConfig";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        FileUtils.createDir(OleConstants.TMP_PATH);
        checkNet();
    }


    /**
     * 监听网络状态变化
     */
    private void checkNet() {
        Flowable.create(new FlowableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(FlowableEmitter<Boolean> e) throws Exception {
                e.onNext(NetWorkUtil.isNetworkAvailable(SplashActivity.this));
            }
        }, BackpressureStrategy.BUFFER)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean value) throws Exception {
                        if (value) {
                            getConfigData();
                        } else {
                            showNotConnectDialog();
                        }
                    }
                });
    }

    /**
     * 展示设置网络dialog
     */
    private void showNotConnectDialog() {
        showAlertDialog("连接失败,请开启您的网络", "确定", "取消", new CustomDiaglog.OnConfimClickListerner() {
            @Override
            public void onCanleClick() {
                finish();
            }

            @Override
            public void OnConfimClick() {
                openNetSettings(null);
                finish();
            }
        });
    }

    /**
     * 获取config配置数据
     */
    private void getConfigData() {
        ServiceManger.getInstance().getConfig(new BaseRequestCallback<ConfigResponse>() {
            @Override
            public void onSuccess(ConfigResponse response) {
                if ("ok".equalsIgnoreCase(response.getCode())) {
                    SerializableManager.saveSerializable(mContext, response, cacheKey);
                    parseData(response);
                    setAppConfigVariable();
                    RegionsModel.syncData(PreferencesUtils.getInstance().getString(OleConstants.KEY.REQUEST_SIGN_KEY), null);
                } else {
                    Log.i("配置没有改变");
                    setAppConfigVariable();
                }
                gotoActivity();
            }

            @Override
            public void onFailed(String msg) {
                super.onFailed(msg);
                Log.i("解析配置信息出错:" + msg);
                ToastUtil.showToast("解析配置信息出错" + msg);
                ConfigResponse configResponse = SerializableManager.readSerializable(mContext, cacheKey);
                parseData(configResponse);
                gotoActivity();
            }
        });
    }

    private void gotoActivity() {
        String token = PreferencesUtils.getInstance().getString(OleConstants.KEY.REQUES_TOKEN);

        if (!TextUtil.isEmpty(token)) {
            autoLogin(token);
        } else {
            jumpToNext();
        }
    }

    private void parseData(ConfigResponse response) {
        if (response == null) {
            return;
        }
        PreferencesUtils.getInstance().put(OleConstants.KEY.CONFIG_VERSION_KEY, response.getVersion());
        String confitStr = DESEncryptUtil.decSign(OleConstants.DES_DECODE_KEY, response.getIv(), response.getData());
        Log.w("config解密后的数据：" + confitStr);

        Type type = new TypeToken<ConfigData>() {
        }.getType();
        ConfigData data = new Gson().fromJson(confitStr, type);
        PreferencesUtils.getInstance().put(OleConstants.KEY.APP_TOKEN_KEY, data.getApiConfig().getAppToken());
        PreferencesUtils.getInstance().put(OleConstants.KEY.ENCRYPT_APP_KEY, data.getSmsConfig().getEncryptAppKey());
        PreferencesUtils.getInstance().put(OleConstants.KEY.REQUEST_URL_KEY, data.getApiConfig().getApiPlatformUrl());
        PreferencesUtils.getInstance().put(OleConstants.KEY.REQUEST_SIGN_KEY, data.getApiConfig().getSign());
        Log.i("返回的sign是:" + data.getApiConfig().getSign());
    }

    private void setAppConfigVariable() {
        OleConstants.appToken = PreferencesUtils.getInstance().getString(OleConstants.KEY.APP_TOKEN_KEY);
        OleConstants.request_url = PreferencesUtils.getInstance().getString(OleConstants.KEY.REQUEST_URL_KEY);
        OleConstants.encryptAppKey = PreferencesUtils.getInstance().getString(OleConstants.KEY.ENCRYPT_APP_KEY);
        OleConstants.sign = PreferencesUtils.getInstance().getString(OleConstants.KEY.REQUEST_SIGN_KEY);

        Log.i("ole_App_Token=" + PreferencesUtils.getInstance().getString(OleConstants.KEY.APP_TOKEN_KEY));
        Log.i("ole_encryptAppKey=" + PreferencesUtils.getInstance().getString(OleConstants.KEY.ENCRYPT_APP_KEY));
        Log.i("ole_sign_Key=" + PreferencesUtils.getInstance().getString(OleConstants.KEY.REQUEST_SIGN_KEY));
    }

    /**
     * 用户自动登录
     */
    private void autoLogin(String token) {
        ServiceManger.getInstance().autoLogIn(token, new BaseRequestCallback<LoginResultBean>() {
            @Override
            public void onSuccess(LoginResultBean response) {
                Log.i("自动登录结果:"+new Gson().toJson(response));
                if (OleConstants.REQUES_SUCCESS.equalsIgnoreCase(response.getRETURN_CODE())) {
                    Log.i("自动登录成功:" + response.toString());
                    //记录最后一次登录事件
                    PreferencesUtils.getInstance().put(OleConstants.KEY_LATELY_LOGIN_TIME, System.currentTimeMillis());
                    PreferencesUtils.getInstance().put(OleConstants.KEY.LOGIN_STATUS, true);
                    PreferencesUtils.getInstance().put(OleConstants.KEY.USER_ID, response.getRETURN_DATA().getUserId());
                    PreferencesUtils.getInstance().put(OleConstants.KEY.LOGIN_ID, response.getRETURN_DATA().getLoginId());
                    jumpToNext();
                }else{
                    onFailed("自动登录失败");
                }

            }

            @Override
            public void onFailed(String msg) {
                Log.i("自动登录失败"+msg);
                PreferencesUtils.getInstance().remove(OleConstants.KEY.LOGIN_STATUS);
                PreferencesUtils.getInstance().remove(OleConstants.KEY.USER_ID);
                BaseApplication.getInstance().setRequestCookieParams(null);
                BaseApplication.getInstance().setUserCenter(null);
                jumpToNext();
            }
        });
    }

    /**
     * 界面跳转
     */
    private void jumpToNext() {
        boolean aleadyShowTutorial = mPreferencesHelper.getBoolean(OleConstants.KEY_IS_SHOW_TUTORIAL, false);
        if (!aleadyShowTutorial) {
            startActivityWithAnim(TutorialActivity.class); //去引导页
            finish();
            return;
        }
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startActivityWithAnim(MainActivity.class);
        finish();
    }
}
