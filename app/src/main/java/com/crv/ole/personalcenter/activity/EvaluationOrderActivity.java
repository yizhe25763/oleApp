package com.crv.ole.personalcenter.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.base.BaseItemTouchListener;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.home.model.ImageListData;
import com.crv.ole.net.BaseResponseData;
import com.crv.ole.personalcenter.adapter.EvaluaPhotoAdapter;
import com.crv.ole.personalcenter.model.OrderData;
import com.crv.ole.personalcenter.model.OrderItem;
import com.crv.ole.utils.LoadImageUtil;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.crv.ole.utils.lgimagecompressor.CompressServiceParam;
import com.crv.ole.utils.lgimagecompressor.Constanse;
import com.crv.ole.utils.lgimagecompressor.LGImgCompressor;
import com.crv.ole.utils.lgimagecompressor.LGImgCompressorIntentService;
import com.crv.sdk.utils.TextUtil;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.imageloader.GlideImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 评价订单
 * Created by zhangbo on 2017/8/19.
 */

public class EvaluationOrderActivity extends BaseActivity {

    @BindView(R.id.ll_product_item)
    LinearLayout ll_product_item;//商品区域


    @BindView(R.id.radio_evalua)
    RadioGroup radio_evalua;//评价


    @BindView(R.id.rb_service_nice)
    RadioButton rb_service_nice;//非常满意


    @BindView(R.id.rb_service_nomal)
    RadioButton rb_service_bad;//不满意

    @BindView(R.id.rb_service_bad)
    RadioButton rb_service_nomal;//基本满意


    @BindView(R.id.et_feedback)
    EditText et_feedback;//反馈


    @BindView(R.id.bt_applay)
    Button bt_applay;//提交

    private LayoutInflater inflater;

    private int count;//商品类目数量

    private Map<Integer, EvaluaPhotoAdapter> adapteMap = new HashMap<>();

    private final int PICK_PHOTO = 0x11;

    private int currentIndex = -1;

    private boolean isEditMode = true;

    private int type = 2;//0: 不满意 1: 一般满意 2: 非常满意

    private OrderData orderData;

    private List<OrderItem> orderItems;

    private long serviceStartTime;
    private CompressingReciver reciver;

