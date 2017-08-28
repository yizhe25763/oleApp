package com.crv.ole.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.crv.ole.BaseApplication;
import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.login.model.LoginResultBean;
import com.crv.ole.register.activity.RegisterActivity;
import com.crv.ole.utils.DESEncryptUtil;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.PreferencesUtils;
import com.crv.ole.utils.ToastUtil;
import com.crv.ole.utils.ToolUtils;
import com.crv.ole.utils.UmengUtils;
import com.crv.ole.utils.UmengUtils.LogInType;
import com.crv.sdk.utils.StringUtils;
import com.google.gson.Gson;

import net.arnx.jsonic.JSON;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录页面
 */
public class LoginActivity extends BaseActivity {

    //返回
    @BindView(R.id.back_txt)
    TextView mBackTxt;
    //用户名
    @BindView(R.id.username_edt)
    EditText mUsernameEdt;
    //密码
    @BindView(R.id.password_edt)
    EditText mPasswordEdt;
    //密码可见
    @BindView(R.id.password_hint_imgbtn)
    ImageView mPasswordHintBtn;
    //新用户注册
    @BindView(R.id.quick_login_txt)
    TextView mQuickLoginTxt;
    //忘记密码
    @BindView(R.id.forget_password_txt)
    TextView mForgetPasswordTxt;
    //登录
    @BindView(R.id.login_btn)
    Button mLoginBtn;
    //微信登录
    @BindView(R.id.wechat_btn)
    ImageButton mWechatBtn;
    @BindView(R.id.tencent_btn)
    ImageButton tencentBtn;
    @BindView(R.id.sina_btn)
    ImageButton sinaBtn;

