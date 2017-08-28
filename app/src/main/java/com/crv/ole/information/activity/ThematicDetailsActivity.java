package com.crv.ole.information.activity;

import android.os.Bundle;
import android.view.View;

import com.crv.ole.R;
import com.crv.ole.base.BaseAppCompatActivity;
import com.crv.ole.databinding.ActivityThematicDetailsBinding;
import com.crv.ole.utils.OleConstants;
import com.mingle.pulltonextlayout.OnItemSelectListener;
import com.mingle.pulltonextlayout.adapter.PullToNextFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * create by lihongshi 2017/08/26
 * 专题详情
 */
public class ThematicDetailsActivity extends BaseAppCompatActivity {
    private ActivityThematicDetailsBinding mDataBinding;
    private List<String> articleIds;
    private int curPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = (ActivityThematicDetailsBinding) getViewDataBinding();
        articleIds = getIntent().getStringArrayListExtra(OleConstants.BundleConstant.ARG_PARAMS_0);
        curPosition = getIntent().getIntExtra(OleConstants.BundleConstant.ARG_PARAMS_1, 0);
        initData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_thematic_details;
    }

    private void initData() {
        List list = new ArrayList<>();
        for (String id : articleIds) {
            String url = OleConstants.BASE_HOST + "/img/oleH5/dist/index.html#/ThematicDetail?id=" + id;
            list.add(WebViewFragment.newInstant(id, url));
        }
        
        mDataBinding.pullToNextLayout.setAdapter(new PullToNextFragmentAdapter(getSupportFragmentManager(), list), curPosition);
        mDataBinding.pullToNextLayout.setOnItemSelectListener(new OnItemSelectListener() {
            @Override
            public void onSelectItem(int position, View view) {
                curPosition = position;
            }
        });
    }

}
