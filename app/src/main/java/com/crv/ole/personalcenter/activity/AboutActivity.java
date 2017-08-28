package com.crv.ole.personalcenter.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.sdk.utils.ManifestUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 个人中心 - 设置 - 关于页面
 */

public class AboutActivity extends BaseActivity {
    @BindView(R.id.versionTv)
    TextView versionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        initTitleViews();
        initBackButton();
        setBarTitle(R.string.version_title);

        String versionName = ManifestUtils.getVersionName(mContext);
        if(!TextUtils.isEmpty(versionName)){
            versionTv.setText("版本v"+versionName);
        }
    }
}
