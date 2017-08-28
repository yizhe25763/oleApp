package com.crv.ole.information.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.information.adapter.SpecialCommentListAdapter;
import com.crv.ole.information.model.LikeBean;
import com.crv.ole.information.model.SpecialCommentInfoResultBean;
import com.crv.ole.net.RequestData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.google.gson.Gson;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Fairy on 2017/7/21.
 * 专题 - 专题详情 - 评论
 */

public class SpecialCommentActivity extends BaseActivity {
    @BindView(R.id.comment_refreshLayout)
    PullToRefreshLayout refreshLayout;
    @BindView(R.id.comment_listView)
    ListView listView;
    @BindView(R.id.comment_none)
    TextView noData;
    @BindView(R.id.comment_inputEt)
    EditText inputEt;
    private SpecialCommentListAdapter adapter;
    private List<SpecialCommentInfoResultBean.SpecialCommentListInfo> dataList;
    private String articleId;
    private int start = 1;
    private int limit = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_comment);
        ButterKnife.bind(this);
        initTitleViews();
        initBackButton();
        setBarTitle("评论");

        articleId = getIntent().getStringExtra("id");
        dataList = new ArrayList<>();
        adapter = new SpecialCommentListAdapter(mContext, dataList);
        adapter.setListener(new ZanInterf() {
            @Override
            public void requestZannet(int type, int position) {
                commentZan(type, position);
            }
        });
        listView.setAdapter(adapter);
        getArticleInfo(0);
        initListener();
    }

    private void initListener(){
        //  设置刷新加载事件
        refreshLayout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                start = 1;
                getArticleInfo(1);
            }

            @Override
            public void loadMore() {
                start += limit;
                getArticleInfo(2);
            }
        });
    }

    @OnClick({R.id.comment_sendTv})
    public void onClick(View v){
        super.onClick(v);
        switch (v.getId()){
            case R.id.comment_sendTv:
                if(TextUtils.isEmpty(inputEt.getText().toString())){
                    ToastUtil.showToast("评论内容不能为空！");
                }else {
                    commentZan(0, -1);
                }
                break;
        }
    }

    /**
     * 获取文章评论
     * @param type 0-普通刷新 1-上拉刷新 2-下拉加载
     */
    private void getArticleInfo(int type){
        showProgressDialog(getResources().getString(R.string.zx_transfer_dialog_loading), null);
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.GET_ARTICLEINFO);
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("start", start+"");
        requestMap.put("limit", limit+"");
        requestMap.put("articleId",articleId);
        requestData.setREQUEST_DATA(requestMap);
        ServerApi.request(false, requestData, SpecialCommentInfoResultBean.class,mPreferencesHelper.getString(OleConstants.KEY.REQUEST_SIGN_KEY))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        Log.i("开始请求");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SpecialCommentInfoResultBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i("onSubscribe");
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(SpecialCommentInfoResultBean response) {
                        Log.e("结果数据：" + new Gson().toJson(response));
                        if(response.getRETURN_DESC().equals("操作成功")){
                            if(response.getRETURN_DATA()!=null){
                                if(response.getRETURN_DATA().getCommentList() != null){
                                    if(response.getRETURN_DATA().getCommentList().size() > 0) {
                                        if(type == 1){
                                            dataList.clear();
                                        }
                                        dataList.addAll(response.getRETURN_DATA().getCommentList());
                                    }
                                    if(dataList.size() > 0){
                                        listView.setVisibility(View.VISIBLE);
                                        noData.setVisibility(View.GONE);
                                    }else{
                                        listView.setVisibility(View.GONE);
                                        noData.setVisibility(View.VISIBLE);
                                    }
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("请求出错");
                        ToastUtil.showToast("请求出错，请稍后再试");
                        e.printStackTrace();            //请求失败
                    }

                    @Override
                    public void onComplete() {
                        Log.i("请求完成");
                        dismissProgressDialog();
                        if(type == 1){
                            refreshLayout.finishRefresh();
                        }else if(type == 2){
                            refreshLayout.finishLoadMore();
                        }
                    }
                });
    }



    /**
     * 对文章进行评论，点赞
     * @param type 操作类型 0-评论 5-点赞评论 6-取消点赞评论
     * @param position 操作评论的index值
     */
    public void commentZan(int type, int position){
        showProgressDialog(getResources().getString(R.string.zx_transfer_dialog_loading), null);
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.COMMENT_LIKE_ID);
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("isComment", type+"");
        requestMap.put("articleId", articleId);
        if(type == 0) {
            requestMap.put("commentData", inputEt.getText().toString().trim());
        }else{
            requestMap.put("commentId", dataList.get(position).getCommentId());
        }
        requestData.setREQUEST_DATA(requestMap);
        ServerApi.request(false, requestData, LikeBean.class,mPreferencesHelper.getString(OleConstants.KEY.REQUEST_SIGN_KEY))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        Log.i("开始请求");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LikeBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i("onSubscribe");
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(LikeBean response) {
                        Log.e("结果数据：" + new Gson().toJson(response));
                        Log.i("请求结果：" + response.getRETURN_DESC());
                        if(TextUtils.equals(response.getRETURN_DESC(), "操作成功")){
                            if(type == 0){
                                ToastUtil.showToast("评论发表成功，待审核！");
                                inputEt.setText("");
                            }else {
                                getArticleInfo(1);
                            }
                        }else{
                            ToastUtil.showToast(response.getRETURN_DESC());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("请求出错");
                        ToastUtil.showToast("请求出错，请稍后再试");
                        e.printStackTrace();            //请求失败
                    }

                    @Override
                    public void onComplete() {
                        Log.i("请求完成");
                        dismissProgressDialog();
                    }
                });
    }

    private String getDate(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int date = calendar.get(Calendar.DATE);
        String currTime = year+"年"+month+"月"+date+"日";
        return currTime;
    }

    public interface ZanInterf{
        void requestZannet(int type, int position);
    }

}
