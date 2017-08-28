package com.crv.ole.home.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.crv.ole.R;
import com.crv.ole.home.adapter.SpecialListAdapter;
import com.crv.ole.information.activity.SpecialDeatail1Activity2;
import com.crv.ole.information.model.ContentID;
import com.crv.ole.information.model.FindResult;
import com.crv.ole.information.model.ListResult;
import com.crv.ole.information.requestmodel.ListBeanRequest;
import com.crv.ole.net.RequestData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.utils.OleConstants;
import com.crv.sdk.fragment.BaseFragment;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 态度专题频道
 */
public class SpecialFragment extends BaseFragment {
    private Unbinder unbinder;

    @BindView(R.id.fragment_market_special_layout)
    PullToRefreshLayout fragment_market_special_layout;
    @BindView(R.id.fragment_market_special_list)
    ListView fragment_market_special_list;
    private String spID;
    private List<ListResult.RETURNDATABean.InformationBean> datas = new ArrayList<>();
    private List<FindResult.RETURNDATABean.ColumnListBean.ImagesBean> mImagesList = new ArrayList<>();

    public static SpecialFragment getInstance() {
        SpecialFragment fragment = new SpecialFragment();
        return fragment;
    }

    public void setLists(List<FindResult.RETURNDATABean.ColumnListBean.ImagesBean> images) {
        this.mImagesList = images;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_market_special_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        fragment_market_special_layout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                getContentList(ContentID.SPID);
            }

            @Override
            public void loadMore() {
                fragment_market_special_layout.finishLoadMore();

            }

        });


        return view;
    }

    /**
     * 获取专题列表
     */
    public void getContentList(String id) {
        datas.clear();
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.LIST);
        ListBeanRequest bean = new ListBeanRequest();
        bean.setColumnId(id);
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
                        if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                            mDialog.dissmissDialog();
                            for (int i = 0; i < response.getRETURN_DATA().getTotal(); i++) {
                                ListResult.RETURNDATABean.InformationBean beans = new ListResult.RETURNDATABean.InformationBean();
                                beans.setAuthor(response.getRETURN_DATA().getInformation().get(i).getDescriptions());
                                beans.setTitle(response.getRETURN_DATA().getInformation().get(i).getTitle());
                                beans.setFavoriteCount(response.getRETURN_DATA().getInformation().get(i).getFavoriteCount());
                                beans.setLikeCount(response.getRETURN_DATA().getInformation().get(i).getLikeCount());
                                beans.setCoverImg(response.getRETURN_DATA().getInformation().get(i).getCoverImg());
                                beans.setId(response.getRETURN_DATA().getInformation().get(i).getId());
                                beans.setIconUrl(mImagesList.get(0).getUrl());
                                datas.add(beans);
                            }
                            if (datas != null) {
                                fragment_market_special_list.setAdapter(new SpecialListAdapter(mContext, datas));
                                fragment_market_special_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                        Intent intent = new Intent();
                                        intent.setClass(getActivity(), SpecialDeatail1Activity2.class);
                                        intent.putExtra("id", response.getRETURN_DATA().getInformation().get(i).getId());
                                        startActivity(intent);
                                        //                                        ToastUtil.showToast(response.getRETURN_DATA().getInformation().get(i).getId());
                                        //                                        Intent intent = new Intent();
                                        //                                        intent.setClass(getActivity(), SpecialDetailActivity.class);
                                        //                                        intent.putExtra("id", response.getRETURN_DATA().getInformation().get(i).getId());
                                        //                                        startActivity(intent);

                                        //                                        Intent intent = new Intent(getActivity(), ThematicDetailsActivity.class);
                                        //                                        intent.putExtra("id", response.getRETURN_DATA().getInformation().get(i).getId());
                                        //                                        startActivity(intent);

//                                        ArrayList<CharSequence> list = new ArrayList<>();
//                                        for (ListResult.RETURNDATABean.InformationBean informationBean : response.getRETURN_DATA().getInformation()) {
//                                            list.add(informationBean.getId());
//                                        }
//                                        Intent intent = new Intent(getActivity(), ThematicDetailsActivity.class);
//                                        intent.putCharSequenceArrayListExtra(OleConstants.BundleConstant.ARG_PARAMS_0, list);
//                                        intent.putExtra(OleConstants.BundleConstant.ARG_PARAMS_1, i);
//                                        startActivity(intent);
                                    }


                                });
                                isLoad = true;
                            }
                            fragment_market_special_layout.finishRefresh();
                        } else {
                            fragment_market_special_layout.finishRefresh();

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mDialog.dissmissDialog();
                        fragment_market_special_layout.finishRefresh();

                    }

                    @Override
                    public void onComplete() {
                        mDialog.dissmissDialog();
                        fragment_market_special_layout.finishRefresh();

                    }
                });

    }

    @Override
    public void showThisPage() {
        super.showThisPage();
        if (!isLoad)
            getContentList(ContentID.SPID);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        //        if (!isLoad)
        //            getContentList(ContentID.SPID);
        super.onResume();
    }

}
