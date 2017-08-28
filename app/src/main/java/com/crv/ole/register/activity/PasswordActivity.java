package com.crv.ole.register.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.login.activity.LoginActivity;
import com.crv.ole.net.BaseResponseData;
import com.crv.ole.register.model.CheckMemberByMobileInfoResultBean;
import com.crv.ole.register.model.RegisterMemberInfoResultBean;
import com.crv.ole.utils.FinishUtils;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.PreferencesUtils;
import com.crv.ole.utils.ToastUtil;
import com.crv.ole.utils.ToolUtils;
import com.crv.sdk.utils.TextUtil;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置密码
 */
public class PasswordActivity extends BaseActivity {
    @BindView(R.id.password_edt)
    EditText passwordEdt;

    @BindView(R.id.password_config_edt)
    EditText passwordConfigEdt;

    @BindView(R.id.password_delete_iv)
    ImageView delete_iv;

    @BindView(R.id.finish_btn)
    Button finishBtn;

    private String mPassword;//密码
    private String mConfirmPassword;//确认密码
    private String mobilePhone;
    private String validateCode;

    private String type;    //判断是注册用户设置密码还是老用户找回密码


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        ButterKnife.bind(this);
        FinishUtils.getInstance().addActivity(this);
        initTitleViews();
        title_name_tv.setText("设置密码");
        initBackButton();
        mobilePhone = getIntent().getStringExtra("mobilePhone");
        validateCode = getIntent().getStringExtra("validateCode");
        type = getIntent().getStringExtra("type");

