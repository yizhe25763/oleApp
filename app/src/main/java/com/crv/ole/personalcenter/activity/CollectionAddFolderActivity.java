package com.crv.ole.personalcenter.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.crv.ole.BaseApplication;
import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.information.requestmodel.RequestCommentOrLike;
import com.crv.ole.net.BaseResponseData;
import com.crv.ole.net.RequestData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.pay.tools.PayPopupWindow;
import com.crv.ole.pay.tools.PayResultUtils;
import com.crv.ole.personalcenter.model.CollectionFolderListData;
import com.crv.ole.personalcenter.model.CollectionGoodsFolderListData;
import com.crv.ole.personalcenter.requestmodel.RequestCollectionGoodsFolderData;
import com.crv.ole.personalcenter.tools.CollectionEvent;
import com.crv.ole.utils.LoadImageUtil;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.crv.ole.utils.ToolUtils;
import com.crv.ole.utils.fileupload.CapturePhotoHelper;
import com.crv.ole.utils.fileupload.UploadPhotoHelper;
import com.crv.ole.utils.fileupload.UploadUtils;
import com.crv.ole.view.CustomDiaglog;
import com.crv.sdk.utils.FileUtils;
import com.crv.sdk.utils.PreferencesHelper;
import com.crv.sdk.utils.TextUtil;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.yalantis.ucrop.UCrop;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;

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
 * 个人中心 - 我的收藏页面 - 添加收藏文件夹
 * 跳转到此页面必须至少传edit_tag参数，表示编辑文件夹或者新建文件夹
 * 如果为编辑文件夹，则要再传一个folder_data参数
 * 保存文件夹信息的CollectionFolderData对象，新建不用传
 */

public class CollectionAddFolderActivity extends BaseActivity {
    @BindView(R.id.collection_add_folder_name)
    EditText collectionAddFolderName;
    @BindView(R.id.collection_add_folder_face1_layout)
    RelativeLayout collectionAddFolderFace1Layout;
    @BindView(R.id.collection_add_folder_face1_img)
    ImageView collectionAddFolderFace1Img;
    @BindView(R.id.collection_add_folder_face2_layout)
    RelativeLayout collectionAddFolderFace2Layout;
    @BindView(R.id.collection_add_folder_face2_img)
    ImageView collectionAddFolderFace2Img;
    @BindView(R.id.collection_add_folder_face3_layout)
    RelativeLayout collectionAddFolderFace3Layout;
    @BindView(R.id.collection_add_folder_face3_img)
    ImageView collectionAddFolderFace3Img;
    @BindView(R.id.collection_add_folder_face4_layout)
    RelativeLayout collectionAddFolderFace4Layout;
    @BindView(R.id.collection_add_folder_face4_img)
    ImageView collectionAddFolderFace4Img;
    @BindView(R.id.collection_add_folder_face5_layout)
    RelativeLayout collectionAddFolderFace5Layout;
    @BindView(R.id.collection_add_folder_face5_img)
    ImageView collectionAddFolderFace5Img;
    @BindView(R.id.collection_add_folder_face6_layout)
    RelativeLayout collectionAddFolderFace6Layout;
    @BindView(R.id.collection_add_folder_face6_img)
    ImageView collectionAddFolderFace6Img;
    @BindView(R.id.collection_add_folder_ok)
    Button collectionAddFolderOk;

    @BindView(R.id.collection_add_folder_selected1_img)
    ImageView collection_add_folder_selected1_img;
    @BindView(R.id.collection_add_folder_selected2_img)
    ImageView collection_add_folder_selected2_img;
    @BindView(R.id.collection_add_folder_selected3_img)
    ImageView collection_add_folder_selected3_img;
    @BindView(R.id.collection_add_folder_selected4_img)
    ImageView collection_add_folder_selected4_img;
    @BindView(R.id.collection_add_folder_selected5_img)
    ImageView collection_add_folder_selected5_img;
    @BindView(R.id.collection_add_folder_selected6_img)
    ImageView collection_add_folder_selected6_img;

    private UploadPhotoHelper mUploadPhotoHelper;
    private String selectImgPath;

    private int selectIndex = 0;
    private String fromPage;//information:资讯,goods:商品
    private String tag;//表示编辑还是新建，取值：edit，new
    private CollectionFolderListData.FolderData folderData;
    private CollectionGoodsFolderListData.GoodsFolderData goodsFolderData;

