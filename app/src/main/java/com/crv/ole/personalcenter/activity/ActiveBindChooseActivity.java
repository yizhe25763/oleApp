package com.crv.ole.personalcenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.register.activity.RegisterActivity;
import com.crv.ole.utils.FinishUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人中心 - 会员卡 - 开卡和绑定(绑定会员卡或重新注册)页面
 */

public class ActiveBindChooseActivity extends BaseActivity {
    @BindView(R.id.choose_bindVip_Btn)
    Button bindVipBtn;
    @BindView(R.id.choose_regist_Btn)
    Button registBtn;
    private String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_bind_choose);
        ButterKnife.bind(this);
        FinishUtils.getInstance().addActivity(this);
        initTitleViews();
        initBackButton();
        setBarTitle(R.string.vipCard_activeBind);
        mobile = getIntent().getStringExtra("mobile");
    }

    @OnClick({R.id.choose_bindVip_Btn, R.id.choose_regist_Btn})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.choose_bindVip_Btn:
                startActivity(new Intent(mContext, BindVipCardActivity.class)
                        .putExtra("mobile",mobile));
                break;
            case R.id.choose_regist_Btn:
                startActivityWithAnim(RegisterActivity.class);
                break;
        }
    }
}
