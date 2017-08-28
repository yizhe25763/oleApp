package com.crv.ole.personalcenter.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.net.RequestData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.personalcenter.adapter.CollectionGoodsListAdapter;
import com.crv.ole.personalcenter.model.CollectionGoodsListData;
import com.crv.ole.personalcenter.requestmodel.RequestCollectionGoodsData;
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
 * 个人中心 - 我的收藏 - 商品列表
 *
 * @author wj_wsf
 */

public class CollectionGoodsListActivity extends BaseActivity {
    @BindView(R.id.collection_list_layout)
    PullToRefreshLayout collection_list_layout;

    @BindView(R.id.collection_goods_list)
    GridView collection_list;

    List<CollectionGoodsListData.GoodsData> dataList;
    private String folderId;
    private CollectionGoodsListAdapter collectionGoodsListAdapter;
    private boolean isRefresh = false;
    private int pageNum = 1, pageLimit = 10, pageTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_goods_list);
        ButterKnife.bind(this);
        initTitleViews();
        initBackButton();
        setBarTitle(getIntent().getStringExtra("folder_name"));
        folderId = getIntent().getStringExtra("folder_id");

        collection_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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

    private void showData(List<CollectionGoodsListData.GoodsData> lists) {
        if (lists == null) {
            ToastUtil.showToast("暂无数据");
            return;
        }
        if (collectionGoodsListAdapter == null || dataList == null) {
            dataList = new ArrayList<>();
            dataList.addAll(lists);

            collectionGoodsListAdapter = new CollectionGoodsListAdapter(mContext, dataList);
            collection_list.setAdapter(collectionGoodsListAdapter);
        } else {
            if (isRefresh) {
                collection_list_layout.finishRefresh();
                isRefresh = false;
                dataList.clear();
            } else {
                if (pageNum != 1)
                    collection_list_layout.finishLoadMore();
            }

            dataList.addAll(lists);
            collectionGoodsListAdapter.notifyDataSetChanged();
        }

        if (pageNum >= pageTotal)
            collection_list_layout.setCanLoadMore(false);
        else
            collection_list_layout.setCanLoadMore(true);
    }

    private void getData() {
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.GOODS_COLLECTION_LIST_URL_ID);
        requestData.setREQUEST_DATA(new RequestCollectionGoodsData(folderId, pageNum, pageLimit));

        ServerApi.request(false, requestData, CollectionGoodsListData.class,
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
                .subscribe(new Observer<CollectionGoodsListData>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(CollectionGoodsListData response) {
                        mDialog.dissmissDialog();
                        if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                            pageTotal = response.getRETURN_DATA().getPageTotal();
                            showData(response.getRETURN_DATA().getFavorList());

                        } else {
//                            dismissProgressDialog();
                            ToastUtil.showToast(response.getRETURN_DESC());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mDialog.dissmissDialog();
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
