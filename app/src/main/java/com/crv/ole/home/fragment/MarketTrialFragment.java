package com.crv.ole.home.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.crv.ole.R;
import com.crv.ole.home.activity.MainActivity;
import com.crv.ole.home.adapter.SYAdapter;
import com.crv.ole.home.model.DataBean;
import com.crv.ole.home.model.SYBean;
import com.crv.ole.information.model.ContentID;
import com.crv.ole.net.RequestData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.trial.activity.TrialInfoActivity;
import com.crv.ole.utils.OleConstants;
import com.crv.sdk.fragment.BaseFragment;
import com.crv.sdk.utils.StringUtils;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 商城试用频道
 */
public class MarketTrialFragment extends BaseFragment {


    private View mViews;
    private List<DataBean> titleList = new ArrayList<>();
    private ListView listView;
    private SYAdapter pullToRefreshAdapter;
    private PullToRefreshLayout fragment_market_special_layout;


    public static MarketTrialFragment getInstance() {
        MarketTrialFragment fragment = new MarketTrialFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mViews = inflater.inflate(R.layout.fragment_market_trial_layout, container, false);
        listView = (ListView) mViews.findViewById(R.id.list);
        fragment_market_special_layout = (PullToRefreshLayout) mViews.findViewById(R.id.fragment_market_special_layout);
        getList();
        initListener();

        return mViews;
    }

    /**
     * 获取试用列表
     */
    private void getList() {
        titleList.clear();
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.GET_MARKET_TEMPLATE);
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("pageId", "tryOut3");
        requestMap.put("rappId", "oleMarketTemplate");
        requestData.setREQUEST_DATA(requestMap);
        ServerApi.request(false, requestData, SYBean.class, OleConstants.sign)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mDialog.showProgressDialog("加载中……", null);

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SYBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(SYBean response) {
                        mDialog.dissmissDialog();
                        if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                            DataBean beans = null;
                            for (int i = 0; i < response.getRETURN_DATA().size(); i++) {
                                for (int j = 0; j < response.getRETURN_DATA().get(i).getOleFloors_i().size(); j++) {
                                    if (j == 0) {
                                        beans = new DataBean();
                                        beans.setUnitName(response.getRETURN_DATA().get(i).getOleFloors_h());
                                        beans.setName("");
                                        titleList.add(beans);
                                    }
                                    beans = new DataBean();
                                    beans.setUnitName(response.getRETURN_DATA().get(i).getOleFloors_h());
                                    beans.setName(response.getRETURN_DATA().get(i).getOleFloors_i().get(j).getParh());
                                    beans.setImageUrl(response.getRETURN_DATA().get(i).getOleFloors_i().get(j).getImgUrl());
                                    beans.setParp(response.getRETURN_DATA().get(i).getOleFloors_i().get(j).getParp());
                                    beans.setId(response.getRETURN_DATA().get(i).getOleFloors_i().get(j).getId());
                                    beans.setLinkTo(response.getRETURN_DATA().get(i).getOleFloors_i().get(j).getLinkTo());
                                    titleList.add(beans);
                                }
                            }
                            pullToRefreshAdapter = new SYAdapter(getActivity(), titleList);
                            listView.setAdapter(pullToRefreshAdapter);
                            pullToRefreshAdapter.notifyDataSetChanged();
                        }
                        fragment_market_special_layout.finishRefresh();
                        isLoad = true;

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

    //    @Override
    //    public void showThisPage() {
    //        super.showThisPage();
    //        if (!isLoad)
    //            getList();
    //    }

    private void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!StringUtils.isNullOrEmpty(titleList.get(position).getName())) {
                    Intent intent = new Intent(getContext(), TrialInfoActivity.class);
                    intent.putExtra("url", titleList.get(position).getImageUrl());
                    intent.putExtra("title", titleList.get(position).getName());
                    intent.putExtra("activeId", titleList.get(position).getLinkTo());
                    startActivity(intent);
                }
            }
        });
        fragment_market_special_layout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                getList();
            }

            @Override
            public void loadMore() {
                fragment_market_special_layout.finishLoadMore();

            }

        });
    }


}
