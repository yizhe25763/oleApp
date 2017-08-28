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
 * 直播提醒页面
 */
public class LiveActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_layout);

        initTitleViews();
        initBackButton();
        setBarTitle("直播提醒");
    }

}