    ArrayList<ImageItem> allImags = new ArrayList<>();//所有图片集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_order_layout);
        ButterKnife.bind(this);
        inflater = LayoutInflater.from(this);
        initTitleViews();
        initUI();
        initEvent();
    }

    private void initUI() {
        title_name_tv.setText(getString(R.string.evalua_order));
        orderData = (OrderData) getIntent().getSerializableExtra("orderData");
        isEditMode = getIntent().getBooleanExtra("isEditMode", true);
        count = orderData.getItems().size();
        orderItems = orderData.getItems();
        ll_product_item.removeAllViews();
        for (int i = 0; i < count; i++) {
            int a = i;
            EvaluaPhotoAdapter adapter = new EvaluaPhotoAdapter(EvaluationOrderActivity.this);
            adapteMap.put(i, adapter);
            View view = inflater.inflate(R.layout.evaluation_orde_item_layout, null);
            View view_line = view.findViewById(R.id.view_line);
            if (i == 0) {
                view_line.setVisibility(View.GONE);
            } else {
                view_line.setVisibility(View.VISIBLE);
            }
            ImageView img = (ImageView) view.findViewById(R.id.img);
            TextView name = (TextView) view.findViewById(R.id.name);
            TextView price = (TextView) view.findViewById(R.id.price);
            TextView num = (TextView) view.findViewById(R.id.num);
            EditText et_evlua_product = (EditText) view.findViewById(R.id.et_evlua_product);
            TextView tx_evlua_product = (TextView) view.findViewById(R.id.tx_evlua_product);
            RecyclerView rv_photo = (RecyclerView) view.findViewById(R.id.rv_photo);

            LoadImageUtil.getInstance().loadImage(img, orderItems.get(i).getLogoUrl());
            name.setText(orderItems.get(i).getProductName());
            price.setText("¥" + orderItems.get(i).getfTotalPrice());
            num.setText(orderItems.get(i).getAmount() + "");

            if (!isEditMode) {
                et_evlua_product.setVisibility(View.GONE);
                tx_evlua_product.setVisibility(View.VISIBLE);
            } else {
                et_evlua_product.setVisibility(View.VISIBLE);
                tx_evlua_product.setVisibility(View.GONE);
                registerCompressingReciver();
            }

            GridLayoutManager gridLayoutManager = new GridLayoutManager(EvaluationOrderActivity.this, 5);
            gridLayoutManager.setAutoMeasureEnabled(true);
            rv_photo.setLayoutManager(gridLayoutManager);
            rv_photo.setTag(i);
            rv_photo.setAdapter(adapter);
            adapter.setBaseItemTouchListener(new BaseItemTouchListener() {

                @Override
                public void onItemClick(Object item, int position) {
                    currentIndex = a;
                    if (isEditMode && position == adapter.getItemCount() - 1) {
                        pickPhoto(adapter);
                        return;
                    }
                    previewPhotos(adapter, position);
                }
            });
            ll_product_item.addView(view);
        }

    }

    private void initEvent() {
        title_back_layout.setOnClickListener(this);
        bt_applay.setOnClickListener(this);
        radio_evalua.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_service_nice://非常满意
                        type = 2;
                        break;
                    case R.id.rb_service_nomal://满意
                        type = 1;
                        break;
                    case R.id.rb_service_bad://不满意
                        type = 0;
                        break;
                }
            }
        });
    }


    /**
     * 打开相册选择器
     */
    private void pickPhoto(EvaluaPhotoAdapter adapter) {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setMultiMode(true);
        imagePicker.setShowCamera(true);//显示拍照按钮
        imagePicker.setCrop(false);
        imagePicker.setSelectLimit(9);//选中数量限制
        Intent intent = new Intent(this, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_IMAGES, adapter.getAllItem());
        startActivityForResult(intent, PICK_PHOTO);
    }


    /**
     * 预览图片
     *
     * @param adapter
     * @param pos
     */
    private void previewPhotos(EvaluaPhotoAdapter adapter, int pos) {
        Intent intent = new Intent(this, ImagePreviewActivity.class);
        intent.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, adapter.getAllItem());
        intent.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, pos);
        intent.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
        startActivityForResult(intent, ImagePicker.REQUEST_CODE_PREVIEW);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICK_PHOTO:
                if (isEditMode && resultCode == ImagePicker.RESULT_CODE_ITEMS && data != null) {
                    List<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    adapteMap.get(currentIndex).addItems(images);
                }
                break;
            case ImagePicker.REQUEST_CODE_PREVIEW: {
                if (isEditMode && resultCode == ImagePicker.RESULT_CODE_ITEMS && data != null) {
                    List<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    adapteMap.get(currentIndex).setAllItem(images);
                }
                break;
            }
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    /**
     * 压缩图片
     *
     * @param context
     * @param list
     */
    private void compressImages(Context context, ArrayList<ImageItem> list, int index) {
        if (list.size() == 0) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            CompressServiceParam param = new CompressServiceParam();
            param.setOutHeight(800);
            param.setOutWidth(600);
            param.setMaxFileSize(25);
            param.setSrcImageUri(list.get(i).path);
            param.setTag(index);
            LGImgCompressorIntentService.startActionCompress(context, param);
        }
    }

    /**
     * 上传图片到服务器
     *
     * @param compressResults
     */
    private void uploadImages(ArrayList<LGImgCompressor.CompressResult> compressResults) {
        List<File> fileList = new ArrayList<>();
        for (LGImgCompressor.CompressResult compressResult : compressResults) {
            if (compressResult.getStatus() == LGImgCompressor.CompressResult.RESULT_OK) { //压缩成功
                fileList.add(new File(compressResult.getOutPath()));
            }
        }
        ServiceManger.getInstance().uploadImage(fileList, new BaseRequestCallback<ImageListData>() {

            @Override
            public void onEnd() {
                super.onEnd();
                dismissProgressDialog();
            }

            @Override
            public void onSuccess(ImageListData data) {
                if (OleConstants.REQUES_SUCCESS.equalsIgnoreCase(data.getCode())) {
                    List<ImageListData.Data> imaListData = data.getData();

                    StringBuilder sb = new StringBuilder();
                    List<String> list = new ArrayList();
                    for (int i = 0; i < imaListData.size(); i++) {
                        sb.append(imaListData.get(i).getFileId());
                        if (i != imaListData.size() - 1) {
                            sb.append("|");
                        }
                    }
                    productAppraiseAdd(sb.toString(), compressResults.get(0).getTag());
                }
            }

            @Override
            public void onFailed(String msg) {
                dismissProgressDialog();
                Toast.makeText(mContext, "上传图片失败:" + msg, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.title_back_layout:
                finish();
                break;

            case R.id.bt_applay://提交评价
                for (int i = 0; i < ll_product_item.getChildCount(); i++) {
                    View childAt = ll_product_item.getChildAt(i);
                    EditText editText = (EditText) childAt.findViewById(R.id.et_evlua_product);
                    if (TextUtil.isEmpty(editText.getText().toString())) {
                        ToastUtil.showToast("请填写商品评价");
                        return;
                    }
                }
                if (TextUtil.isEmpty(et_feedback.getText().toString())) {
                    ToastUtil.showToast("请填写订单评价");
                    return;
                }
                submitEvlua();
                break;
        }
    }

    /**
     * 提交评价
     */
    private void submitEvlua() {
        showProgressDialog(R.string.waiting);
        allImags.clear();
        for (int i = 0; i < count; i++) {
            allImags.addAll(adapteMap.get(i).getAllItem());
        }
        if (allImags.size() == 0) {
            //直接评价商品
            for (int i = 0; i < count; i++) {
                productAppraiseAdd("", i);
            }
        } else {//压缩图片后再上传评价
            for (int i = 0; i < count; i++) {
                compressImages(this, adapteMap.get(i).getAllItem(), i);
            }
        }
    }


    /**
     * 评价商品
     */
    public void productAppraiseAdd(String imglist, int index) {
        Map<String, String> params = new HashMap<>();
        View view = ll_product_item.getChildAt(index);
        EditText editText = (EditText) view.findViewById(R.id.et_evlua_product);
        params.put("comment", editText.getText().toString());
        params.put("images", imglist);
        params.put("productId", orderData.getItems().get(index).getProductId());
        ServiceManger.getInstance().productAppraiseAdd(params, new BaseRequestCallback<BaseResponseData>() {
            @Override
            public void onSuccess(BaseResponseData data) {
                if (OleConstants.REQUES_SUCCESS.equals(data.getRETURN_CODE())) {
                    if (index == orderData.getItems().size() - 1) {//最后一个完成 调用订单评论
                        orderAppraiseAdd();
                    }
                } else {
                    dismissProgressDialog();
                    ToastUtil.showToast(data.getRETURN_DESC());
                }
            }
        });
    }


    /**
     * 评价订单
     */
    public void orderAppraiseAdd() {
        Map<String, String> map = new HashMap<>();
        map.put("comment", et_feedback.getText().toString());
        map.put("orderId", orderData.getOrderAliasCode());
        map.put("satisfaction", type + "");

        ServiceManger.getInstance().orderAppraiseAdd(map, new BaseRequestCallback<BaseResponseData>() {
            @Override
            public void onEnd() {
                super.onEnd();
                dismissProgressDialog();
            }

            @Override
            public void onSuccess(BaseResponseData data) {
                if (OleConstants.REQUES_SUCCESS.equals(data.getRETURN_CODE())) {
                    ToastUtil.showToast("订单评价成功");
                    EventBus.getDefault().post(OleConstants.ORDER_APPRAISE_ADD_SUCCESS);
                    finish();
                } else {
                    ToastUtil.showToast("订单评价失败");
                }
            }
        });

    }


    /**
     * 注册压缩图片广播
     */
    private void registerCompressingReciver() {
        reciver = new CompressingReciver();
        IntentFilter intentFilter = new IntentFilter(Constanse.ACTION_COMPRESS_BROADCAST);
        registerReceiver(reciver, intentFilter);
    }

    /**
     * 压缩图片广播
     */
    private class CompressingReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int flag = intent.getIntExtra(Constanse.KEY_COMPRESS_FLAG, -1);
            if (flag == Constanse.FLAG_BEGAIIN) {
                serviceStartTime = System.currentTimeMillis();
                return;
            }

            if (flag == Constanse.FLAG_END) {
                ArrayList<LGImgCompressor.CompressResult> compressResults = (ArrayList<LGImgCompressor.CompressResult>) intent.getSerializableExtra(Constanse.KEY_COMPRESS_RESULT);
                Log.d(TAG, compressResults.size() + "compressed done");
                Log.d(TAG, "compress " + compressResults.size() + " files used total time:" + (System.currentTimeMillis() - serviceStartTime));
                Log.i(TAG, compressResults.size() + " files compressed done \nused total time:" + (System.currentTimeMillis() - serviceStartTime) + "ms");

                ArrayList<LGImgCompressor.CompressResult>[] comps = new ArrayList[count];
                for (int i = 0; i < count; i++) {
                    ArrayList<LGImgCompressor.CompressResult> list = new ArrayList<>();
                    comps[i] = list;
                    for (LGImgCompressor.CompressResult result : compressResults) {
                        if (result.getTag() == i) {
                            comps[i].add(result);
                        }
                    }
                }
                for (int j = 0; j < comps.length; j++) {
                    if (comps[j].size() > 0) {
                        uploadImages(comps[j]);
                    } else {
                        productAppraiseAdd("", j);
                    }
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (reciver != null) {
            unregisterReceiver(reciver);
        }
    }
}
