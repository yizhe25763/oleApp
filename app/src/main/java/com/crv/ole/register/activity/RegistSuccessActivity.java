package com.crv.ole.register.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.home.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登陆 - 注册成功页面 (开卡流程)
 */

public class RegistSuccessActivity extends BaseActivity {
    @BindView(R.id.regist_arrow_iv)
    ImageView regist_arrow_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_success);
        ButterKnife.bind(this);
        initTitleViews();
        initBackButton();
        setBarTitle(R.string.register);
        title_iv_1.setVisibility(View.VISIBLE);
        title_iv_1.setText("跳过");

        Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.regist_arrow_anim);
        regist_arrow_iv.startAnimation(anim);
    }


    @OnClick({R.id.title_iv_1, R.id.regist_next_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_iv_1:
                startActivity(new Intent(mContext, MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
                break;
            case R.id.regist_next_btn:
                String mobile = getIntent().getStringExtra("mobile");
                startActivity(new Intent(mContext, VipRegistActivity.class)
                .putExtra("source", "RegistSuccessActivity")
                .putExtra("mobile", mobile));
                break;
        }
    }
}
