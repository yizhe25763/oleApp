package com.crv.ole.information.activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.information.model.LikeBean;
import com.crv.ole.information.model.SpecialDerailResult;
import com.crv.ole.information.requestmodel.InfoBeanRequest;
import com.crv.ole.information.view.NewWebView;
import com.crv.ole.login.activity.LoginActivity;
import com.crv.ole.net.RequestData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.personalcenter.tools.CollectionUtils;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.crv.ole.utils.ToolUtils;
import com.crv.ole.utils.UmengUtils;
import com.crv.ole.utils.fileupload.CapturePhotoHelper;
import com.crv.ole.utils.fileupload.FileUtils;
import com.crv.ole.utils.fileupload.UploadPhotoHelper;
import com.crv.ole.utils.fileupload.UploadUtils;
import com.google.gson.Gson;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 专题详情
 *
 */

public class SpecialDetailActivity extends BaseActivity {
    //    protected RecyclerViewPager mRecyclerView;
    private LinearLayout bottom;
    private String mimeType = "text/html";
    private String encoding = "utf-8";
    private NewWebView mWebView;
    private Boolean isDz;//点赞
    private Boolean isSc; //收藏
    private String id;//文章id

    private TextView plTxt;//评论
    private TextView scTxt;//收藏
    private TextView dzTxt;//点赞
    private ImageButton scIbtn;//收藏
    private ImageButton dzIbtn;//点赞

    private String DZ = "1";//点赞
    private String QXDZ = "2";//取消点赞
    private String SC = "3";//收藏
    private String QXSC = "4";//取消收藏
    private String ID;
    private UploadPhotoHelper mUploadPhotoHelper;
    private String shareUrl;
    private String sharetitle;
    private String shareContent;
    private String shareImage;
    private ValueAnimator apperaAnim;

