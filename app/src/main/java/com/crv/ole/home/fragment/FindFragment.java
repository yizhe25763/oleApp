package com.crv.ole.home.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.home.activity.MainActivity;
import com.crv.ole.home.model.TabEntity;
import com.crv.ole.home.view.CommonTabChildLayout;
import com.crv.ole.information.model.ContentID;
import com.crv.ole.information.model.FindResult;
import com.crv.ole.net.NullBean;
import com.crv.ole.net.RequestData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.search.activity.FindSearchActivity;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.sdk.fragment.BaseFragment;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
 * 首页态度频道
 */
public class FindFragment extends BaseFragment {
    private Unbinder unbinder;
    //    private int[] mTitles = {R.string.home_find_tab_1, R.string.home_market_tab_2};
    private int[] mIconUnselectIds = {
            R.drawable.select, R.drawable.select};
    private int[] mIconSelectIds = {
            R.drawable.selected, R.drawable.selected};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private List<String> mTitlesList;

    @BindView(R.id.title_name_tv)
    TextView title_name_tv;

    @BindView(R.id.information_search_layout)
    RelativeLayout information_search_layout;

    @BindView(R.id.information_search_btn)
    TextView information_search_btn;

    @BindView(R.id.fragment_find_tab_title_layout)
    CommonTabChildLayout fragment_find_tab_title_layout;

    @BindView(R.id.fragment_find_content_layout)
    FrameLayout fragment_find_content_layout;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    MainActivity mainActivity;

    private MagazineFragment magazineFragment;
    private SpecialFragment specialFragment;
    private int currIndex = 0;

    public static FindFragment getInstance() {
        FindFragment fragment = new FindFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_find_layout, container, false);
            unbinder = ButterKnife.bind(this, view);
        }
        mainActivity = (MainActivity) getActivity();
        getTitleList();
        title_name_tv.setText(R.string.tab_find_page);
        information_search_btn.setBackgroundResource(R.drawable.information_search_selector);
        information_search_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, FindSearchActivity.class));
            }
        });


        return view;
    }

    /**
     * 获取资讯标题
     */
    public void getTitleList() {
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.COLUMN);
        requestData.setREQUEST_DATA(new NullBean());
        ServerApi.request(false, requestData, FindResult.class, OleConstants.sign)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FindResult>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(FindResult response) {
                        if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                            mainActivity.dismissProgressDialog();
                            String title;
                            mTitlesList = new ArrayList<String>();
                            for (int i = 0; i < response.getRETURN_DATA().getColumnList().size(); i++) {
                                title = response.getRETURN_DATA().getColumnList().get(i).getName();
                                mTitlesList.add(title);
                            }
                            for (int i = 0; i < mTitlesList.size(); i++) {
                                mTabEntities.add(new TabEntity(mTitlesList.get(i), mIconSelectIds[i], mIconUnselectIds[i]));
                            }

                            magazineFragment = MagazineFragment.getInstance();
                            specialFragment = SpecialFragment.getInstance();
                            mFragments.add(magazineFragment);
                            mFragments.add(specialFragment);
                            ContentID.ZZID = response.getRETURN_DATA().getColumnList().get(0).getId();
                            ContentID.SPID = response.getRETURN_DATA().getColumnList().get(1).getId();
                            magazineFragment.setLists(response.getRETURN_DATA().getColumnList().get(0).getChildColumn());
                            ContentID.SP_TYPE_IMAGES = response.getRETURN_DATA().getColumnList().get(1).getImages();
                            specialFragment.setLists(response.getRETURN_DATA().getColumnList().get(1).getImages());
                            fragment_find_tab_title_layout.setTabData(mTabEntities, FindFragment.this, R.id.fragment_find_content_layout, mFragments);
                            fragment_find_tab_title_layout.setCurrentTab(currIndex);
                            fragment_find_tab_title_layout.setOnTabSelectListener(new OnTabSelectListener() {
                                @Override
                                public void onTabSelect(int position) {
                                    currIndex = position;
                                    if (currIndex == 0) {
                                        magazineFragment.showThisPage();
                                    } else if (currIndex == 1) {
                                        specialFragment.showThisPage();
                                    }
                                    String id = response.getRETURN_DATA().getColumnList().get(position).getId();

                                }

                                @Override
                                public void onTabReselect(int position) {

                                }
                            });

                        } else {
                            mainActivity.dismissProgressDialog();

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mainActivity.dismissProgressDialog();

                    }

                    @Override
                    public void onComplete() {
                        mainActivity.dismissProgressDialog();
                    }
                });

    }

    @Override
    public void showThisPage() {
        super.showThisPage();
        if (currIndex == 0) {
            if (magazineFragment == null) {
                magazineFragment = MagazineFragment.getInstance();
            }
            magazineFragment.showThisPage();
        } else if (currIndex == 1) {
            if (specialFragment == null) {
                specialFragment = SpecialFragment.getInstance();
            }
            specialFragment.showThisPage();
        }
    }

}