    private String mUserName;//用户名
    private String mPassword;//密码
    private Boolean isHint = false;//密码是否可见

    Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
        intent = getIntent();
        String jscal = intent.getStringExtra("jscallback");
        if (jscal!=null){
            Log.i("jscallback="+jscal);
        }else{
            Log.i("jscallback为null");
        }
    }

    /**
     * 初始化View
     */
    private void initView() {
        mUsernameEdt.setText(mPreferencesHelper.getString(OleConstants.KEY.ACCOUNT_KEY, ""));
        mPasswordEdt.setText(mPreferencesHelper.getString(OleConstants.KEY.PWD_KEY, ""));
        mUsernameEdt.setSelection(mUsernameEdt.getText().length());
        mPasswordEdt.setSelection(mPasswordEdt.getText().length());
    }

    /**
     * 登录
     */
    private void login(LogInType type) {
        if (type == LogInType.ORIGIN) {
            String mUsername = mUsernameEdt.getText().toString().trim();
//            if (!StringUtils.isMobile(mUsername)) {
//                ToastUtil.showToast("请输入正确的手机号码");
//                return;
//            }

            String mPassword = mPasswordEdt.getText().toString().trim();
            if (!StringUtils.isNullOrEmpty(mUsername) && !StringUtils.isNullOrEmpty(mPassword)) {
                HashMap<String, String> map = new HashMap<>();
                map.put("loginId", mUsername);
                map.put("password", mPassword);
                ServiceManger.getInstance().logIn(map, new BaseRequestCallback<LoginResultBean>() {
                    @Override
                    public void onStart() {
                        showProgressDialog("登录中...", null);
                    }

                    @Override
                    public void onSuccess(LoginResultBean response) {
                        if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                            String json = new Gson().toJson(response);
                            Log.i("登录成功:" + json);
                            //记录最后一次登录事件
                            long time = System.currentTimeMillis();
                            PreferencesUtils.getInstance().put(OleConstants.KEY_LATELY_LOGIN_TIME, time);
                            mPreferencesHelper.put(OleConstants.KEY.ACCOUNT_KEY, mUsername);
                            mPreferencesHelper.put(OleConstants.KEY.PWD_KEY, mPassword);
                            mPreferencesHelper.put(OleConstants.KEY.LOGIN_STATUS, true);
                            mPreferencesHelper.put(OleConstants.KEY.REQUES_TOKEN, response.getRETURN_DATA().getToken());
                            mPreferencesHelper.put(OleConstants.KEY.USER_ID, response.getRETURN_DATA().getUserId());
                            EventBus.getDefault().post(OleConstants.USER_CENTER_RELOAD);
                            if (intent.getStringExtra("jscallback") != null) {
                                setResult(OleConstants.noticeJsLogInSuccess, intent);
                            }
                            finish();
                        } else {
                            mPreferencesHelper.remove(OleConstants.KEY.LOGIN_STATUS);
                            mPreferencesHelper.remove(OleConstants.KEY.REQUES_TOKEN);
                            mPreferencesHelper.remove(OleConstants.KEY.USER_ID);
                            BaseApplication.getInstance().setRequestCookieParams(null);
                            ToastUtil.showToast(response.getRETURN_DESC());
                        }
                    }

                    @Override
                    public void onEnd() {
                        dismissProgressDialog();
                    }

                    @Override
                    public void onFailed(String msg) {
                        dismissProgressDialog();
                        Log.i(msg);
                    }

                });
            } else {
                ToastUtil.showToast("请输入用户名和密码");
            }
        } else {
            umengLogIn(type);
        }
    }

    /**
     * 友盟三方登录
     *
     * @param type 登录类型
     */
    private void umengLogIn(LogInType type) {

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
    private void logInBind(LogInType type, Map<String, String> mapInfo) {
        Log.i("第三方登录信息:"+mapInfo.toString());

        Info info = new Info(mapInfo.get("openid"), mapInfo.get("name"), mapInfo.get("iconurl"), type);
        if (type.equals(LogInType.SINA)){
            info = new Info(mapInfo.get("id"), mapInfo.get("name"),mapInfo.get("avatar_hd"),type);
        }
        String iv = ToolUtils.getRandomString(8);
        String key = OleConstants.DES_DECODE_KEY;
        HashMap<String, String> map = new HashMap<>();
        map.put("iv", iv);
        map.put("data", DESEncryptUtil.encSign(key, iv, JSON.encode(info)));

        ServiceManger.getInstance().logInOther(map, new BaseRequestCallback<LoginResultBean>() {
            @Override
            public void onSuccess(LoginResultBean response) {
                if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                    String json = new Gson().toJson(response);
                    Log.i("三方登录成功:" + json);
                    //记录最后一次登录事件
                    long time = System.currentTimeMillis();
                    PreferencesUtils.getInstance().put(OleConstants.KEY_LATELY_LOGIN_TIME, time);
                    mPreferencesHelper.put(OleConstants.KEY.LOGIN_STATUS, true);
                    mPreferencesHelper.put(OleConstants.KEY.REQUES_TOKEN, response.getRETURN_DATA().getToken());
                    mPreferencesHelper.put(OleConstants.KEY.USER_ID, response.getRETURN_DATA().getUserId());
                    EventBus.getDefault().post(OleConstants.USER_CENTER_RELOAD);
                    if (intent.getStringExtra("jscallback") != null) {
                        setResult(OleConstants.noticeJsLogInSuccess, intent);
                    }
                    finish();
                } else {
                    mPreferencesHelper.remove(OleConstants.KEY.LOGIN_STATUS);
                    mPreferencesHelper.remove(OleConstants.KEY.REQUES_TOKEN);
                    mPreferencesHelper.remove(OleConstants.KEY.USER_ID);
                    BaseApplication.getInstance().setRequestCookieParams(null);
                    ToastUtil.showToast(response.getRETURN_DESC());
                }
            }

            @Override
            public void onEnd() {
                dismissProgressDialog();
            }

            @Override
            public void onFailed(String msg) {
                dismissProgressDialog();
                Log.i("三方登录失败"+msg);
            }
        });
    }

    private void toIntent(Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, cls);
        startActivity(intent);
    }


    public static class Info {
        public String openid;
        public String nickName;
        public String logo;
        public String type;

        public Info(String openid, String nickName, String logo, LogInType type) {
            this.openid = openid;
            this.nickName = nickName;
            this.logo = logo;
            if (LogInType.WECHAT.equals(type)) {
                this.type = "weixin";
            } else if (LogInType.QQ.equals(type)) {
                this.type = "qq";
            } else if (LogInType.SINA.equals(type)) {
                this.type = "sina";
            }
        }
    }

    @OnClick({R.id.back_txt, R.id.password_hint_imgbtn, R.id.login_btn, R.id.wechat_btn, R.id.forget_password_txt, R.id.quick_login_txt, R.id.tencent_btn, R.id.sina_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.quick_login_txt:
                toIntent(RegisterActivity.class);
                finish();
                break;
            case R.id.forget_password_txt://找回密码
                toIntent(RetrievePwdActivity.class);
                break;
            case R.id.back_txt://返回
                LoginActivity.this.finish();
                break;
            case R.id.password_hint_imgbtn://密码可见
                if (isHint) {
                    //设置EditText文本为可见的
                    mPasswordEdt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mPasswordHintBtn.setBackground(getResources().getDrawable(R.drawable.ic_kjmm_small));

                } else {
                    //设置EditText文本为隐藏的
                    mPasswordEdt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mPasswordHintBtn.setBackground(getResources().getDrawable(R.drawable.ic_kbjmm_small));
                }
                isHint = !isHint;
                mPasswordEdt.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = mPasswordEdt.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
                break;
            case R.id.login_btn://登录
                login(LogInType.ORIGIN);
                break;
            case R.id.wechat_btn://微信登录
                login(LogInType.WECHAT);
                break;
            case R.id.tencent_btn://腾讯登录
                login(LogInType.QQ);
                break;
            case R.id.sina_btn://新浪微博登录
                login(LogInType.SINA);
                break;
        }
    }

}
