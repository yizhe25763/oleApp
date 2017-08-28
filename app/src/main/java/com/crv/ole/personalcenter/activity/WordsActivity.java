package com.crv.ole.personalcenter.activity;

import android.os.Bundle;
import android.view.View;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * 个人中心 - 帮助与反馈 - 留言页面
 */

public class WordsActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);
        ButterKnife.bind(this);
        initTitleViews();
        initBackButton();
        setBarTitle(R.string.help_words_title);
        title_iv_1.setVisibility(View.VISIBLE);
        title_iv_1.setText(R.string.help_submit);

    }
}
