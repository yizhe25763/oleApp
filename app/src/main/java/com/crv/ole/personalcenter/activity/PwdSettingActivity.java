package com.crv.ole.personalcenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.home.activity.MainActivity;
import com.crv.ole.net.BaseResponseData;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
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
 * 个人中心 - 设置 - 设置密码页面
 */

public class PwdSettingActivity extends BaseActivity {
    @BindView(R.id.setPwd_pwd)
    EditText pwdEt;
    @BindView(R.id.setPwd_rePwd)
    EditText rePwdEt;
    @BindView(R.id.setPwd_clear)
    ImageView clearIv;
    @BindView(R.id.setPwd_finish)
    Button finishBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_setting);
        ButterKnife.bind(this);
        initTitleViews();
        initBackButton();
        setBarTitle(R.string.password_title);

        initListener();
    }

    private void initListener() {
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String password = pwdEt.getText().toString().trim();
                String passwordConfig = rePwdEt.getText().toString().trim();

                if (!TextUtil.isEmpty(password) || !TextUtil.isEmpty(passwordConfig)) {
                    clearIv.setVisibility(View.VISIBLE);
                } else {
                    clearIv.setVisibility(View.INVISIBLE);
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

        pwdEt.addTextChangedListener(watcher);
        rePwdEt.addTextChangedListener(watcher);

    }

    @OnClick({R.id.setPwd_clear, R.id.setPwd_finish})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.setPwd_clear:
                pwdEt.setText("");
                rePwdEt.setText("");
                break;
            case R.id.setPwd_finish:
                submitPwd();
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


    private void submitPwd() {
        String password = pwdEt.getText().toString();
        if (!password.equals(rePwdEt.getText().toString())) {
            ToastUtil.showToast("亲,两次输入的密码不一致哟!");
            return;
        }
        if (password.length() < 6 || password.length()>20) {
            ToastUtil.showToast("亲,密码长度为6-20位!");
            return;
        }
        if (!isContainChar(password) || !isContainNum(password)) {
            ToastUtil.showToast("亲,密码必须同时包含数字和字母哟!");
            return;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("password", pwdEt.getText().toString());
        map.put("surepassword", rePwdEt.getText().toString());

        ServiceManger.getInstance().resetPwd(map, new BaseRequestCallback<BaseResponseData>() {

            @Override
            public void onStart() {
                showProgressDialog("请求中...", null);
            }
            @Override
            public void onSuccess(BaseResponseData response) {
                if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                    ToastUtil.showToast("设置成功.");
                    mPreferencesHelper.remove(OleConstants.KEY.LOGIN_STATUS);
                    mPreferencesHelper.remove(OleConstants.KEY.USER_ID);
                    mPreferencesHelper.remove(OleConstants.KEY.PWD_KEY);
                    mPreferencesHelper.remove(OleConstants.KEY.ACCOUNT_KEY);
                    mPreferencesHelper.remove(OleConstants.KEY.REQUES_TOKEN);

                    Intent intent = new Intent(mContext, MainActivity.class);
                    startActivity(intent);
                    return;
                }

                ToastUtil.showToast(response.getRETURN_DESC());
            }

            @Override
            public void onEnd() {
                dismissProgressDialog();
            }

            @Override
            public void onFailed(String msg) {
                dismissProgressDialog();
                Log.i("请求出错");
                ToastUtil.showToast("请求出错，请稍后再试");
            }
        });
    }
}