        if (TextUtils.equals(type, "register")) {
            passwordEdt.setHint("请输入密码");
            passwordConfigEdt.setHint("请再次输入密码");
        }
        initLister();
    }

    private void initLister() {

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String password = passwordEdt.getText().toString().trim();
                String passwordConfig = passwordConfigEdt.getText().toString().trim();

                if (!TextUtil.isEmpty(password) || !TextUtil.isEmpty(passwordConfig)) {
                    delete_iv.setVisibility(View.VISIBLE);
                } else {
                    delete_iv.setVisibility(View.INVISIBLE);
                }

                if ((ToolUtils.getWordCount(password) != password.length()) || (ToolUtils.getWordCount(passwordConfig) != passwordConfig.length())) {
                    finishBtn.setEnabled(false);
                    ToastUtil.showToast("密码不能含有汉字");
                    return;
                }

                if ((password.length() >= 6 && password.length() <= 20) && (passwordConfig.length() >= 6 && passwordConfig.length() <= 20)) {
                    finishBtn.setEnabled(true);
                } else {
                    finishBtn.setEnabled(false);
                }

            }
        };
        passwordEdt.addTextChangedListener(watcher);
        passwordConfigEdt.addTextChangedListener(watcher);
    }

    @OnClick({R.id.finish_btn, R.id.password_delete_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.finish_btn:
                configPassword();
                break;
            case R.id.password_delete_iv:
                passwordEdt.setText("");
                passwordConfigEdt.setText("");
                break;
        }
    }

    /**
     * 该方法主要使用正则表达式来判断字符串中是否包含字母
     *
     * @param cardNum 待检验的
     * @return 返回是否包含
     */
    private boolean isContainChar(String cardNum) {
        String regex = ".*[a-zA-Z]+.*";
        Matcher m = Pattern.compile(regex).matcher(cardNum);
        return m.matches();
    }

    private boolean isContainNum(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (Character.isDigit(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }


    //设置密码提交
    private void configPassword() {
        mPassword = passwordEdt.getText().toString().trim();
        mConfirmPassword = passwordConfigEdt.getText().toString().trim();
        if (!mPassword.equals(mConfirmPassword)) {    //密码验证通过，请求接口进行处理
            ToastUtil.showToast("两次填写密码不一致");
            return;
        } else if (!isContainChar(mPassword) || !isContainNum(mPassword) || mPassword.length() < 6 || mPassword.length() > 20) {
            ToastUtil.showToast("密码长度须为6-20位，且包含英文和数字");
            return;
        }
        if (type.equals("findPwd")) {    //找回密码
            ressetPwd();
        } else if (type.equals("register")) { //新注册用户设置密码
            registerMember(mobilePhone, passwordEdt.getText().toString().trim());
        }

    }

    /**
     * 重新设置密码
     */
    private void ressetPwd() {
        HashMap<String, String> map = new HashMap<>();
        map.put("validatingCode", validateCode);
        map.put("phone", mobilePhone);
        map.put("newPwd", mConfirmPassword);
        ServiceManger.getInstance().smsResetPwd(map, new BaseRequestCallback<BaseResponseData>() {

            @Override
            public void onStart() {
                showProgressDialog("修改中...", null);
            }

            @Override
            public void onSuccess(BaseResponseData response) {
                if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                    ToastUtil.showToast("密码修改成功");
                    startActivity(new Intent(mContext, LoginActivity.class).
                            setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                } else {
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
            }
        });
    }

    /**
     * 注册会员
     */
    private void registerMember(String mobile, String pwd) {
        HashMap<String, String> map = new HashMap<>();
        map.put("mobilePhone", mobile);
        map.put("password", pwd);
        map.put("validateCode", validateCode);
        ServiceManger.getInstance().registerMember(map, new BaseRequestCallback<RegisterMemberInfoResultBean>() {
            @Override
            public void onStart() {
                showProgressDialog("请求中...", null);
            }

            @Override
            public void onSuccess(RegisterMemberInfoResultBean response) {
                String desc = response.getRETURN_DESC();
                if (TextUtils.equals("注册成功", desc)) {
                    //  立马进行登录操作
                    if (response.getRETURN_DATA() != null && response.getRETURN_DATA().getToken() != null) {
                        String tokenStr = response.getRETURN_DATA().getToken();
                        PreferencesUtils.getInstance().put(OleConstants.KEY.REQUES_TOKEN, tokenStr);
                        autoLogin();
                    }
                    checkMemberInfoByMobile();
                } else if (TextUtils.equals("用户已存在，请直接登录", response.getRETURN_DESC())) {
                    ToastUtil.showToast(response.getRETURN_DESC());
                    startActivity(new Intent(mContext, LoginActivity.class).
                            setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                } else {
                    ToastUtil.showToast(desc);
                }
            }

            @Override
            public void onEnd() {
                dismissProgressDialog();
            }

            @Override
            public void onFailed(String msg) {
                ToastUtil.showToast("请求出错，请稍后再试");
            }
        });
    }

    /**
     * 根据手机号校验是否注册会员
     */
    private void checkMemberInfoByMobile() {
        HashMap<String, String> map = new HashMap<>();
        map.put("mobile", mobilePhone);
        map.put("focusshopid", "123");
        map.put("channel", "ole");
        ServiceManger.getInstance().checkMemberByMobile(map, new BaseRequestCallback<CheckMemberByMobileInfoResultBean>() {
            @Override
            public void onStart() {
                showProgressDialog("请求中...", null);
            }

            @Override
            public void onSuccess(CheckMemberByMobileInfoResultBean response) {
                if (response.getRETURN_DATA() != null && response.getRETURN_DATA().getFlag() != null) {
                    FinishUtils.getInstance().finishActivityList();
                    if (response.getRETURN_DATA().getFlag().equals("1")) {    // 存在
                        startActivity(new Intent(mContext, RegisterSuccessActivity.class)
                                .putExtra("mobile", mobilePhone));
                    } else if (response.getRETURN_DATA().getFlag().equals("0")) {
                        startActivity(new Intent(mContext, RegistSuccessActivity.class)
                                .putExtra("mobile", mobilePhone));
                    }
                }
            }

            @Override
            public void onEnd() {
                dismissProgressDialog();
            }

            @Override
            public void onFailed(String msg) {
                super.onFailed(msg);
                Log.i("请求出错" + msg);
                ToastUtil.showToast("请求出错，请稍后再试");
            }
        });
    }
}
