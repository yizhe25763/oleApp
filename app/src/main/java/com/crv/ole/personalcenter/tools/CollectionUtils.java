package com.crv.ole.personalcenter.tools;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crv.ole.BaseApplication;
import com.crv.ole.R;
import com.crv.ole.information.requestmodel.RequestCommentOrLike;
import com.crv.ole.net.RequestData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.personalcenter.model.CollectionFolderListData;
import com.crv.ole.personalcenter.model.CollectionGoodsFolderListData;
import com.crv.ole.personalcenter.model.CollectionResultData;
import com.crv.ole.personalcenter.requestmodel.RequestCollectionGoodsData;
import com.crv.ole.personalcenter.requestmodel.RequestCollectionInformationData;
import com.crv.ole.utils.LoadImageUtil;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.crv.ole.utils.ToolUtils;
import com.crv.ole.utils.fileupload.UploadPhotoHelper;
import com.crv.sdk.dialog.FragmentDialog;
import com.crv.sdk.dialog.IDialog;
import com.crv.sdk.utils.DisplayUtil;
import com.crv.sdk.utils.FileUtils;
import com.crv.sdk.utils.PreferencesHelper;
import com.crv.sdk.utils.TextUtil;
import com.lzy.okgo.model.HttpParams;
import com.vondear.rxtools.view.dialog.RxDialog;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 作用描述：收藏方面的通用调用工具类
 * 这是一个单利类
 * 创建者： wj_wsf
 * 创建时间： 2017/7/17 11:25.
 */

public class CollectionUtils {
    private Context context;
    private CollectionTypeEnum collectionEnum;
    private List<String> ids;
    private String id;
    private String informationType;
    private IDialog mDialog;
    private RxDialog dialog;
    private int selectIndex = 0;
    private UploadPhotoHelper mUploadPhotoHelper;
    private String selectImgPath;
    private OnCollectionListener onCollectionListener;
    //H5界面调用商品收藏时使用
    private OnCollectionListener2 onCollectionListener2;

    private LinearLayout collectionDialogAddLayout;
    private LinearLayout collectionDialogContentLayout;
    private TextView collectionCancel;
    private LinearLayout collectionDialogNewLayout;
    private TextView collectionDialogNewCancle;
    private EditText collectionAddFolderName;
    //    private LinearLayout collectionDialogNewContentLayout;
    private TextView collectionNewConfirm;
    RelativeLayout collectionAddFolderFace1Layout;
    ImageView collectionAddFolderFace1Img;
    RelativeLayout collectionAddFolderFace2Layout;
    ImageView collectionAddFolderFace2Img;
    RelativeLayout collectionAddFolderFace3Layout;
    ImageView collectionAddFolderFace3Img;
    RelativeLayout collectionAddFolderFace4Layout;
    ImageView collectionAddFolderFace4Img;
    RelativeLayout collectionAddFolderFace5Layout;
    ImageView collectionAddFolderFace5Img;
    RelativeLayout collectionAddFolderFace6Layout;
    ImageView collectionAddFolderFace6Img;

    ImageView collection_add_folder_selected1_img;
    ImageView collection_add_folder_selected2_img;
    ImageView collection_add_folder_selected3_img;
    ImageView collection_add_folder_selected4_img;
    ImageView collection_add_folder_selected5_img;
    ImageView collection_add_folder_selected6_img;

