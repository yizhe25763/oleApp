package com.crv.ole.trial.activity;

import android.os.Bundle;
import android.view.View;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * 试用规则界面
 * Created by zhangbo on 2017/8/16.
 */

public class TrialRuleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trial_rule_layout);
        ButterKnife.bind(this);
        initTitleViews();
        title_name_tv.setText(getString(R.string.trial_rule));
        initEvent();
    }

    private void initEvent() {
        title_back_layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.title_back_layout:
                finish();
                break;
        }
    }
}
