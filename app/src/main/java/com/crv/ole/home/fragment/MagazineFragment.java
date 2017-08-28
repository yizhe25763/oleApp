package com.crv.ole.home.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.BaseApplication;
import com.crv.ole.information.activity.ZzActivity;
import com.crv.ole.information.model.ContentID;
import com.crv.ole.information.model.FindResult;
import com.crv.ole.information.requestmodel.ListBeanRequest;
import com.crv.ole.information.model.ListResult;
import com.crv.ole.net.RequestData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.utils.LoadImageUtil;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.sdk.fragment.BaseFragment;

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
 * 首页发现频道-杂志
 */
public class MagazineFragment extends BaseFragment {
    Unbinder unbinder1;
    private Unbinder unbinder;

    @BindView(R.id.magazine_content_scroll)
    HorizontalScrollView magazine_content_scroll;

    @BindView(R.id.magazine_content_layout)
    LinearLayout magazine_content_layout;
    private int number;
    private String title;
    private String urls;
    private String ids;
    List<ListResult.RETURNDATABean.InformationBean> datas = new ArrayList<>();
    List<FindResult.RETURNDATABean.ColumnListBean.ChildColumnBean> childColumn = new ArrayList<>();

    public static MagazineFragment getInstance() {
        MagazineFragment fragment = new MagazineFragment();
        return fragment;
    }

    public void setLists(List<FindResult.RETURNDATABean.ColumnListBean.ChildColumnBean> childColumn) {
        this.childColumn = childColumn;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_magazine_layout, container, false);
            unbinder = ButterKnife.bind(this, view);
        }
        magazine_content_layout.removeAllViews();
        unbinder1 = ButterKnife.bind(this, view);
        return view;
    }


    /**
     * 获取杂志列表
     */
    public void getContentList(String id) {
        magazine_content_layout.removeAllViews();
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
                        mDialog.dissmissDialog();
                        if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                            for (int i = 0; i < response.getRETURN_DATA().getTotal(); i++) {
                                for (int j = 0; j < childColumn.size(); j++) {
                                    if (response.getRETURN_DATA().getInformation().get(i).getColumnId().equals(childColumn.get(j).getId())) {
                                        ListResult.RETURNDATABean.InformationBean beans = new ListResult.RETURNDATABean.InformationBean();
                                        number = response.getRETURN_DATA().getInformation().get(i).getBrowseCount();
                                        urls = response.getRETURN_DATA().getInformation().get(i).getCoverImg();
                                        title = response.getRETURN_DATA().getInformation().get(i).getTitle();
                                        ids = response.getRETURN_DATA().getInformation().get(i).getId();
                                        beans.setTitle(title);
                                        beans.setId(ids);
                                        beans.setIconUrl(childColumn.get(j).getImages().get(0).getUrl());
                                        beans.setCoverImg(urls);
                                        beans.setBrowseCount(number);
                                        datas.add(beans);
                                    }

                                }
                            }

                            for (int i = 0; i < response.getRETURN_DATA().getTotal(); i++) {
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                        BaseApplication.getDeviceWidth() * 3 / 4,
                                        LinearLayout.LayoutParams.WRAP_CONTENT);
                                magazine_content_layout.addView(getItemView(i, datas), layoutParams);
                            }

                            isLoad = true;
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mDialog.dissmissDialog();

                    }

                    @Override
                    public void onComplete() {
                        mDialog.dissmissDialog();

                    }
                });

    }

    private View getItemView(int position, List<ListResult.RETURNDATABean.InformationBean> datas) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.magazine_item_layout, null);
        ImageView magazine_item_img = (ImageView) itemView.findViewById(R.id.magazine_item_img);
        ImageView iconImage = (ImageView) itemView.findViewById(R.id.special_list_item_img_icon);
        TextView magazine_item_desc_txt = (TextView) itemView.findViewById(R.id.magazine_item_desc_txt);
        TextView magazine_item_count_txt = (TextView) itemView.findViewById(R.id.magazine_item_count_txt);
        LoadImageUtil.getInstance().loadImage(magazine_item_img, datas.get(position).getCoverImg(), R.drawable.capture01, ImageView.ScaleType.CENTER_CROP);
        LoadImageUtil.getInstance().loadImage(iconImage, datas.get(position).getIconUrl(), R.drawable.capture01, ImageView.ScaleType.MATRIX);
        //        LoadImageUtil.getInstance().loadImage(magazine_item_img, datas.get(position).getCoverImg());
        magazine_item_desc_txt.setText(datas.get(position).getTitle());
        magazine_item_count_txt.setText(datas.get(position).getBrowseCount() + "个人阅读");
        magazine_item_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("id", datas.get(position).getId());
                intent.setClass(getActivity(), ZzActivity.class);
                startActivity(intent);
            }
        });

        return itemView;
    }

    @Override
    public void showThisPage() {
        super.showThisPage();
        if (!isLoad) {
            magazine_content_layout.removeAllViews();
            getContentList(ContentID.ZZID);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
