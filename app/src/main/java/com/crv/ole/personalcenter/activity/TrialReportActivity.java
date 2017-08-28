package com.crv.ole.personalcenter.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Toast;

import com.crv.ole.R;
import com.crv.ole.base.BaseAppCompatActivity;
import com.crv.ole.base.BaseItemTouchListener;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.PicPreviewActivity;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.databinding.ActivityTrialReportBinding;
import com.crv.ole.home.model.ImageListData;
import com.crv.ole.net.BaseResponseData;
import com.crv.ole.personalcenter.adapter.TrialReportAdapter;
import com.crv.ole.shopping.model.TrialReportInfoData;
import com.crv.ole.trial.activity.TrialInfoActivity;
import com.crv.ole.trial.model.TrialReportInputDate;
import com.crv.ole.trial.model.TrialReportInputResponse;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.crv.ole.utils.lgimagecompressor.CompressServiceParam;
import com.crv.ole.utils.lgimagecompressor.Constanse;
import com.crv.ole.utils.lgimagecompressor.LGImgCompressor;
import com.crv.ole.utils.lgimagecompressor.LGImgCompressorIntentService;
import com.crv.sdk.dialog.FragmentDialog;
import com.crv.sdk.dialog.IDialog;
import com.crv.sdk.utils.TextUtil;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.imageloader.GlideImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查看和提交试用报告
 * create by lihongshi
 */
public class TrialReportActivity extends BaseAppCompatActivity implements BaseItemTouchListener<ImageItem> {
    private boolean isEditMode = true;
    private ActivityTrialReportBinding mDataBinding;
    private String activityId, productId, orderId, productObjId;
    private TrialReportAdapter mAdapter;

    private final int PICK_PHOTO = 0x11;