    @SuppressWarnings("deprecation")
    private void assignViews(View v) {
        collectionDialogAddLayout = (LinearLayout) v.findViewById(R.id.collection_dialog_add_layout);
        collectionDialogContentLayout = (LinearLayout) v.findViewById(R.id.collection_dialog_content_layout);
        collectionCancel = (TextView) v.findViewById(R.id.collection_cancel);
        collectionDialogNewLayout = (LinearLayout) v.findViewById(R.id.collection_dialog_new_layout);
        collectionDialogNewCancle = (TextView) v.findViewById(R.id.collection_dialog_new_cancle);
        collectionAddFolderName = (EditText) v.findViewById(R.id.collection_add_folder_name);
//        collectionDialogNewContentLayout = (LinearLayout) v.findViewById(R.id.collection_dialog_new_content_layout);
        collectionNewConfirm = (TextView) v.findViewById(R.id.collection_new_confirm);

        collectionAddFolderFace1Layout = (RelativeLayout) v.findViewById(R.id.collection_add_folder_face1_layout);
        collectionAddFolderFace2Layout = (RelativeLayout) v.findViewById(R.id.collection_add_folder_face2_layout);
        collectionAddFolderFace3Layout = (RelativeLayout) v.findViewById(R.id.collection_add_folder_face3_layout);
        collectionAddFolderFace4Layout = (RelativeLayout) v.findViewById(R.id.collection_add_folder_face4_layout);
        collectionAddFolderFace5Layout = (RelativeLayout) v.findViewById(R.id.collection_add_folder_face5_layout);
        collectionAddFolderFace6Layout = (RelativeLayout) v.findViewById(R.id.collection_add_folder_face6_layout);

        collectionAddFolderFace1Layout.setOnClickListener(onClickListener);
        collectionAddFolderFace2Layout.setOnClickListener(onClickListener);
        collectionAddFolderFace3Layout.setOnClickListener(onClickListener);
        collectionAddFolderFace4Layout.setOnClickListener(onClickListener);
        collectionAddFolderFace5Layout.setOnClickListener(onClickListener);
        collectionAddFolderFace6Layout.setOnClickListener(onClickListener);

        collection_add_folder_selected1_img = (ImageView) v.findViewById(R.id.collection_add_folder_selected1_img);
        collection_add_folder_selected2_img = (ImageView) v.findViewById(R.id.collection_add_folder_selected2_img);
        collection_add_folder_selected3_img = (ImageView) v.findViewById(R.id.collection_add_folder_selected3_img);
        collection_add_folder_selected4_img = (ImageView) v.findViewById(R.id.collection_add_folder_selected4_img);
        collection_add_folder_selected5_img = (ImageView) v.findViewById(R.id.collection_add_folder_selected5_img);
        collection_add_folder_selected6_img = (ImageView) v.findViewById(R.id.collection_add_folder_selected6_img);

        collectionAddFolderFace1Img = (ImageView) v.findViewById(R.id.collection_add_folder_face1_img);
        collectionAddFolderFace2Img = (ImageView) v.findViewById(R.id.collection_add_folder_face2_img);
        collectionAddFolderFace3Img = (ImageView) v.findViewById(R.id.collection_add_folder_face3_img);
        collectionAddFolderFace4Img = (ImageView) v.findViewById(R.id.collection_add_folder_face4_img);
        collectionAddFolderFace5Img = (ImageView) v.findViewById(R.id.collection_add_folder_face5_img);
        collectionAddFolderFace6Img = (ImageView) v.findViewById(R.id.collection_add_folder_face6_img);
        try {
            collectionAddFolderFace1Img.setBackground(new BitmapDrawable(BitmapFactory.decodeStream(context.getAssets().open("collection_folder_face_1.jpg"))));
            collectionAddFolderFace2Img.setBackground(new BitmapDrawable(BitmapFactory.decodeStream(context.getAssets().open("collection_folder_face_2.jpg"))));
            collectionAddFolderFace3Img.setBackground(new BitmapDrawable(BitmapFactory.decodeStream(context.getAssets().open("collection_folder_face_3.jpg"))));
            collectionAddFolderFace4Img.setBackground(new BitmapDrawable(BitmapFactory.decodeStream(context.getAssets().open("collection_folder_face_4.jpg"))));
            collectionAddFolderFace5Img.setBackground(new BitmapDrawable(BitmapFactory.decodeStream(context.getAssets().open("collection_folder_face_5.jpg"))));

            ViewGroup.LayoutParams layoutParams2 = collectionAddFolderFace2Img.getLayoutParams();
            ViewGroup.LayoutParams layoutParams3 = collectionAddFolderFace3Img.getLayoutParams();
            ViewGroup.LayoutParams layoutParams4 = collectionAddFolderFace4Img.getLayoutParams();
            ViewGroup.LayoutParams layoutParams5 = collectionAddFolderFace5Img.getLayoutParams();
            ViewGroup.LayoutParams layoutParams1 = collectionAddFolderFace1Img.getLayoutParams();
            switch (ToolUtils.getCropMaxType(BaseApplication.getDeviceWidth())) {
                case 1:
                    layoutParams2.height = 286;
                    layoutParams3.height = 286;
                    layoutParams4.height = 286;
                    layoutParams5.height = 286;
                    layoutParams1.height = 286;
                    layoutParams2.width = 218;
                    layoutParams3.width = 218;
                    layoutParams4.width = 218;
                    layoutParams5.width = 218;
                    layoutParams1.width = 218;
                    break;
                case 2:
                    layoutParams2.height = 429;
                    layoutParams3.height = 429;
                    layoutParams4.height = 429;
                    layoutParams5.height = 429;
                    layoutParams1.height = 429;
                    layoutParams2.width = 327;
                    layoutParams3.width = 327;
                    layoutParams4.width = 327;
                    layoutParams5.width = 327;
                    layoutParams1.width = 327;
                    break;
                case 3:
                    layoutParams2.height = 572;
                    layoutParams3.height = 572;
                    layoutParams4.height = 572;
                    layoutParams5.height = 572;
                    layoutParams1.height = 572;
                    layoutParams2.width = 436;
                    layoutParams3.width = 436;
                    layoutParams4.width = 436;
                    layoutParams5.width = 436;
                    layoutParams1.width = 436;
                    break;
            }
            collectionAddFolderFace2Img.setLayoutParams(layoutParams2);
            collectionAddFolderFace3Img.setLayoutParams(layoutParams3);
            collectionAddFolderFace4Img.setLayoutParams(layoutParams4);
            collectionAddFolderFace5Img.setLayoutParams(layoutParams5);
            collectionAddFolderFace1Img.setLayoutParams(layoutParams1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public OnCollectionListener getOnCollectionListener() {
        return onCollectionListener;
    }

    public void setOnCollectionListener(OnCollectionListener onCollectionListener) {
        this.onCollectionListener = onCollectionListener;
    }

    public OnCollectionListener2 getOnCollectionListener2() {
        return onCollectionListener2;
    }

    public void setOnCollectionListener2(OnCollectionListener2 onCollectionListener2) {
        this.onCollectionListener2 = onCollectionListener2;
    }

    private static class SingletonHolder {
        private static final CollectionUtils INSTANCE = new CollectionUtils();
    }

    private CollectionUtils() {
    }

    public static final CollectionUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 公共调用，显示收藏夹对话框，商品调用此方法
     *
     * @param activity       必须是BaseActivity
     * @param collectionEnum 收藏的类型，商品收藏或者图文咨询收藏
     * @param id             资讯传资讯ID，商品传商品ID
     */
    public void showCollectionDialog(Activity activity, CollectionTypeEnum collectionEnum, List<String> id) {
        mDialog = new FragmentDialog(activity);
        Log.e("CollectionTypeEnum：" + collectionEnum);
        mUploadPhotoHelper = new UploadPhotoHelper(activity);
        this.context = activity;
        this.collectionEnum = collectionEnum;
        this.ids = id;
        initDialog();
        refreshDialog();
    }

    /**
     * 公共调用，显示收藏夹对话框，资讯调用此方法
     *
     * @param activity       必须是BaseActivity
     * @param collectionEnum 收藏的类型，商品收藏或者图文咨询收藏
     * @param id             资讯传资讯ID
     */
    public void showCollectionDialog(Activity activity, CollectionTypeEnum collectionEnum, String id, String informationType) {
        mDialog = new FragmentDialog(activity);
        Log.e("CollectionTypeEnum：" + collectionEnum);
        mUploadPhotoHelper = new UploadPhotoHelper(activity);
        this.context = activity;
        this.collectionEnum = collectionEnum;
        this.id = id;
        this.informationType = informationType;
        initDialog();
        refreshDialog();
    }

    private void initDialog() {
        View view = LayoutInflater.from(context).inflate(R.layout.collection_dialog_layout, null);
        assignViews(view);

        collectionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dissmissDialog();
            }
        });
        collectionDialogNewCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collectionDialogAddLayout.setVisibility(View.VISIBLE);
                collectionDialogNewLayout.setVisibility(View.GONE);
            }
        });
        collectionNewConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!TextUtil.isEmpty(collectionAddFolderName.getText().toString().trim()))
                        if (!TextUtil.isEmpty(selectImgPath) || (selectIndex > 0 && selectIndex < 6)) {
                            if (collectionEnum == CollectionTypeEnum.INFORMATION_COLLECTION) {
                                if (selectIndex == 6)
                                    createInformationFolder(selectImgPath);
                                else {
                                    createInformationFolder2("collection_folder_face_" + selectIndex + ".jpg");
                                }
                            } else {
                                if (selectIndex == 6)
                                    createGoodsFolder(selectImgPath);
                                else {
                                    createGoodsFolder2("collection_folder_face_" + selectIndex + ".jpg");
                                }
                            }
                        } else
                            ToastUtil.showToast("请选择文件夹封面");
                    else
                        ToastUtil.showToast("请输入文件夹名称");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        dialog = new RxDialog(context, 0, Gravity.BOTTOM);
        dialog.setFullScreenWidth();
        dialog.setContentView(view);
        dialog.getWindow().setWindowAnimations(R.style.dialogBottomStyle);


        selectImgPath = "";

    }

    /**
     * 重新获取收藏相关数据,对话框不会消失，直接刷新数据
     */
    public void refreshDialog() {
        collectionDialogAddLayout.setVisibility(View.VISIBLE);
        collectionDialogNewLayout.setVisibility(View.GONE);

        if (collectionEnum == CollectionTypeEnum.INFORMATION_COLLECTION) {
            getInformationFolderListData();
        } else {
            getGoodsFolderData();
        }

    }

    /**
     * 刷新对话框里边的View
     */
    private void refreshDialogView(List<CollectionFolderListData.FolderData> dataList) {
        if (dataList == null)
            dataList = new ArrayList<>();

        CollectionFolderListData.FolderData folderData = new CollectionFolderListData.FolderData();
        folderData.setArticleFileName("");
        folderData.setArticleFileImage(R.drawable.collection_add_folder_bg + "");
        dataList.add(folderData);

        collectionDialogContentLayout.removeAllViews();

        int height = 286, width = 218;
        switch (ToolUtils.getCropMaxType(BaseApplication.getDeviceWidth())) {
            case 1:
                height = 286;
                width = 218;
                break;
            case 2:
                height = 429;
                width = 327;
                break;
            case 3:
                height = 572;
                width = 436;
                break;
        }

        for (int i = 0; i < dataList.size(); i++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    width, height);
            layoutParams.setMargins(
                    DisplayUtil.dip2px(context, 5), 0,
                    DisplayUtil.dip2px(context, 5), 0);
            collectionDialogContentLayout.addView(getItemView(dataList.get(i), i == dataList.size() - 1), layoutParams);
        }

        if (!dialog.isShowing())
            dialog.show();

    }

    /**
     * 刷新对话框里边的View
     */
    private void refreshGoodsDialogView(List<CollectionGoodsFolderListData.GoodsFolderData> dataList) {
        if (dataList == null)
            dataList = new ArrayList<>();

        CollectionGoodsFolderListData.GoodsFolderData folderData = new CollectionGoodsFolderListData.GoodsFolderData();
        folderData.setFavoriteClassName("");
        folderData.setImgUrl(R.drawable.collection_add_folder_bg + "");
        dataList.add(folderData);

        collectionDialogContentLayout.removeAllViews();

        int height = 286, width = 218;
        switch (ToolUtils.getCropMaxType(BaseApplication.getDeviceWidth())) {
            case 1:
                height = 286;
                width = 218;
                break;
            case 2:
                height = 429;
                width = 327;
                break;
            case 3:
                height = 572;
                width = 436;
                break;
        }

        for (int i = 0; i < dataList.size(); i++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    width, height);
            layoutParams.setMargins(
                    DisplayUtil.dip2px(context, 5), 0,
                    DisplayUtil.dip2px(context, 5), 0);
            collectionDialogContentLayout.addView(getGoodsItemView(dataList.get(i), i == dataList.size() - 1), layoutParams);
        }

        if (!dialog.isShowing())
            dialog.show();
    }

    /**
     * 根据接口返回数据，创建文件夹列表中item的view
     *
     * @param data
     * @param isLast
     * @return
     */
    private View getItemView(CollectionFolderListData.FolderData data, final boolean isLast) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.collection_item_layout, null);

        FrameLayout collection_item_layout = (FrameLayout) itemView.findViewById(R.id.collection_item_layout);
        TextView collection_item_name_txt = (TextView) itemView.findViewById(R.id.collection_item_name_txt);

        if (isLast)
            collection_item_layout.setBackgroundResource(R.drawable.collection_add_folder_bg_selector);
        else
            LoadImageUtil.getInstance().loadBackground(collection_item_layout, data.getArticleFileImage());

        ViewGroup.LayoutParams layoutParams = collection_item_layout.getLayoutParams();
        switch (ToolUtils.getCropMaxType(BaseApplication.getDeviceWidth())) {
            case 1:
                layoutParams.height = 286;
                layoutParams.width = 218;
                break;
            case 2:
                layoutParams.height = 429;
                layoutParams.width = 327;
                break;
            case 3:
                layoutParams.height = 572;
                layoutParams.width = 436;
                break;
        }
        collection_item_layout.setLayoutParams(layoutParams);

        collection_item_name_txt.setText(data.getArticleFileName());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLast) {
                    //新建文件夹
                    collectionDialogAddLayout.setVisibility(View.GONE);
                    collectionDialogNewLayout.setVisibility(View.VISIBLE);
                    changeFaceBG(1);
                } else {
                    //执行收藏操作
                    addInformationCollection(data.getArticleFileName());
                }
            }
        });

        return itemView;
    }

    /**
     * 根据接口返回数据，创建文件夹列表中item的view
     *
     * @param data
     * @param isLast
     * @return
     */
    private View getGoodsItemView(CollectionGoodsFolderListData.GoodsFolderData data,
                                  final boolean isLast) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.collection_item_layout, null);

        FrameLayout collection_item_layout = (FrameLayout) itemView.findViewById(R.id.collection_item_layout);
        TextView collection_item_name_txt = (TextView) itemView.findViewById(R.id.collection_item_name_txt);

        if (isLast) {
            collection_item_layout.setBackgroundResource(R.drawable.collection_add_folder_bg_selector);
        } else {
            LoadImageUtil.getInstance().loadBackground(collection_item_layout, data.getImgUrl());
        }

        ViewGroup.LayoutParams layoutParams = collection_item_layout.getLayoutParams();
        switch (ToolUtils.getCropMaxType(BaseApplication.getDeviceWidth())) {
            case 1:
                layoutParams.height = 286;
                layoutParams.width = 218;
                break;
            case 2:
                layoutParams.height = 429;
                layoutParams.width = 327;
                break;
            case 3:
                layoutParams.height = 572;
                layoutParams.width = 436;
                break;
        }
        collection_item_layout.setLayoutParams(layoutParams);

        collection_item_name_txt.setText(data.getFavoriteClassName());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLast) {
                    //新建文件夹
                    collectionDialogAddLayout.setVisibility(View.GONE);
                    collectionDialogNewLayout.setVisibility(View.VISIBLE);
                    changeFaceBG(1);
                } else {
                    //执行收藏操作
                    addGoodsCollection(data.getId());
                }
            }
        });

        return itemView;
    }

    /**
     * 从接口获取资讯收藏文件夹列表
     */
    private void getInformationFolderListData() {
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.INFORMATION_COLLECTION_LIST_URL_ID);
        requestData.setREQUEST_DATA(new RequestCollectionInformationData("", 1, 10));

        ServerApi.request(false, requestData, CollectionFolderListData.class,
                new PreferencesHelper(context).getString(OleConstants.KEY.REQUEST_SIGN_KEY))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        Log.i("获取收藏列表开始请求");
                        mDialog.showProgressDialog("加载中……", null);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CollectionFolderListData>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(CollectionFolderListData response) {
                        if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
//                            Log.i("获取收藏列表成功:" + json);
                            refreshDialogView(response.getRETURN_DATA().getFileList());

                        } else {
                            mDialog.dissmissDialog();
                            ToastUtil.showToast(response.getRETURN_DESC());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        mDialog.dissmissDialog();
                        ToastUtil.showToast("获取收藏列表失败");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("获取收藏列表完成");
                        mDialog.dissmissDialog();
                    }
                });
    }

    /**
     * 获取商品收藏文件夹列表数据
     */
    private void getGoodsFolderData() {
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.GOODS_COLLECTION_FOLDER_LIST_URL_ID);
        requestData.setREQUEST_DATA(new RequestCollectionInformationData("", 1, 10));

        ServerApi.request(false, requestData, CollectionGoodsFolderListData.class,
                new PreferencesHelper(context).getString(OleConstants.KEY.REQUEST_SIGN_KEY))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        Log.i("获取收藏列表开始请求");
                        mDialog.showProgressDialog("加载中……", null);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CollectionGoodsFolderListData>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(CollectionGoodsFolderListData response) {
                        mDialog.dissmissDialog();
                        if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                            refreshGoodsDialogView(response.getRETURN_DATA().getFavorTypeList());

                        } else {
                            ToastUtil.showToast(response.getRETURN_DESC());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mDialog.dissmissDialog();
                        ToastUtil.showToast("获取收藏列表失败");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("获取收藏列表完成");
                        mDialog.dissmissDialog();
                    }
                });
    }

    /**
     * 添加资讯收藏文件夹
     */
    private void createInformationFolder(String imgPath) {
        try {
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
                                    refreshDialog();
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
            e.printStackTrace();
        }
    }

    private void createInformationFolder2(String assetFileName) {
        mDialog.showProgressDialog("加载中……", null);
        try {
            Bitmap bmp = BitmapFactory.decodeStream(context.getAssets().open(assetFileName));
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
                                    refreshDialog();
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
    private void createGoodsFolder(String imgPath) {
        try {
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

                        }

                        @Override
                        public void onNext(String response) {
                            mDialog.dissmissDialog();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (OleConstants.REQUES_SUCCESS.equals(jsonObject.optString("code"))) {
                                    Log.i("添加收藏夹成功");
                                    refreshDialog();
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
            e.printStackTrace();
        }
    }

    /**
     * 添加商品收藏夹
     */
    private void createGoodsFolder2(String assetFileName) {
//        mDialog.showProgressDialog("加载中……", null);
        try {
            Bitmap bmp = BitmapFactory.decodeStream(context.getAssets().open(assetFileName));
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
                                    refreshDialog();
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
     * 添加资讯收藏
     */
    private void addInformationCollection(String folderName) {
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.COMMENT_LIKE_ID);

        RequestCommentOrLike requestCommentOrLike = new RequestCommentOrLike();
        requestCommentOrLike.setIsComment("3");
        requestCommentOrLike.setArticleFileName(folderName);
        requestCommentOrLike.setArticleId(id);
        requestCommentOrLike.setArticleType(informationType);

        requestData.setREQUEST_DATA(requestCommentOrLike);

        ServerApi.request(false, requestData, CollectionResultData.class,
                new PreferencesHelper(context).getString(OleConstants.KEY.REQUEST_SIGN_KEY))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        Log.i("添加资讯收藏开始请求");
                        mDialog.showProgressDialog("加载中……", null);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CollectionResultData>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(CollectionResultData response) {
                        mDialog.dissmissDialog();
                        if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                            ToastUtil.showToast(response.getRETURN_DESC());
                            Log.i("添加资讯收藏成功");
                            if (onCollectionListener != null)
                                onCollectionListener.onCollection(response.getRETURN_DATA().getLikes(), response.getRETURN_DATA().getCollections());
                            dissmissDialog();
                        } else {
//                            dismissProgressDialog();
                            ToastUtil.showToast(response.getRETURN_DESC());
                            if (onCollectionListener != null)
                                onCollectionListener.onFailed(response.getRETURN_DESC());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mDialog.dissmissDialog();
                        ToastUtil.showToast("添加资讯收藏失败");
                        if (onCollectionListener != null)
                            onCollectionListener.onFailed(e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i("添加资讯收藏完成");
                        mDialog.dissmissDialog();
                    }
                });
    }

    /**
     * 添加商品收藏
     */
    private void addGoodsCollection(String folderId) {
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.GOODS_COLLECTION_ADD_TO_FOLDER_URL_ID);
        RequestCollectionGoodsData requestParamData = new RequestCollectionGoodsData();
        requestParamData.setFavorType(folderId);
        StringBuilder objId = new StringBuilder();
        for (String string : ids) {
            objId.append(string).append(",");
        }
        requestParamData.setObjId(objId.toString());
        requestData.setREQUEST_DATA(requestParamData);

        ServerApi.request(false, requestData, CollectionResultData.class,
                new PreferencesHelper(context).getString(OleConstants.KEY.REQUEST_SIGN_KEY))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        Log.i("添加商品收藏开始请求");
                        mDialog.showProgressDialog("加载中……", null);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CollectionResultData>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(CollectionResultData response) {
                        mDialog.dissmissDialog();

                        if (onCollectionListener2 != null) {
                            onCollectionListener2.onCollection(response);
                            dissmissDialog();
                        } else {
                            if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                                ToastUtil.showToast(response.getRETURN_DESC());
                                Log.i("添加商品收藏成功");
                                if (onCollectionListener != null)
                                    onCollectionListener.onCollection(response.getRETURN_DATA().getLikes(), response.getRETURN_DATA().getCollections());
                                dissmissDialog();
                            } else {
                                ToastUtil.showToast(response.getRETURN_DESC());
                                if (onCollectionListener != null)
                                    onCollectionListener.onFailed(response.getRETURN_DESC());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mDialog.dissmissDialog();
                        ToastUtil.showToast("添加商品收藏失败");
                        if (onCollectionListener != null)
                            onCollectionListener.onFailed(e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i("添加商品收藏完成");
                        mDialog.dissmissDialog();
                    }
                });
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.collection_add_folder_face1_layout:
                    if (selectIndex != 1)
                        changeFaceBG(1);
                    break;
                case R.id.collection_add_folder_face2_layout:
                    if (selectIndex != 2)
                        changeFaceBG(2);
                    break;
                case R.id.collection_add_folder_face3_layout:
                    if (selectIndex != 3)
                        changeFaceBG(3);
                    break;
                case R.id.collection_add_folder_face4_layout:
                    if (selectIndex != 4)
                        changeFaceBG(4);
                    break;
                case R.id.collection_add_folder_face5_layout:
                    if (selectIndex != 5)
                        changeFaceBG(5);
                    break;
                case R.id.collection_add_folder_face6_layout:
                    if (TextUtils.isEmpty(selectImgPath)) {
                        dissmissDialog();
                        mUploadPhotoHelper.getPermission();
                    } else {
                        if (selectIndex != 6) {
                            changeFaceBG(6);
                        } else {
                            dissmissDialog();
                            mUploadPhotoHelper.getPermission();
                        }
                    }
                    break;
            }
        }
    };

    /**
     * 切换选中封面图片的背景边框
     *
     * @param index
     */
    private void changeFaceBG(int index) {
        switch (selectIndex) {
            case 1:
//                collectionAddFolderFace1Layout.setBackgroundResource(R.drawable.bg_xuanzhongk);
                collection_add_folder_selected1_img.setImageResource(R.drawable.bg_wdscjb_wxz);
                break;
            case 2:
//                collectionAddFolderFace2Layout.setBackgroundResource(R.drawable.bg_xuanzhongk);
                collection_add_folder_selected2_img.setImageResource(R.drawable.bg_wdscjb_wxz);
                break;
            case 3:
//                collectionAddFolderFace3Layout.setBackgroundResource(R.drawable.bg_xuanzhongk);
                collection_add_folder_selected3_img.setImageResource(R.drawable.bg_wdscjb_wxz);
                break;
            case 4:
//                collectionAddFolderFace4Layout.setBackgroundResource(R.drawable.bg_xuanzhongk);
                collection_add_folder_selected4_img.setImageResource(R.drawable.bg_wdscjb_wxz);
                break;
            case 5:
//                collectionAddFolderFace5Layout.setBackgroundResource(R.drawable.bg_xuanzhongk);
                collection_add_folder_selected5_img.setImageResource(R.drawable.bg_wdscjb_wxz);
                break;
            case 6:
//                collectionAddFolderFace6Layout.setBackgroundResource(R.drawable.bg_xuanzhongk);
                collection_add_folder_selected6_img.setImageResource(R.drawable.bg_wdscjb_wxz);
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

    public String getSelectImgPath() {
        return selectImgPath;
    }

    /**
     * 当选择完图片时候，在activity中需要调用一次这个方法
     *
     * @param selectImgPath
     */
    public void setSelectImgPath(String selectImgPath) {
        this.selectImgPath = selectImgPath;
        LoadImageUtil.getInstance().loadImage(collectionAddFolderFace6Img, selectImgPath);
        changeFaceBG(6);
        dialog.show();
    }

    public void dissmissDialog() {
        if (dialog != null)
            dialog.dismiss();
    }

    /**
     * 枚举类，表示收藏来自商品或者图文资讯
     */
    public enum CollectionTypeEnum {
        GOODS_COLLECTION, INFORMATION_COLLECTION
    }

    public interface OnCollectionListener {
        void onCollection(int like, int collections);

        void onFailed(String msg);
    }

    /**
     * H5页面调用商品收藏时回调接口
     */
    public interface OnCollectionListener2 {
        /**
         * H5页面调用商品收藏时回调方法
         * @param responseObject
         */
        void onCollection(Object responseObject);
    }
}
