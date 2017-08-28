package com.crv.ole.personalcenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.base.BaseItemTouchListener;
import com.crv.ole.base.PicPreviewActivity;
import com.crv.ole.net.RequestData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.personalcenter.adapter.AfterSaleAdapter;
import com.crv.ole.personalcenter.adapter.AfterSaleListAdapter;
import com.crv.ole.personalcenter.fragment.AfterBottomDialogFragment;
import com.crv.ole.personalcenter.model.OrderInfoReslt;
import com.crv.ole.personalcenter.model.UnicornModel;
import com.crv.ole.personalcenter.ui.MyListView;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.imageloader.GlideImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewActivity;

import java.util.ArrayList;
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
 * 发起售后页面
 * <p>
 * Created by fanhaoyi on 2017/8/22.
 */

public class AfterSaleActivity extends BaseActivity implements BaseItemTouchListener<ImageItem>, AfterSaleListAdapter.OnOrderClickListener {
    @BindView(R.id.title_back_layout)
    RelativeLayout titleBackLayout;
    @BindView(R.id.kf_layout)
    RelativeLayout kfLayout;
    private RecyclerView recyclerView2;
    private AfterSaleAdapter mAdapter;
    private AfterSaleListAdapter mListAdapter;
    private MyListView listViews;

    private boolean isEditMode = true;
    private final int PICK_PHOTO = 0x11;
    private List<OrderInfoReslt.RETURNDATABean.ItemsBean> dataList = new ArrayList<>();
    private TextView mTk;
    private String aliasCode;//订单号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_order);
        initView();
        ButterKnife.bind(this);
        aliasCode = this.getIntent().getStringExtra(OleConstants.BundleConstant.ARG_PARAMS_0);
        initRecyclerView();
        getInfo("2017081817255566023");
        mTk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegionDialog();
            }
        });

    }


    @Override
    public void onAddtClick(OrderInfoReslt.RETURNDATABean.ItemsBean data) {

    }

    @Override
    public void onJSClick(OrderInfoReslt.RETURNDATABean.ItemsBean data) {

    }

    @Override
    public void onCheckClick(OrderInfoReslt.RETURNDATABean.ItemsBean data) {

    }

    private void getInfo(String mOrderID) {
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.ORDER_INFO);
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("aliasCode", mOrderID);
        requestMap.put("serviceCode", "getOrderDetails");
        requestData.setREQUEST_DATA(requestMap);
        ServerApi.request(false, requestData, OrderInfoReslt.class, OleConstants.sign)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mDialog.showProgressDialog("加载中……", null);

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OrderInfoReslt>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(OrderInfoReslt response) {
                        mDialog.dissmissDialog();
                        if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                            if (response.getRETURN_DATA().getItems().size() > 0) {
                                dataList = response.getRETURN_DATA().getItems();
                            }
                            mListAdapter = new AfterSaleListAdapter(AfterSaleActivity.this, dataList);
                            mListAdapter.setOrderClickListener(AfterSaleActivity.this);
                            listViews.setAdapter(mListAdapter);
                            mAdapter.notifyDataSetChanged();
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

    public void initView() {
        recyclerView2 = (RecyclerView) findViewById(R.id.recyclerView2);
        listViews = (MyListView) findViewById(R.id.info_list);
        mTk = (TextView) findViewById(R.id.tk);
    }

    @Override
    public void onItemClick(ImageItem item, int position) {
        if (isEditMode && position == mAdapter.getItemCount() - 1) {
            if (mAdapter.getItemCount() == 6) {
                ToastUtil.showToast("最多选择5张照片");
                return;
            }
            pickPhoto();
            return;
        }
        previewPhotos(position);
    }

    private void initRecyclerView() {
        isEditMode = this.getIntent().getBooleanExtra(OleConstants.BundleConstant.ARG_PARAMS_0, true);
        mAdapter = new AfterSaleAdapter(mContext);
        mAdapter.setBaseItemTouchListener(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 5);
        gridLayoutManager.setAutoMeasureEnabled(true);
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView2.setLayoutManager(gridLayoutManager);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setAdapter(mAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICK_PHOTO:
                if (isEditMode && resultCode == ImagePicker.RESULT_CODE_ITEMS && data != null) {
                    if (mAdapter.getItemCount() == 6) {
                        ToastUtil.showToast("最多选择5张照片");
                        return;
                    }
                    mAdapter.clear();
                    List<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    mAdapter.addItems(images);
                }
                break;
            case ImagePicker.REQUEST_CODE_PREVIEW: {
                if (isEditMode && resultCode == ImagePicker.RESULT_CODE_ITEMS && data != null) {
                    mAdapter.clear();
                    List<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    mAdapter.setAllItem(images);
                }
                break;
            }
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    /**
     * 预览图片
     *
     * @param pos
     */
    private void previewPhotos(int pos) {
        Intent intent = new Intent(this, PicPreviewActivity.class);
        intent.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, mAdapter.getAllItem());
        intent.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, pos);
        intent.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
        intent.putExtra(ImagePreviewActivity.IS_EDIT_MODE, isEditMode);
        startActivityForResult(intent, ImagePicker.REQUEST_CODE_PREVIEW);
    }

    /**
     * 打开相册选择器
     */
    private void pickPhoto() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setMultiMode(true);
        imagePicker.setShowCamera(true);//显示拍照按钮
        imagePicker.setCrop(false);
        imagePicker.setSelectLimit(5);//选中数量限制
        Intent intent = new Intent(this, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_IMAGES, mAdapter.getAllItem());
        startActivityForResult(intent, PICK_PHOTO);
    }


    /**
     * 区域选择
     */
    private void showRegionDialog() {
        AfterBottomDialogFragment.showDialog(this, "", new AfterBottomDialogFragment.OnConfirmClickListener() {
            @Override
            public void onClick(String content) {
                ToastUtil.showToast(content);

            }
        });
    }

    @OnClick({R.id.title_back_layout, R.id.kf_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back_layout:
                finish();
                break;
            case R.id.kf_layout:
                UnicornModel.openChat(mContext);

                break;
        }
    }

}