    private TrialReportInfoData.RETURNDATABean.ListBean.ExamineInfoBean examineInfoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isEditMode = this.getIntent().getBooleanExtra(OleConstants.BundleConstant.ARG_PARAMS_0, true);
        activityId = this.getIntent().getStringExtra(OleConstants.BundleConstant.ARG_PARAMS_1);
        productId = this.getIntent().getStringExtra(OleConstants.BundleConstant.ARG_PARAMS_2);
        orderId = this.getIntent().getStringExtra(OleConstants.BundleConstant.ARG_PARAMS_3);
        productObjId = this.getIntent().getStringExtra(OleConstants.BundleConstant.ARG_PARAMS_4);
        mDataBinding = (ActivityTrialReportBinding) getViewDataBinding();
        initView();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (reciver != null) {
            unregisterReceiver(reciver);
        }
    }

    @Override
    public void onItemClick(ImageItem item, int position) {
        if (isEditMode && position == mAdapter.getItemCount() - 1) {
            if (mAdapter.getItemCount() == 10) {
                ToastUtil.showToast("最多选择9张照片");
                return;
            }
            pickPhoto();
            return;
        }
        previewPhotos(position);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_trial_report;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICK_PHOTO:
                if (isEditMode && resultCode == ImagePicker.RESULT_CODE_ITEMS && data != null) {
                    if (mAdapter.getItemCount() == 10) {
                        ToastUtil.showToast("最多选择9张照片");
                        return;
                    }
                    List<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    mAdapter.addItems(images);
                    updateView();
                }
                break;
            case ImagePicker.REQUEST_CODE_PREVIEW: {
                if (isEditMode && resultCode == ImagePicker.RESULT_CODE_ITEMS && data != null) {
                    List<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    mAdapter.setAllItem(images);
                    updateView();
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
        intent.putExtra(PicPreviewActivity.IS_FROM_NET, !isEditMode);
        startActivityForResult(intent, ImagePicker.REQUEST_CODE_PREVIEW);
    }

    private void initView() {
        if (isEditMode) {
            getToolbarSubTitle().setText("提交");
            getToolbarSubTitle().setEnabled(false);
            getToolbarSubTitle().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    submitTrial();
                }
            });
            mDataBinding.wordContent.addTextChangedListener(textWatcher);
            mDataBinding.moodWords.addTextChangedListener(textWatcher);
            mDataBinding.feelingWords.addTextChangedListener(textWatcher);
            mDataBinding.compareWords.addTextChangedListener(textWatcher);
            mDataBinding.freeWords.addTextChangedListener(textWatcher);
            registerCompressingReciver();
            //获取模版
            getTrialReprotInput();
        } else {
            getToolbarSubTitle().setText("审核中");
            getToolbarSubTitle().setEnabled(false);
            mDataBinding.wordContent.setEnabled(false);
            mDataBinding.moodWords.setEnabled(false);
            mDataBinding.feelingWords.setEnabled(false);
            mDataBinding.compareWords.setEnabled(false);
            mDataBinding.freeWords.setEnabled(false);
            getTrialReprot();
            getToolbarSubTitle().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (examineInfoBean != null) {
                        if (examineInfoBean.getExamineStatus() == 1) {//审核通过
                            startActivity(new Intent(TrialReportActivity.this, TrialInfoActivity.class).putExtra("from", "report"));
                        }
                    }
                }
            });
        }
        initRecyclerView();
    }

    private void initRecyclerView() {
        mAdapter = new TrialReportAdapter(mContext);
        mAdapter.setBaseItemTouchListener(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 5);
        gridLayoutManager.setAutoMeasureEnabled(true);
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        mDataBinding.recyclerView2.setLayoutManager(gridLayoutManager);
        mDataBinding.recyclerView2.setHasFixedSize(true);
        mDataBinding.recyclerView2.setAdapter(mAdapter);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            updateView();
        }
    };

    private void updateView() {
        if (TextUtil.isEmpty(mDataBinding.wordContent.getText().toString())
                || TextUtil.isEmpty(mDataBinding.moodWords.getText().toString())
                || TextUtil.isEmpty(mDataBinding.feelingWords.getText().toString())
                || TextUtil.isEmpty(mDataBinding.compareWords.getText().toString())
                || TextUtil.isEmpty(mDataBinding.freeWords.getText().toString())
                || mAdapter.getAllItem().size() < 3) {
            getToolbarSubTitle().setEnabled(false);
            return;
        }
        getToolbarSubTitle().setEnabled(true);
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
        imagePicker.setSelectLimit(9);//选中数量限制

        Intent intent = new Intent(this, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_IMAGES, mAdapter.getAllItem());
        startActivityForResult(intent, PICK_PHOTO);
    }

    private IDialog dialog;

    private void submitTrial() {
        Log.e(TAG, "提交");
        dialog = new FragmentDialog(this);
        dialog.showProgressDialog("上传中..", null);
        compressImages(this, mAdapter.getAllItem());
    }

    private void compressImages(Context context, ArrayList<ImageItem> list) {
        for (int i = 0; i < list.size(); i++) {
            CompressServiceParam param = new CompressServiceParam();
            param.setOutHeight(800);
            param.setOutWidth(600);
            param.setMaxFileSize(25);
            param.setSrcImageUri(list.get(i).path);
            LGImgCompressorIntentService.startActionCompress(context, param);
        }
    }

    private long serviceStartTime;
    private CompressingReciver reciver;

    private void registerCompressingReciver() {
        reciver = new CompressingReciver();
        IntentFilter intentFilter = new IntentFilter(Constanse.ACTION_COMPRESS_BROADCAST);
        registerReceiver(reciver, intentFilter);
    }

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
                uploadImages(compressResults);
            }
        }
    }

    private void uploadImages(ArrayList<LGImgCompressor.CompressResult> compressResults) {
        List<File> fileList = new ArrayList<>();
        for (LGImgCompressor.CompressResult compressResult : compressResults) {
            if (compressResult.getStatus() == LGImgCompressor.CompressResult.RESULT_OK) { //压缩成功
                fileList.add(new File(compressResult.getOutPath()));
            }
        }

        ServiceManger.getInstance().uploadImage(fileList, new BaseRequestCallback<ImageListData>() {
            @Override
            public void onSuccess(ImageListData data) {
                if (OleConstants.REQUES_SUCCESS.equalsIgnoreCase(data.getCode())) {
                    List<ImageListData.Data> imaListData = data.getData();
                    List<String> list = new ArrayList();
                    for (ImageListData.Data data1 : imaListData) {
                        String fileId = data1.getFileId();
                        list.add(fileId);
                    }
                    commitContent(list);
                    return;
                }
                dialog.dissmissDialog();
            }

            @Override
            public void onFailed(String msg) {
                dialog.dissmissDialog();
                Toast.makeText(mContext, "上传图片失败:" + msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void commitContent(List<String> fileIds) {
        StringBuilder fileIdList = new StringBuilder();
        for (int i = 0; i < fileIds.size(); i++) {
            fileIdList.append(fileIds.get(i));
            if (i != fileIds.size() - 1) {
                fileIdList.append(",");
            }
        }
        ServiceManger.getInstance().addTrialReport(activityId, productObjId, orderId,
                mDataBinding.wordContent.getText().toString(),
                mDataBinding.moodWords.getText().toString(),
                mDataBinding.feelingWords.getText().toString(),
                mDataBinding.compareWords.getText().toString(),
                mDataBinding.freeWords.getText().toString(),
                fileIdList.toString(), new BaseRequestCallback<BaseResponseData>() {
                    @Override
                    public void onSuccess(BaseResponseData data) {
                        dialog.dissmissDialog();
                        if (OleConstants.REQUES_SUCCESS.equals(data.getRETURN_CODE())) {
                            ToastUtil.showToast("提交报告成功");
                            finish();
                        } else {
                            ToastUtil.showToast(data.getRETURN_DESC());
                        }
                    }

                    @Override
                    public void onFailed(String msg) {
                        dialog.dissmissDialog();
                        ToastUtil.showToast(msg);
                    }
                });
    }

    /**
     * 已填写，获取试用报告
     */
    private void getTrialReprot() {
        Map<String, String> map = new HashMap<>();
        map.put("activityId", activityId);
        map.put("orderId", orderId);
        map.put("productId", productId);
        ServiceManger.getInstance().getTrialReport(map, new BaseRequestCallback<TrialReportInfoData>() {
            @Override
            public void onStart() {
                super.onStart();
                showProgressDialog(getString(R.string.waiting), null);
            }

            @Override
            public void onEnd() {
                super.onEnd();
                dismissProgressDialog();
            }

            @Override
            public void onSuccess(TrialReportInfoData data) {
                if (OleConstants.REQUES_SUCCESS.equals(data.getRETURN_CODE()) && data.getRETURN_DATA() != null && data.getRETURN_DATA().getList().size() > 0) {
                    updateEdit(data.getRETURN_DATA());
                } else {
                    ToastUtil.showToast("获取报告失败");
                }
            }
        });
    }

    /**
     * 获取试用报告模版
     */
    private void getTrialReprotInput() {
        Map<String, String> map = new HashMap<>();
        map.put("productObjId", productId);
        ServiceManger.getInstance().getReportInput(map, new BaseRequestCallback<TrialReportInputResponse>() {
            @Override
            public void onStart() {
                super.onStart();
                showProgressDialog(getString(R.string.waiting), null);
            }

            @Override
            public void onEnd() {
                super.onEnd();
                dismissProgressDialog();
            }

            @Override
            public void onSuccess(TrialReportInputResponse data) {
                if (data != null && data.getRETURN_DATA() != null) {
                    updateHint(data.getRETURN_DATA());
                }
            }
        });
    }

    private void updateHint(TrialReportInputDate date) {
        mDataBinding.wordContent.setHint(date.getStatement1());
        mDataBinding.moodWords.setHint(date.getStatement2());
        mDataBinding.feelingWords.setHint(date.getStatement3());
        mDataBinding.compareWords.setHint(date.getStatement4());
        mDataBinding.freeWords.setHint(date.getStatement5());
    }

    private void updateEdit(TrialReportInfoData.RETURNDATABean bean) {
        mDataBinding.wordContent.setText(bean.getList().get(0).getWordContent());
        mDataBinding.moodWords.setText(bean.getList().get(0).getMoodWords());
        mDataBinding.feelingWords.setText(bean.getList().get(0).getFeelingWords());
        mDataBinding.compareWords.setText(bean.getList().get(0).getCompareWords());
        mDataBinding.freeWords.setText(bean.getList().get(0).getFreeWords());

        List<ImageItem> imageItems = new ArrayList<>();
        for (String img : bean.getList().get(0).getFileIdList()) {
            ImageItem imageItem = new ImageItem();
            imageItem.path = img;
            imageItems.add(imageItem);
        }
        if (imageItems.size() > 0) {
            mAdapter.setEdit(false);
            mAdapter.setAllItem(imageItems);
            mAdapter.notifyDataSetChanged();
        }
        if (bean.getList().get(0).getExamineInfo() != null) {
            examineInfoBean = bean.getList().get(0).getExamineInfo();
            if (examineInfoBean.getExamineStatus() == 0) {//待审核
                getToolbarSubTitle().setText("后台审核中");
            } else if (examineInfoBean.getExamineStatus() == 1) {//审核通过
                getToolbarSubTitle().setTextColor(getResources().getColor(R.color.d_d9be64));
                String str = "审核通过";
                SpannableString content = new SpannableString(str);
                content.setSpan(new UnderlineSpan(), 0, str.length(), 0);
                getToolbarSubTitle().setText(content);
                getToolbarSubTitle().setEnabled(true);
            } else {//审核不通过
                getToolbarSubTitle().setText("审核不通过");
            }
        }
    }
}
