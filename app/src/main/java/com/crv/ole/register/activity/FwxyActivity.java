package com.crv.ole.register.activity;

import android.os.Bundle;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;


/**
 * 服务协议
 */
public class FwxyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fwxy);
        initTitleViews();
        title_name_tv.setText("Ole服务协议");
        initBackButton();
    }
}
