package com.crv.ole.trial.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.trial.adapter.TrialListAdapter;
import com.crv.ole.trial.model.TrialItemData;
import com.crv.ole.trial.model.TrialItemResponse;
import com.crv.ole.utils.OleConstants;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.extras.recyclerview.PullToRefreshRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 申请试用过期记录界面(审核不通过)
 * Created by zhangbo on 2017/8/17.
 */

public class TrialOverdueListActivity extends BaseActivity {

    @BindView(R.id.trial_list)
    PullToRefreshRecyclerView trial_list;

    private TrialListAdapter adapter;

    private List<TrialItemData> list = new ArrayList<>();

    private Map<String, String> map = new HashMap<>();

    private int pageNum = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trial_list_layout);
        ButterKnife.bind(this);
        initTitleViews();
        initUI();
        initParams();
        initEvent();
        pageNum = 1;
        initDate(true);
    }


    private void initUI() {
        adapter = new TrialListAdapter(this, list);
        trial_list.setMode(PullToRefreshBase.Mode.BOTH);
        RecyclerView recyclerView = trial_list.getRefreshableView();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        title_name_tv.setText(getString(R.string.trial));
    }

    private void initEvent() {
        title_back_layout.setOnClickListener(this);
        trial_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                pageNum = 1;
                initDate(true);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                pageNum++;
                initDate(false);
            }
        });
    }


    private void initParams() {
        map.put("isHistory", "1");
        map.put("limit", "20");
        map.put("state", "");
    }


    //服务器获取数据
    private void initDate(boolean isRefresh) {
        map.put("pageNum", pageNum + "");
        ServiceManger.getInstance().appraiseList(map, new BaseRequestCallback<TrialItemResponse>() {
            @Override
            public void onStart() {
                super.onStart();
                showProgressDialog(R.string.waiting);
            }

            @Override
            public void onEnd() {
                super.onEnd();
                dismissProgressDialog();
            }

            @Override
            public void onSuccess(TrialItemResponse data) {
                if (OleConstants.REQUES_SUCCESS.equals(data.getRETURN_CODE())) {
                    if (data.getRETURN_DATA() != null && data.getRETURN_DATA().getList() != null && data.getRETURN_DATA().getList().size() > 0) {
                        if (isRefresh) {
                            list.clear();
                        }
                        list.addAll(data.getRETURN_DATA().getList());
                        adapter.notifyDataSetChanged();
                    } else {
                        if (isRefresh) {
                            list.clear();
                        }
                        adapter.notifyDataSetChanged();
                    }
                    trial_list.onRefreshComplete();
                }
            }
        });
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
