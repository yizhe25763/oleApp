package com.crv.ole.personalcenter.activity;

import android.os.Bundle;
import android.view.View;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人中心 - 帮助与反馈页面
 */

public class HelpFeedbackActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_feedback);
        ButterKnife.bind(this);
        initTitleViews();
        initBackButton();
        setBarTitle(R.string.help_title);
    }

    @OnClick({R.id.help_call, R.id.help_words})
    public void onClick(View v){
        super.onClick(v);
        switch (v.getId()){
            case R.id.help_call:
//                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("12345678")));
                break;
            case R.id.help_words:
                startActivityWithAnim(WordsActivity.class);
                break;
        }
    }
}