    /**
     * 点赞收藏相关操作
     *
     * @param id 收藏的文件夹名字
     * @param id
     */
    private void addCommentOrLike(String id, String contextSS) {
        {
            RequestData requestData = new RequestData();
            requestData.getRequestAttrsInstance().setApi_ID(OleConstants.COMMENT_LIKE_ID);
            Map<String, String> requestMap = new HashMap<>();
            requestMap.put("isComment", id);
            requestMap.put("articleId", ID);
            if (!contextSS.equals("")) {
                requestMap.put("articleFileName", contextSS);
            }
            requestData.setREQUEST_DATA(requestMap);
            ServerApi.request(false, requestData, LikeBean.class, OleConstants.sign)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(@NonNull Disposable disposable) throws Exception {
                            mDialog.showProgressDialog("加载中……", null);

                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<LikeBean>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                            addDisposable(d);
                        }

                        @Override
                        public void onNext(LikeBean response) {
                            if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                                dismissProgressDialog();
                                if (id.equals(DZ)) {
                                    isDz = true;
                                    dzIbtn.setBackground(getResources().getDrawable(R.drawable.btn_dbldz_ydz));
                                }
                                if (id.equals(QXDZ)) {
                                    isDz = false;
                                    dzIbtn.setBackground(getResources().getDrawable(R.drawable.td_dz_selector));
                                }

                                if (id.equals(QXSC)) {
                                    isSc = false;
                                    scIbtn.setBackground(getResources().getDrawable(R.drawable.td_sc_selector));

                                }
                                Log.e("操作：" + new Gson().toJson(response));
                                scTxt.setText(response.getRETURN_DATA().getCollections() + "");
                                dzTxt.setText(response.getRETURN_DATA().getLikes() + "");
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

    }

    /**
     * 获取文章详情
     *
     * @param id 文章id
     */
    private void getInfo(String id) {
        RequestData requestData = new RequestData();
        requestData.getRequestAttrsInstance().setApi_ID(OleConstants.DETAILS);
        InfoBeanRequest bean = new InfoBeanRequest();
        bean.setId(id);
        requestData.setREQUEST_DATA(bean);
        ServerApi.request(false, requestData, SpecialDerailResult.class, OleConstants.sign)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mDialog.showProgressDialog("加载中……", null);

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SpecialDerailResult>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(SpecialDerailResult response) {
                        if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                            dismissProgressDialog();
                            mWebView.loadDataWithBaseURL(null, response.getRETURN_DATA().getInfoDetail().getContent(), mimeType, encoding, null);




                            isDz = response.getRETURN_DATA().getInfoDetail().isWhetherLike();
                            isSc = response.getRETURN_DATA().getInfoDetail().isWhetherFavorite();
                            plTxt.setText(response.getRETURN_DATA().getInfoDetail().getCommentCount() + "");
                            scTxt.setText(response.getRETURN_DATA().getInfoDetail().getFavoriteCount() + "");
                            dzTxt.setText(response.getRETURN_DATA().getInfoDetail().getLikeCount() + "");
                            //
                            shareUrl = "http://www.baidu.com";
                            sharetitle = response.getRETURN_DATA().getInfoDetail().getTitle();
                            shareContent = response.getRETURN_DATA().getInfoDetail().getContent();
                            shareImage = response.getRETURN_DATA().getInfoDetail().getCoverImg();
                            if (isDz) {
                                dzIbtn.setBackground(getResources().getDrawable(R.drawable.btn_dbldz_ydz));
                            }
                            if (isSc) {
                                scIbtn.setBackground(getResources().getDrawable(R.drawable.btn_dblsc_ysc));
                            }
                            findViewById(R.id.pl_image).setOnClickListener(new View.OnClickListener() {//评论
                                @Override
                                public void onClick(View view) {
                                    if (response.getRETURN_DATA().getInfoDetail().getId() != null && !response.getRETURN_DATA().getInfoDetail().getId().equals("")) {
                                        if (ToolUtils.isLoginStatus(mContext)) {
                                            Intent intent = new Intent();
                                            intent.setClass(SpecialDetailActivity.this, SpecialCommentActivity.class);
                                            intent.putExtra("id", response.getRETURN_DATA().getInfoDetail().getId());
                                            startActivity(intent);
                                        } else {
                                            startActivityWithAnim(LoginActivity.class);
                                        }
                                    }

                                }
                            });
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


    private void initLister() {
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {//返回
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        findViewById(R.id.dz_image).setOnClickListener(new View.OnClickListener() {//点赞
            @Override
            public void onClick(View view) {
                if (ToolUtils.isLoginStatus(mContext)) {
                    if (isDz) {//取消点赞
                        addCommentOrLike(QXDZ, "");
                    } else {//点赞
                        addCommentOrLike(DZ, "");
                    }
                } else {
                    startActivityWithAnim(LoginActivity.class);
                }

            }
        });
        findViewById(R.id.sc_image).setOnClickListener(new View.OnClickListener() {//收藏
            @Override
            public void onClick(View view) {
                if (ToolUtils.isLoginStatus(mContext)) {
                    if (isSc) {//取消收藏
                        addCommentOrLike(QXSC, "");
                    } else {//收藏
                        CollectionUtils.getInstance().showCollectionDialog(SpecialDetailActivity.this,
                                CollectionUtils.CollectionTypeEnum.INFORMATION_COLLECTION, ID, "文章");
                        CollectionUtils.getInstance().setOnCollectionListener(new CollectionUtils.OnCollectionListener() {
                            @Override
                            public void onCollection(int like, int collections) {
                                scTxt.setText(collections + "");
                                dzTxt.setText(like + "");
                                isSc = true;
                                scIbtn.setBackground(getResources().getDrawable(R.drawable.btn_dblsc_ysc));
                                ToastUtil.showToast("成功收藏到专题");
                            }

                            @Override
                            public void onFailed(String msg) {
                                ToastUtil.showToast(msg);
                            }
                        });

                    }
                } else {
                    startActivityWithAnim(LoginActivity.class);
                }

            }
        });
        findViewById(R.id.fx_image).setOnClickListener(new View.OnClickListener() {//分享
            @Override
            public void onClick(View view) {

                UmengUtils.shareUrl(SpecialDetailActivity.this, sharetitle, shareContent, shareUrl, shareImage, R.drawable.logo_ole, new UmengUtils.ShareCallBack() {
                    @Override
                    public void onStart(UmengUtils.ShareType type) {

                    }

                    @Override
                    public void onResult(UmengUtils.ShareType type) {
                        ToastUtil.showToast(type + "分享成功");
                    }

                    @Override
                    public void onError(UmengUtils.ShareType type, Throwable throwable) {
                        ToastUtil.showToast(type + "分享出错");
                    }

                    @Override
                    public void onCancel(UmengUtils.ShareType type) {
                        Log.i("取消分享");
                        ToastUtil.showToast("取消分享");
                    }
                });
            }
        });

        mWebView.setOnScrollChangeListener(new NewWebView.OnScrollChangeListener() {
            @Override
            public void onPageEnd(int l, int t, int oldl, int oldt) {
                bottom.setVisibility(View.GONE);

            }

            @Override
            public void onPageTop(int l, int t, int oldl, int oldt) {
                bottom.setVisibility(View.GONE);


            }


            @Override
            public void onScrollChanged(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if ((scrollY - oldScrollY) > 0) {//向下滑动
                    bottom.setVisibility(View.GONE);
                } else if ((oldScrollY - scrollY) > 0) {//向上滑动
                    bottom.setVisibility(View.VISIBLE);

                }
                mWebView.setAnimationView(bottom);
            }

        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_detail_layout);
        mUploadPhotoHelper = new UploadPhotoHelper(this);
        initView();
        initData();
        initLister();

    }

    private void initData() {
        ID = getIntent().getStringExtra("id");
        mWebView.clearCache(true);
        mWebView.clearHistory();
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        getInfo(ID);
        bottom.setVisibility(View.GONE);
    }

    private void initView() {
        bottom = (LinearLayout) findViewById(R.id.bottom_layout);
        mWebView = (NewWebView) findViewById(R.id.webview);
        bottom = (LinearLayout) findViewById(R.id.bottom_layout);
        plTxt = (TextView) findViewById(R.id.pl_txt);
        //评论
        scTxt = (TextView) findViewById(R.id.sc_txt);
        //收藏
        dzTxt = (TextView) findViewById(R.id.dz_txt);
        //点赞
        scIbtn = (ImageButton) findViewById(R.id.sc_image);
        //收藏
        dzIbtn = (ImageButton) findViewById(R.id.dz_image);
        //点赞
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                        String selectImgPath = mUploadPhotoHelper.getPhoto().getPath();
                        //                        LoadImageUtil.getInstance().loadImage(collectionAddFolderFace1Img, selectImgPath, false);
                        //                        compressAndUploadImg(mUploadPhotoHelper.getPhoto().getPath());
                        CollectionUtils.getInstance().setSelectImgPath(selectImgPath);
                    } else {
                        mUploadPhotoHelper.getPhoto().delete();
                    }
                }
                break;
            default:
                break;
        }
    }
}