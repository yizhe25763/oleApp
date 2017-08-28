package com.crv.ole.information.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.home.adapter.SpecialListAdapter;
import com.crv.ole.information.model.ContentID;
import com.crv.ole.information.model.ListResult;
import com.crv.ole.information.requestmodel.ListBeanRequest;
import com.crv.ole.net.RequestData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.utils.OleConstants;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 作用描述：文章列表页面
 * 创建者： wj_wsf
 * 创建时间： 2017/8/24 17:11.
 */

public class ArticleListActivity extends BaseActivity {

    private SpecialListAdapter adapter;
    private List<ListResult.RETURNDATABean.InformationBean> dataList = new ArrayList<>();
    private int pageNum = 1, pageTotal;
    private boolean isRefresh = false;

    private ListView article_list_lv;
    private PullToRefreshLayout article_list_refresh_layout;

    private void assignViews() {
        article_list_lv = (ListView) findViewById(R.id.article_list_lv);
        article_list_refresh_layout = (PullToRefreshLayout) findViewById(R.id.article_list_refresh_layout);
        article_list_refresh_layout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                pageNum = 1;
                isRefresh = true;
                getContentList();
            }

            @Override
            public void loadMore() {
                pageNum += 1;
                getContentList();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list_layout);

        assignViews();
        initTitleViews();
        initBackButton();
        setBarTitle(R.string.home_find_tab_2);

        article_list_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayList<CharSequence> list = new ArrayList<>();
                for (ListResult.RETURNDATABean.InformationBean informationBean : dataList) {
                    list.add(informationBean.getId());
                }
                Intent intent = new Intent(ArticleListActivity.this, ThematicDetailsActivity.class);
                intent.putCharSequenceArrayListExtra(OleConstants.BundleConstant.ARG_PARAMS_0, list);
                intent.putExtra(OleConstants.BundleConstant.ARG_PARAMS_1, i);
                startActivity(intent);

            }


        });

        getContentList();
    }

    private void showData() {
        if (adapter == null) {
            adapter = new SpecialListAdapter(mContext, dataList);
            article_list_lv.setAdapter(adapter);
        } else {
            if (isRefresh) {
                dataList.clear();
                isRefresh = false;
                article_list_refresh_layout.finishRefresh();
            } else {
                article_list_refresh_layout.finishLoadMore();
            }
            adapter.notifyDataSetChanged();

        }

        if (pageNum >= pageTotal) {
            article_list_refresh_layout.setCanLoadMore(false);
        } else {
            article_list_refresh_layout.setCanLoadMore(true);
        }
    }

    /**
     * 获取专题列表
     */
    public void getContentList() {
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.LIST);
        ListBeanRequest bean = new ListBeanRequest();
        bean.setColumnId(ContentID.SPID);
        bean.setLimit(100);
        requestData.setREQUEST_DATA(bean);
        ServerApi.request(false, requestData, ListResult.class, OleConstants.sign)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mDialog.showProgressDialog("加载中……", null);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ListResult>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(ListResult response) {
                        mDialog.dissmissDialog();
                        if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                            if (isRefresh) {
                                dataList.clear();
                            }

                            for (int i = 0; i < response.getRETURN_DATA().getTotal(); i++) {
                                ListResult.RETURNDATABean.InformationBean beans = new ListResult.RETURNDATABean.InformationBean();
                                beans.setAuthor(response.getRETURN_DATA().getInformation().get(i).getDescriptions());
                                beans.setTitle(response.getRETURN_DATA().getInformation().get(i).getTitle());
                                beans.setFavoriteCount(response.getRETURN_DATA().getInformation().get(i).getFavoriteCount());
                                beans.setLikeCount(response.getRETURN_DATA().getInformation().get(i).getLikeCount());
                                beans.setCoverImg(response.getRETURN_DATA().getInformation().get(i).getCoverImg());
                                beans.setId(response.getRETURN_DATA().getInformation().get(i).getId());
                                beans.setIconUrl(ContentID.SP_TYPE_IMAGES.get(0).getUrl());
                                dataList.add(beans);
                            }
                            if (dataList != null) {
                                showData();
                            }
                            article_list_refresh_layout.finishRefresh();
                        } else {
                            article_list_refresh_layout.finishRefresh();

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mDialog.dissmissDialog();
                        article_list_refresh_layout.finishRefresh();

                    }

                    @Override
                    public void onComplete() {
                        mDialog.dissmissDialog();
                        article_list_refresh_layout.finishRefresh();

                    }
                });

    }
}
