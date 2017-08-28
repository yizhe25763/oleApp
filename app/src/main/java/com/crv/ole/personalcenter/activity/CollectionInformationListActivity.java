package com.crv.ole.personalcenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.information.activity.SpecialDetailActivity;
import com.crv.ole.information.activity.ZzActivity;
import com.crv.ole.net.RequestData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.personalcenter.adapter.CollectionInformationListAdapter;
import com.crv.ole.personalcenter.model.CollectionFolderListData;
import com.crv.ole.personalcenter.requestmodel.RequestCollectionInformationData;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.crv.sdk.utils.PreferencesHelper;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 个人中心 - 我的收藏 - 资讯列表
 *
 * @author wj_wsf
 */

public class CollectionInformationListActivity extends BaseActivity {
    @BindView(R.id.collection_list_layout)
    PullToRefreshLayout collection_list_layout;

    @BindView(R.id.collection_list)
    ListView collection_list;

    private String folderName;
    private List<CollectionFolderListData.FolderData> dataList;
    private CollectionInformationListAdapter collectionInformationListAdapter;
    private boolean isRefresh = false;
    private int pageNum = 1, pageLimit = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_list);
        ButterKnife.bind(this);
        initTitleViews();
        initBackButton();
        folderName = getIntent().getStringExtra("folder_name");
        setBarTitle(folderName);

        collection_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (TextUtils.equals("杂志", dataList.get(position).getArticleType())) {
                    startActivity(new Intent(CollectionInformationListActivity.this,
                            ZzActivity.class)
                            .putExtra("id", dataList.get(position).getArticleId()));
                } else
                    startActivity(new Intent(CollectionInformationListActivity.this,
                            SpecialDetailActivity.class)
                            .putExtra("id", dataList.get(position).getArticleId()));
            }
        });

        collection_list_layout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                isRefresh = true;
                pageNum = 1;
                getData();
            }

            @Override
            public void loadMore() {
                pageNum += 1;
                getData();
            }
        });

        getData();
    }

    private void showData(List<CollectionFolderListData.FolderData> lists) {
        if (lists == null) {
            ToastUtil.showToast("暂无数据");
            return;
        }
        if (collectionInformationListAdapter == null || dataList == null) {
            dataList = new ArrayList<>();
            dataList.addAll(lists);

            collectionInformationListAdapter = new CollectionInformationListAdapter(mContext, dataList);
            collection_list.setAdapter(collectionInformationListAdapter);
        } else {
            dataList.clear();
            dataList.addAll(lists);

            collectionInformationListAdapter.notifyDataSetChanged();
        }

        if (isRefresh) {
            collection_list_layout.finishRefresh();
            isRefresh = false;
        } else {
            if (pageNum != 1)
                collection_list_layout.finishLoadMore();
        }

    }

    private void getData() {
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.INFORMATION_COLLECTION_LIST_URL_ID);
        requestData.setREQUEST_DATA(new RequestCollectionInformationData(folderName, pageNum, pageLimit));

        ServerApi.request(false, requestData, CollectionFolderListData.class,
                new PreferencesHelper(this).getString(OleConstants.KEY.REQUEST_SIGN_KEY))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        Log.i("获取收藏列表开始请求");
                        mDialog.showProgressDialog("加载中……", null);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CollectionFolderListData>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(CollectionFolderListData response) {
                        if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                            showData(response.getRETURN_DATA().getCollectionList());

                        } else {
//                            dismissProgressDialog();
                            ToastUtil.showToast(response.getRETURN_DESC());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        ToastUtil.showToast("获取收藏列表失败");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("获取收藏列表完成");
                        mDialog.dissmissDialog();
                    }
                });
    }

}
