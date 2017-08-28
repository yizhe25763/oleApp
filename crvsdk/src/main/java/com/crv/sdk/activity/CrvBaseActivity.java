package com.crv.sdk.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.crv.sdk.R;
import com.crv.sdk.dialog.FragmentDialog;
import com.crv.sdk.dialog.IDialog;
import com.crv.sdk.event.BaseEvent;
import com.crv.sdk.event.OpenNetEvent;
import com.crv.sdk.utils.PreferencesHelper;
import com.crv.sdk.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 2016-04-15.
 */
public abstract class CrvBaseActivity extends AppCompatActivity implements View.OnClickListener {
    protected IDialog mDialog;
    protected Context mContext;
    public PreferencesHelper mPreferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        mDialog = new FragmentDialog(this);
        mContext = this;
        // 添加activity
        mPreferencesHelper = new PreferencesHelper(this);

        // should be in launcher activity, but all app use this can avoid the problem
        if (!isTaskRoot()) {
            Intent intent = getIntent();
            String action = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)) {
                finish();
                return;
            }
        }
    }


    /**
     * 启动时使用FADE IN or OUT 动画
     */
    public void startActivityWithAnim(Class<?> tag) {
        Intent i = new Intent(this, tag);
        startActivityWithAnim(i);
    }

    /**
     * 启动时使用FADE IN or OUT 动画
     */
    public void startActivityWithAnim(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
    }

    public void startActivityForResultWithAnim(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
    }

    /**
     * 打开网络设置
     */
    public void openNetSettings(@Nullable String apiName) {
        try {
            Intent intent = null;
            // 判断手机系统的版本 即API大于10 就是3.0或以上版本
            if (android.os.Build.VERSION.SDK_INT > 10) {
                intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
            } else {
                intent = new Intent();
                ComponentName component = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
                intent.setComponent(component);
                intent.setAction("android.intent.action.VIEW");
            }
            startActivity(intent);
            if (!StringUtils.isNullOrEmpty(apiName)) {
                EventBus.getDefault().post(new OpenNetEvent(apiName));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String event) {
        busEvent(event);
    }

    //子类需要监听的地方需要复写该方法
    public void busEvent(String event) {
    }

    @Override
    public void onClick(View view) {

    }
}
