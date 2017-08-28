package com.crv.ole.personalcenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.utils.FinishUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 会员卡 - 会员卡介绍页面
 * Created by Fairy on 2017/7/12.
 */

public class CardIntroduceActivity extends BaseActivity {
    @BindView(R.id.cardIntro_btn)
    Button cardIntro_btn;
    @BindView(R.id.cardIntro_img)
    ImageView cardIntro_img;

    /**
     * 分别可以跳转至华润通激活和开卡绑定页面，因此设置此加以区别
     */
    private String source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_introduce);
        ButterKnife.bind(this);
        FinishUtils.getInstance().addActivity(this);
        initTitleViews();
        initBackButton();
        source = getIntent().getStringExtra("source");
        if(TextUtils.equals(source, "active")) {
            setBarTitle(R.string.vipCard_activeHRT);
            cardIntro_btn.setText(R.string.vipCard_active);
            cardIntro_img.setBackground(getResources().getDrawable(R.drawable.active2));
        }else if(TextUtils.equals(source, "bind")){
            setBarTitle(R.string.vipCard_activeBind);
            cardIntro_btn.setText(R.string.vipCard_activeBind);
            cardIntro_img.setBackground(getResources().getDrawable(R.drawable.active1));
        }
    }

    @OnClick({R.id.cardIntro_btn})
    public void onClick(View v){
        super.onClick(v);
        switch (v.getId()){
            case R.id.cardIntro_btn:
                if(TextUtils.equals(source, "active")) {
                    startActivityWithAnim(ActiveHRTActivity.class);
                    cardIntro_btn.setText(R.string.vipCard_active);
                }else if(TextUtils.equals(source, "bind")){ //普通用户开通绑定会员卡
                    startActivity(new Intent(mContext, ActiveBindActivity.class)
                            .putExtra("source", "bind"));
                }
                break;
        }
    }
}
