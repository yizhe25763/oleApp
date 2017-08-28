package com.crv.ole.shopping.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.information.activity.SpecialCommentActivity;
import com.crv.ole.information.model.LikeBean;
import com.crv.ole.net.RequestData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.shopping.adapter.EvaluateListAdapter;
import com.crv.ole.shopping.model.EvaluateInfoResult;
import com.crv.ole.shopping.ui.ProductListView;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.crv.sdk.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * Created by Fairy on 2017/7/26.
 * 商品详情页 - 详情页 - 用户评价
 */

public class EvaluateFragment extends BaseFragment {
    @BindView(R.id.listView)
    ProductListView listView;
    @BindView(R.id.noEvaData_tv)
    TextView noDataTv;
    private Unbinder unbinder;
    private static EvaluateFragment evaluateFragment;
    private EvaluateListAdapter adapter;
    private List<EvaluateInfoResult.RETURNDATABean.RecordListBean> dataList;
    private String productId;
    private int page = 1;

    public static EvaluateFragment getInstance( ){
        if(evaluateFragment == null) {
            evaluateFragment = new EvaluateFragment();
        }
        return evaluateFragment;
    }

    /**
     * 设置页面参数显示
     * @param id
     */
    public void loadData(String id){
        if(TextUtils.isEmpty(productId) || !TextUtils.equals(id, productId)) {
//            productId = "p_190232";
            productId = id;
            getArticleInfo();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(view == null) {
            view = inflater.inflate(R.layout.fragment_evaluate_layout, container, false);
            unbinder = ButterKnife.bind(this, view);
            dataList = new ArrayList<>();
            adapter = new EvaluateListAdapter(mContext, dataList);
            adapter.setListener(new SpecialCommentActivity.ZanInterf() {
                @Override
                public void requestZannet(int type, int position) {
                    commentZan(type, position);
                }
            });
            listView.setAdapter(adapter);
        }
        return view;
    }


    /**
     * 获取评论列表 根据page数量来判断是普通刷新还是上拉加载
     */
    private void getArticleInfo(){
        if(page > 1) {
            mDialog.showProgressDialog("加载中...", null);
        }
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.GET_PRODUCT_APPRAISE_ID);
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("page", page+"");
        requestMap.put("productId",productId);
        requestData.setREQUEST_DATA(requestMap);
        ServerApi.request(false, requestData, EvaluateInfoResult.class,mPreferencesHelper.getString(OleConstants.KEY.REQUEST_SIGN_KEY))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {}
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<EvaluateInfoResult>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(EvaluateInfoResult response) {
                        if(response.getRETURN_DESC().equals("操作成功")){
                            if(response.getRETURN_DATA()!=null){
                                if(response.getRETURN_DATA().getRecordList() != null){
                                    if(response.getRETURN_DATA().getRecordList().size() > 0) {
                                        if(page == 1){
                                            dataList.clear();
                                        }
                                        dataList.addAll(response.getRETURN_DATA().getRecordList());
                                        adapter.notifyDataSetChanged();
                                    }else{
                                        if(page == 1){
                                            noDataTv.setVisibility(View.VISIBLE);
                                            listView.setVisibility(View.GONE);
                                        }else{
                                            ToastUtil.showToast("没有更多的了哦");
                                        }
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        ToastUtil.showToast("请求出错，请稍后再试");
                        e.printStackTrace();            //请求失败
                    }

                    @Override
                    public void onComplete() {
                        mDialog.dissmissDialog();
                    }
                });
    }



    /**
     * 对商品评论进行点赞
     * @param position 操作评论的index值
     */
    public void commentZan(int type, int position){
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.PRODUCT_COMMENT_LIKE_ID);
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("productId", productId);
        requestMap.put("commentId", dataList.get(position).getId());
        requestMap.put("option", "set");
        requestData.setREQUEST_DATA(requestMap);
        ServerApi.request(false, requestData, LikeBean.class,mPreferencesHelper.getString(OleConstants.KEY.REQUEST_SIGN_KEY))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {}
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LikeBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(LikeBean response) {
                        if(TextUtils.equals(response.getRETURN_CODE(), OleConstants.REQUES_SUCCESS)){
                            if(type == 0){
                                dataList.get(position).getLikes().setStatus(1);
                            }else if(type == 1){
                                dataList.get(position).getLikes().setStatus(0);
                            }
                            dataList.get(position).getLikes().setLikesCount(response.getRETURN_DATA().getLikes());
                            adapter.notifyDataSetChanged();

                        }else{
                            ToastUtil.showToast(response.getRETURN_DESC());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        ToastUtil.showToast("请求出错，请稍后再试");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {}
                });
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        productId = "";
        dataList.clear();
    }

}