    private CustomDiaglog deleteConfirmDiaglog;

    @Override
    @SuppressWarnings("deprecation")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_add_folder);
        ButterKnife.bind(this);
        initTitleViews();
        initBackButton();

        mUploadPhotoHelper = new UploadPhotoHelper(this);

        try {
            fromPage = getIntent().getStringExtra("from_page");
            tag = getIntent().getStringExtra("edit_tag");

            if (TextUtils.equals("edit", tag)) {
                setBarTitle(R.string.collection_edit_folder_title);

                if (TextUtils.equals("information", fromPage)) {
                    folderData = (CollectionFolderListData.FolderData) getIntent().getSerializableExtra("folder_data");
                    collectionAddFolderName.setText(folderData.getArticleFileName());
//                    LoadImageUtil.getInstance().loadImage(collectionAddFolderFace1Img, folderData.getArticleFileImage());
                } else {
                    goodsFolderData = (CollectionGoodsFolderListData.GoodsFolderData) getIntent().getSerializableExtra("folder_data");
                    collectionAddFolderName.setText(goodsFolderData.getFavoriteClassName());
//                    LoadImageUtil.getInstance().loadImage(collectionAddFolderFace1Img, goodsFolderData.getImgUrl());
                }

                title_iv_1.setVisibility(View.VISIBLE);
                title_iv_1.setBackgroundResource(R.color.transparent);
                title_iv_1.setText(R.string.collection_add_folder_delete_txt);
                title_iv_1.setTextColor(Color.parseColor("#666666"));

//                changeFaceBG(1);

            } else
                setBarTitle(R.string.collection_add_folder_title);

            ViewGroup.LayoutParams layoutParams1 = collectionAddFolderFace1Img.getLayoutParams();
            ViewGroup.LayoutParams layoutParams2 = collectionAddFolderFace2Img.getLayoutParams();
            ViewGroup.LayoutParams layoutParams3 = collectionAddFolderFace3Img.getLayoutParams();
            ViewGroup.LayoutParams layoutParams4 = collectionAddFolderFace4Img.getLayoutParams();
            ViewGroup.LayoutParams layoutParams5 = collectionAddFolderFace5Img.getLayoutParams();
            ViewGroup.LayoutParams layoutParams6 = collectionAddFolderFace6Img.getLayoutParams();
            switch (ToolUtils.getCropMaxType(BaseApplication.getDeviceWidth())) {
                case 1:
                    layoutParams1.height = 286;
                    layoutParams2.height = 286;
                    layoutParams3.height = 286;
                    layoutParams4.height = 286;
                    layoutParams5.height = 286;
                    layoutParams6.height = 286;
                    layoutParams1.width = 218;
                    layoutParams2.width = 218;
                    layoutParams3.width = 218;
                    layoutParams4.width = 218;
                    layoutParams5.width = 218;
                    layoutParams6.width = 218;
                    break;
                case 2:
                    layoutParams1.height = 429;
                    layoutParams2.height = 429;
                    layoutParams3.height = 429;
                    layoutParams4.height = 429;
                    layoutParams5.height = 429;
                    layoutParams6.height = 429;
                    layoutParams1.width = 327;
                    layoutParams2.width = 327;
                    layoutParams3.width = 327;
                    layoutParams4.width = 327;
                    layoutParams5.width = 327;
                    layoutParams6.width = 327;
                    break;
                case 3:
                    layoutParams1.height = 572;
                    layoutParams2.height = 572;
                    layoutParams3.height = 572;
                    layoutParams4.height = 572;
                    layoutParams5.height = 572;
                    layoutParams6.height = 572;
                    layoutParams1.width = 436;
                    layoutParams2.width = 436;
                    layoutParams3.width = 436;
                    layoutParams4.width = 436;
                    layoutParams5.width = 436;
                    layoutParams6.width = 436;
                    break;
            }
            collectionAddFolderFace1Img.setLayoutParams(layoutParams1);
            collectionAddFolderFace2Img.setLayoutParams(layoutParams2);
            collectionAddFolderFace3Img.setLayoutParams(layoutParams3);
            collectionAddFolderFace4Img.setLayoutParams(layoutParams4);
            collectionAddFolderFace5Img.setLayoutParams(layoutParams5);
            collectionAddFolderFace6Img.setLayoutParams(layoutParams6);

            collectionAddFolderFace2Img.setBackground(new BitmapDrawable(BitmapFactory.decodeStream(getAssets().open("collection_folder_face_1.jpg"))));
            collectionAddFolderFace3Img.setBackground(new BitmapDrawable(BitmapFactory.decodeStream(getAssets().open("collection_folder_face_2.jpg"))));
            collectionAddFolderFace4Img.setBackground(new BitmapDrawable(BitmapFactory.decodeStream(getAssets().open("collection_folder_face_3.jpg"))));
            collectionAddFolderFace5Img.setBackground(new BitmapDrawable(BitmapFactory.decodeStream(getAssets().open("collection_folder_face_4.jpg"))));
            collectionAddFolderFace6Img.setBackground(new BitmapDrawable(BitmapFactory.decodeStream(getAssets().open("collection_folder_face_5.jpg"))));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnClick({R.id.collection_add_folder_ok, R.id.title_iv_1, R.id.collection_add_folder_face1_layout,
            R.id.collection_add_folder_face2_layout, R.id.collection_add_folder_face3_layout,
            R.id.collection_add_folder_face4_layout, R.id.collection_add_folder_face5_layout,
            R.id.collection_add_folder_face6_layout})
    public void onTabClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.title_iv_1:
                    if (deleteConfirmDiaglog == null) {
                        deleteConfirmDiaglog = new CustomDiaglog(mContext, "确认删除此文件夹", mContext.getString(R.string.dialog_cancel), mContext.getString(R.string.dialog_confirm));

                        deleteConfirmDiaglog.setOnConfimClickListerner(new CustomDiaglog.OnConfimClickListerner() {
                            @Override
                            public void onCanleClick() {
                                if (deleteConfirmDiaglog != null && deleteConfirmDiaglog.isShowing()) {
                                    deleteConfirmDiaglog.dismiss();
                                }
                            }

                            @Override
                            public void OnConfimClick() {
                                if (TextUtils.equals("information", fromPage)) {
                                    deleteInformationFolder();
                                } else {
                                    deleteGoodsFolder();
                                }
                            }
                        });
                    }
                    deleteConfirmDiaglog.show();

//                    showAlertDialog("确认删除此文件夹", "确定", "取消", new CustomDiaglog.OnConfimClickListerner() {
//                        @Override
//                        public void onCanleClick() {
//                            Log.i("点击了取消");
//                        }
//                        @Override
//                        public void OnConfimClick() {
//                            if (TextUtils.equals("information", fromPage)) {
//                                deleteInformationFolder();
//                            } else {
//                                deleteGoodsFolder();
//                            }
//                        }
//                    });
                    break;
                case R.id.collection_add_folder_ok:
                    if (TextUtils.equals("edit", tag)) {
                        if (TextUtils.equals("information", fromPage)) {
                            if (TextUtils.isEmpty(selectImgPath) && selectIndex == 0)
                                getFaceImageFile(folderData.getArticleFileImage());
                            else if (!TextUtils.isEmpty(selectImgPath) && selectIndex == 1)
                                updateInformationFolder(new File(selectImgPath));
                            else
                                updateInformationFolder2("collection_folder_face_" + (selectIndex - 1) + ".jpg");
                        } else {
                            if (TextUtils.isEmpty(selectImgPath) && selectIndex == 0)
                                getFaceImageFile(goodsFolderData.getImgUrl());
                            else if (!TextUtils.isEmpty(selectImgPath) && selectIndex == 1)
                                updateGoodsFolder(new File(selectImgPath));
                            else
                                updateGoodsFolder2("collection_folder_face_" + (selectIndex - 1) + ".jpg");
                        }
                    } else {
                        if (!TextUtil.isEmpty(collectionAddFolderName.getText().toString().trim()))
                            if (!TextUtil.isEmpty(selectImgPath) || selectIndex > 1)
                                if (TextUtils.equals("information", fromPage)) {
                                    if (selectIndex == 1)
                                        addInformationFolder(selectImgPath);
                                    else {
                                        createInformationFolder2("collection_folder_face_" + (selectIndex - 1) + ".jpg");
                                    }
                                } else {
                                    if (selectIndex == 1)
                                        addGoodsFolder(selectImgPath);
                                    else {
                                        createGoodsFolder2("collection_folder_face_" + (selectIndex - 1) + ".jpg");
                                    }
                                }
                            else
                                ToastUtil.showToast("请选择文件夹封面");
                        else
                            ToastUtil.showToast("请输入文件夹名称");
                    }
                    break;
                case R.id.collection_add_folder_face1_layout:
                    hideSoftInput();
                    if (TextUtils.isEmpty(selectImgPath))
                        mUploadPhotoHelper.getPermission();
                    else {
                        if (selectIndex == 1)
                            mUploadPhotoHelper.getPermission();
                        else
                            changeFaceBG(1);
                    }
                    break;
                case R.id.collection_add_folder_face2_layout:
                    hideSoftInput();
                    if (selectIndex != 2)
                        changeFaceBG(2);
                    break;
                case R.id.collection_add_folder_face3_layout:
                    hideSoftInput();
                    if (selectIndex != 3)
                        changeFaceBG(3);
                    break;
                case R.id.collection_add_folder_face4_layout:
                    hideSoftInput();
                    if (selectIndex != 4)
                        changeFaceBG(4);
                    break;
                case R.id.collection_add_folder_face5_layout:
                    hideSoftInput();
                    if (selectIndex != 5)
                        changeFaceBG(5);
                    break;
                case R.id.collection_add_folder_face6_layout:
                    hideSoftInput();
                    if (selectIndex != 6)
                        changeFaceBG(6);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // 如果是直接从相册获取
            case UploadUtils.CHOOSE_PICTURE:
                if (resultCode == RESULT_OK && data != null) {
//                    switch (ToolUtils.getCropMaxType(BaseApplication.getDeviceWidth())) {
//                        case 1:
//                            mUploadPhotoHelper.startPhotoZoom(data.getData(), 218, 286, 218, 286);
//                            break;
//                        case 2:
//                            mUploadPhotoHelper.startPhotoZoom(data.getData(), 327, 429, 327, 429);
//                            break;
//                        case 3:
//                            mUploadPhotoHelper.startPhotoZoom(data.getData(), 436, 572, 436, 572);
//                            break;
//                    }
                    mUploadPhotoHelper.startPhotoZoom(data.getData(), 690, 900, 690, 900);
                }
                break;
            // 如果是调用相机拍照时
            case CapturePhotoHelper.CAPTURE_PHOTO_REQUEST_CODE:
                File photoFile = mUploadPhotoHelper.getCapturePhotoHelper().getPhoto();
                if (photoFile.exists()) {
//                    switch (ToolUtils.getCropMaxType(BaseApplication.getDeviceWidth())) {
//                        case 1:
//                            mUploadPhotoHelper.startPhotoZoom(Uri.fromFile(photoFile), 218, 286, 218, 286);
//                            break;
//                        case 2:
//                            mUploadPhotoHelper.startPhotoZoom(Uri.fromFile(photoFile), 327, 429, 327, 429);
//                            break;
//                        case 3:
//                            mUploadPhotoHelper.startPhotoZoom(Uri.fromFile(photoFile), 436, 572, 436, 572);
//                            break;
//                    }
                    mUploadPhotoHelper.startPhotoZoom(Uri.fromFile(photoFile), 690, 900, 690, 900);
                }
                break;
            // 取得裁剪后的图片
            case UCrop.REQUEST_CROP:
                if (mUploadPhotoHelper.getPhoto().exists()) {
                    if (FileUtils.fileSize(mUploadPhotoHelper.getPhoto().getPath()) > 0) {
                        selectImgPath = mUploadPhotoHelper.getPhoto().getPath();
                        LoadImageUtil.getInstance().loadImage(collectionAddFolderFace1Img, selectImgPath, false);
                        changeFaceBG(1);
                    } else {
                        mUploadPhotoHelper.getPhoto().delete();
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 添加资讯收藏文件夹
     */
    private void addInformationFolder(String imgPath) {
        HttpParams params = new HttpParams();
        params.put("action", "0");
        params.put("articleFileName", collectionAddFolderName.getText().toString());
        params.put("imageName", FileUtils.getFileName(imgPath));
        params.put("file", new File(imgPath));


        ServerApi.requestUploadFile(OleConstants.INFORMATION_COLLECTION_ADD_FOLDER_URL,
                params, String.class)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        Log.i("添加收藏夹开始请求");
                        mDialog.showProgressDialog("加载中……", null);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(String response) {
                        mDialog.dissmissDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (OleConstants.REQUES_SUCCESS.equals(jsonObject.optString("code"))) {
                                Log.i("添加收藏夹成功");
//                                refreshDialog();
                                finish();
                                EventBus.getDefault().post(CollectionEvent.INFORMATION_CREATE_FOLDER);
                            } else {
//                            dismissProgressDialog();
                                ToastUtil.showToast(jsonObject.optString("msg"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mDialog.dissmissDialog();
                        ToastUtil.showToast("添加收藏夹失败");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("添加收藏夹完成");
                        mDialog.dissmissDialog();
                    }
                });
    }

    private void createInformationFolder2(String assetFileName) {
        mDialog.showProgressDialog("加载中……", null);
        try {
            Bitmap bmp = BitmapFactory.decodeStream(getAssets().open(assetFileName));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            HttpParams params = new HttpParams();
            params.put("action", "0");
            params.put("articleFileName", collectionAddFolderName.getText().toString());
            params.put("imageName", System.currentTimeMillis() + ".jpg");
            params.put("imageBytes", com.crv.ole.pay.zfb.Base64.encode(stream.toByteArray()));

            bmp.recycle();
            stream.close();

            Log.d("请求的参数：" + params.toString());

            ServerApi.requestUploadFile(OleConstants.INFORMATION_COLLECTION_ADD_FOLDER_URL,
                    params, String.class)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(@NonNull Disposable disposable) throws Exception {
                            Log.i("添加收藏夹开始请求");
                            mDialog.showProgressDialog("加载中……", null);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(String response) {
                            mDialog.dissmissDialog();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (OleConstants.REQUES_SUCCESS.equals(jsonObject.optString("code"))) {
                                    Log.i("添加收藏夹成功");
                                    finish();
                                    EventBus.getDefault().post(CollectionEvent.INFORMATION_CREATE_FOLDER);
                                } else {
//                            dismissProgressDialog();
                                    ToastUtil.showToast(jsonObject.optString("msg"));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            mDialog.dissmissDialog();
                            ToastUtil.showToast("添加收藏夹失败");
                        }

                        @Override
                        public void onComplete() {
                            Log.i("添加收藏夹完成");
                            mDialog.dissmissDialog();
                        }
                    });
        } catch (Exception e) {
            mDialog.dissmissDialog();
            e.printStackTrace();
        }
    }

    /**
     * 添加商品收藏夹
     */
    private void addGoodsFolder(String imgPath) throws Exception {
        HttpParams params = new HttpParams();
        params.put("favoriteClassName", collectionAddFolderName.getText().toString());
        params.put("file", new File(imgPath));

        ServerApi.requestUploadFile(OleConstants.GOODS_COLLECTION_ADD_URL, params, String.class)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        Log.i("添加收藏夹开始请求");
                        mDialog.showProgressDialog("加载中……", null);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(String response) {
                        mDialog.dissmissDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (OleConstants.REQUES_SUCCESS.equals(jsonObject.optString("code"))) {
                                Log.i("添加收藏夹成功");
                                finish();
                                EventBus.getDefault().post(CollectionEvent.GOODS_CREATE_FOLDER);
                            } else {
                                ToastUtil.showToast(jsonObject.optString("msg"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mDialog.dissmissDialog();
                        ToastUtil.showToast("添加收藏夹失败");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("添加收藏夹完成");
                        mDialog.dissmissDialog();
                    }
                });
    }

    /**
     * 添加商品收藏夹,选择预设封面时候调用此方法
     */
    private void createGoodsFolder2(String assetFileName) {
        mDialog.showProgressDialog("加载中……", null);
        try {
            Bitmap bmp = BitmapFactory.decodeStream(getAssets().open(assetFileName));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            HttpParams params = new HttpParams();
            params.put("favoriteClassName", collectionAddFolderName.getText().toString());
            params.put("imageName", System.currentTimeMillis() + ".jpg");
            params.put("imageBytes", com.crv.ole.pay.zfb.Base64.encode(stream.toByteArray()));

            bmp.recycle();
            stream.close();

            ServerApi.requestUploadFile(OleConstants.GOODS_COLLECTION_ADD_URL, params, String.class)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(@NonNull Disposable disposable) throws Exception {
                            Log.i("添加收藏夹开始请求");
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(String response) {
                            mDialog.dissmissDialog();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (OleConstants.REQUES_SUCCESS.equals(jsonObject.optString("code"))) {
                                    Log.i("添加收藏夹成功");
                                    finish();
                                    EventBus.getDefault().post(CollectionEvent.GOODS_CREATE_FOLDER);
                                } else {
                                    ToastUtil.showToast(jsonObject.optString("msg"));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            mDialog.dissmissDialog();
                            ToastUtil.showToast("添加收藏夹失败");
                        }

                        @Override
                        public void onComplete() {
                            Log.i("添加收藏夹完成");
                            mDialog.dissmissDialog();
                        }
                    });
        } catch (Exception e) {
            mDialog.dissmissDialog();
            e.printStackTrace();
        }
    }

    /**
     * 删除资讯收藏文件夹
     */
    private void deleteInformationFolder() {
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.COMMENT_LIKE_ID);

        RequestCommentOrLike requestCommentOrLike = new RequestCommentOrLike();
        requestCommentOrLike.setIsComment("7");
        requestCommentOrLike.setArticleFileName(folderData.getArticleFileName());

        requestData.setREQUEST_DATA(requestCommentOrLike);

        ServerApi.request(false, requestData, BaseResponseData.class,
                new PreferencesHelper(this).getString(OleConstants.KEY.REQUEST_SIGN_KEY))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        Log.i("删除收藏夹开始请求");
                        mDialog.showProgressDialog("加载中……", null);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponseData>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(BaseResponseData response) {
                        mDialog.dissmissDialog();
                        if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                            Log.i("删除收藏夹成功");
                            EventBus.getDefault().post(CollectionEvent.INFORMATION_DELETE_FOLDER);
                            finish();
                        } else {
//                            dismissProgressDialog();
                            ToastUtil.showToast(response.getRETURN_DESC());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        mDialog.dissmissDialog();
                        ToastUtil.showToast("删除收藏夹失败");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("删除收藏夹完成");
                        mDialog.dissmissDialog();
                    }
                });
    }

    /**
     * 删除商品收藏文件夹
     */
    private void deleteGoodsFolder() {
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.GOODS_COLLECTION_DELETE_URL_ID);

        RequestCollectionGoodsFolderData requestDataParms = new RequestCollectionGoodsFolderData();
        requestDataParms.setFavorType(goodsFolderData.getId());

        requestData.setREQUEST_DATA(requestDataParms);

        ServerApi.request(false, requestData, BaseResponseData.class,
                new PreferencesHelper(this).getString(OleConstants.KEY.REQUEST_SIGN_KEY))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        Log.i("删除收藏夹开始请求");
                        mDialog.showProgressDialog("加载中……", null);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponseData>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(BaseResponseData response) {
                        mDialog.dissmissDialog();
                        if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                            Log.i("删除收藏夹成功");
                            finish();
                            EventBus.getDefault().post(CollectionEvent.GOODS_DELETE_FOLDER);
                        } else {
//                            dismissProgressDialog();
                            ToastUtil.showToast(response.getRETURN_DESC());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mDialog.dissmissDialog();
                        ToastUtil.showToast("删除收藏夹失败");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("删除收藏夹完成");
                        mDialog.dissmissDialog();
                    }
                });
    }

    /**
     * 修改资讯收藏文件夹
     */
    private void updateInformationFolder(File imgFile) {
        HttpParams params = new HttpParams();
        params.put("action", "1");
        params.put("articleFileName", folderData.getArticleFileName());
        params.put("newFileName", collectionAddFolderName.getText().toString());
        params.put("imageName", TextUtils.isEmpty(selectImgPath) ?
                FileUtils.getFileName(folderData.getArticleFileImage()) :
                FileUtils.getFileName(selectImgPath));
        params.put("file", imgFile);

        ServerApi.requestUploadFile(OleConstants.INFORMATION_COLLECTION_ADD_FOLDER_URL,
                params, String.class)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        Log.i("修改收藏夹开始请求");
                        if (!mDialog.isShow())
                            mDialog.showProgressDialog("加载中……", null);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(String response) {
                        mDialog.dissmissDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (OleConstants.REQUES_SUCCESS.equals(jsonObject.optString("code"))) {
                                Log.i("修改收藏夹成功");
                                finish();
                                EventBus.getDefault().post(CollectionEvent.INFORMATION_UPDATE_FOLDER);
//                                refreshDialog();
                            } else {
//                            dismissProgressDialog();
                                ToastUtil.showToast(jsonObject.optString("msg"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mDialog.dissmissDialog();
                        ToastUtil.showToast("修改收藏夹失败");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("修改收藏夹完成");
                        mDialog.dissmissDialog();
                    }
                });
    }

    /**
     * 修改资讯收藏文件夹,选择预设封面调用此方法
     */
    private void updateInformationFolder2(String assetFileName) {
        mDialog.showProgressDialog("加载中……", null);
        try {
            Bitmap bmp = BitmapFactory.decodeStream(getAssets().open(assetFileName));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            HttpParams params = new HttpParams();
            params.put("action", "1");
            params.put("articleFileName", folderData.getArticleFileName());
            params.put("newFileName", collectionAddFolderName.getText().toString());
            params.put("imageName", System.currentTimeMillis() + ".jpg");
            params.put("imageBytes", com.crv.ole.pay.zfb.Base64.encode(stream.toByteArray()));

            bmp.recycle();
            stream.close();

            ServerApi.requestUploadFile(OleConstants.INFORMATION_COLLECTION_ADD_FOLDER_URL,
                    params, String.class)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(@NonNull Disposable disposable) throws Exception {
                            Log.i("修改收藏夹开始请求");
                            if (!mDialog.isShow())
                                mDialog.showProgressDialog("加载中……", null);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(String response) {
                            mDialog.dissmissDialog();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (OleConstants.REQUES_SUCCESS.equals(jsonObject.optString("code"))) {
                                    Log.i("修改收藏夹成功");
                                    finish();
                                    EventBus.getDefault().post(CollectionEvent.INFORMATION_UPDATE_FOLDER);
                                } else {
                                    ToastUtil.showToast(jsonObject.optString("msg"));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            mDialog.dissmissDialog();
                            ToastUtil.showToast("修改收藏夹失败");
                        }

                        @Override
                        public void onComplete() {
                            Log.i("修改收藏夹完成");
                            mDialog.dissmissDialog();
                        }
                    });
        } catch (Exception e) {
            mDialog.dissmissDialog();
            e.printStackTrace();
        }
    }

    /**
     * 修改商品收藏文件夹
     */
    private void updateGoodsFolder(File imgFile) {
        HttpParams params = new HttpParams();
        params.put("favorType", goodsFolderData.getId());
        params.put("favoriteClassName", collectionAddFolderName.getText().toString());
        params.put("file", imgFile);

        ServerApi.requestUploadFile(OleConstants.GOODS_COLLECTION_UPDATE_URL, params, String.class)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        Log.i("修改收藏夹开始请求");
                        mDialog.showProgressDialog("加载中……", null);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(String response) {
                        mDialog.dissmissDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (OleConstants.REQUES_SUCCESS.equals(jsonObject.optString("code"))) {
                                Log.i("添加收藏夹成功");
                                finish();
                                EventBus.getDefault().post(CollectionEvent.GOODS_UPDATE_FOLDER);
                            } else {
                                ToastUtil.showToast(jsonObject.optString("msg"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mDialog.dissmissDialog();
                        ToastUtil.showToast("修改收藏夹失败");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("修改收藏夹完成");
                        mDialog.dissmissDialog();
                    }
                });
    }

    /**
     * 修改商品收藏文件夹,选择预设封面调用此方法
     */
    private void updateGoodsFolder2(String assetFileName) {
        mDialog.showProgressDialog("加载中……", null);
        try {
            Bitmap bmp = BitmapFactory.decodeStream(getAssets().open(assetFileName));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            HttpParams params = new HttpParams();
            params.put("favorType", goodsFolderData.getId());
            params.put("favoriteClassName", collectionAddFolderName.getText().toString());
            params.put("file", "");
            params.put("imageName", System.currentTimeMillis() + ".jpg");
            params.put("imageBytes", com.crv.ole.pay.zfb.Base64.encode(stream.toByteArray()));

            bmp.recycle();
            stream.close();

            ServerApi.requestUploadFile(OleConstants.GOODS_COLLECTION_UPDATE_URL, params, String.class)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(@NonNull Disposable disposable) throws Exception {
                            Log.i("修改收藏夹开始请求");
                            mDialog.showProgressDialog("加载中……", null);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            addDisposable(d);
                        }

                        @Override
                        public void onNext(String response) {
                            mDialog.dissmissDialog();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (OleConstants.REQUES_SUCCESS.equals(jsonObject.optString("code"))) {
                                    Log.i("添加收藏夹成功");
                                    finish();
                                    EventBus.getDefault().post(CollectionEvent.GOODS_UPDATE_FOLDER);
                                } else {
                                    ToastUtil.showToast(jsonObject.optString("msg"));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            mDialog.dissmissDialog();
                            ToastUtil.showToast("修改收藏夹失败");
                        }

                        @Override
                        public void onComplete() {
                            Log.i("修改收藏夹完成");
                            mDialog.dissmissDialog();
                        }
                    });
        } catch (Exception e) {
            mDialog.dissmissDialog();
            e.printStackTrace();
        }
    }

    private void getFaceImageFile(String imgUrl) {
        ServerApi.getFile(imgUrl, null, null)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        Log.i("修改收藏夹开始请求");
                        mDialog.showProgressDialog("加载中……", null);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<File>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(Response<File> response) {
//                        mDialog.dissmissDialog();
                        if (TextUtils.equals("information", fromPage))
                            updateInformationFolder(response.body());
                        else
                            updateGoodsFolder(response.body());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mDialog.dissmissDialog();
                        ToastUtil.showToast("修改收藏夹失败");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("修改收藏夹完成");
//                        mDialog.dissmissDialog();
                    }
                });
    }

    /**
     * 切换选中封面图片的背景边框
     *
     * @param index
     */
    private void changeFaceBG(int index) {
        switch (selectIndex) {
            case 1:
//                collectionAddFolderFace1Layout.setBackgroundResource(R.drawable.bg_xuanzhongk);
                collection_add_folder_selected1_img.setImageResource(R.drawable.bg_wdcsjb_wxz);
                break;
            case 2:
//                collectionAddFolderFace2Layout.setBackgroundResource(R.drawable.bg_xuanzhongk);
                collection_add_folder_selected2_img.setImageResource(R.drawable.bg_wdcsjb_wxz);
                break;
            case 3:
//                collectionAddFolderFace3Layout.setBackgroundResource(R.drawable.bg_xuanzhongk);
                collection_add_folder_selected3_img.setImageResource(R.drawable.bg_wdcsjb_wxz);
                break;
            case 4:
//                collectionAddFolderFace4Layout.setBackgroundResource(R.drawable.bg_xuanzhongk);
                collection_add_folder_selected4_img.setImageResource(R.drawable.bg_wdcsjb_wxz);
                break;
            case 5:
//                collectionAddFolderFace5Layout.setBackgroundResource(R.drawable.bg_xuanzhongk);
                collection_add_folder_selected5_img.setImageResource(R.drawable.bg_wdcsjb_wxz);
                break;
            case 6:
//                collectionAddFolderFace6Layout.setBackgroundResource(R.drawable.bg_xuanzhongk);
                collection_add_folder_selected6_img.setImageResource(R.drawable.bg_wdcsjb_wxz);
                break;
        }

        switch (index) {
            case 1:
//                collectionAddFolderFace1Layout.setBackgroundResource(R.drawable.bg_xuanzhongk_sel);
                collection_add_folder_selected1_img.setImageResource(R.drawable.bg_wdscjb_xz);
                break;
            case 2:
//                collectionAddFolderFace2Layout.setBackgroundResource(R.drawable.bg_xuanzhongk_sel);
                collection_add_folder_selected2_img.setImageResource(R.drawable.bg_wdscjb_xz);
                break;
            case 3:
//                collectionAddFolderFace3Layout.setBackgroundResource(R.drawable.bg_xuanzhongk_sel);
                collection_add_folder_selected3_img.setImageResource(R.drawable.bg_wdscjb_xz);
                break;
            case 4:
//                collectionAddFolderFace4Layout.setBackgroundResource(R.drawable.bg_xuanzhongk_sel);
                collection_add_folder_selected4_img.setImageResource(R.drawable.bg_wdscjb_xz);
                break;
            case 5:
//                collectionAddFolderFace5Layout.setBackgroundResource(R.drawable.bg_xuanzhongk_sel);
                collection_add_folder_selected5_img.setImageResource(R.drawable.bg_wdscjb_xz);
                break;
            case 6:
//                collectionAddFolderFace6Layout.setBackgroundResource(R.drawable.bg_xuanzhongk_sel);
                collection_add_folder_selected6_img.setImageResource(R.drawable.bg_wdscjb_xz);
                break;
        }

        selectIndex = index;
    }

}
