package com.crv.ole.information.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crv.ole.BaseApplication;
import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.information.model.InfoBean;
import com.crv.ole.information.model.LikeBean;
import com.crv.ole.information.requestmodel.InfoBeanRequest;
import com.crv.ole.information.view.Pager;
import com.crv.ole.information.view.PagerFactory;
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
import com.crv.sdk.utils.StringUtils;
import com.google.gson.Gson;
import com.yalantis.ucrop.UCrop;

import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 杂志详情页面
 */

public class ZzActivity extends BaseActivity {
    private Pager pager;
    private PagerFactory pagerFactory;
    private Bitmap currentBitmap, mCurPageBitmap, mNextPageBitmap;
    private Canvas mCurPageCanvas, mNextPageCanvas;
    private static final String[] pages = {"one", "two", "three"};
    private int screenWidth;
    private int screenHeight;
    List<InfoBean.RETURNDATABean.InfoDetailBean.ImgContentBean> datas = new ArrayList<>();
    private LinearLayout imageLayout;
    private TextView tx_page_index;//页签
    private Boolean isDz;//点赞
    private Boolean isSc; //收藏

    private TextView plTxt;//评论
    private TextView scTxt;//收藏
    private TextView dzTxt;//点赞
    private ImageButton scIbtn;//收藏
    private ImageButton dzIbtn;//点赞

    private String DZ = "1";//点赞
    private String QXDZ = "2";//取消点赞
    private String SC = "3";//收藏
    private String QXSC = "4";//取消收藏
    private String ID;//文章id
    private String id;//操作id

    private UploadPhotoHelper mUploadPhotoHelper;

    private String shareUrl;
    private String sharetitle;
    private String shareContent;
    private String shareImage;

    private int count = 0;
    private int currentIndex = 0;
    private int lastIndex = 0;
    private Bitmap lastBitmap = null;

    private float startX;
    private float startY;

    private float currentX;
    private float currentY;
    private ValueAnimator apperaAnim;
    private ValueAnimator hiddenAnim;
    private boolean isHidding;

    private LinearLayout bottom;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ZzActivity.this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        ZzActivity.this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_zz);
        ID = getIntent().getStringExtra("id");
        mUploadPhotoHelper = new UploadPhotoHelper(this);
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
        bottom = (LinearLayout) findViewById(R.id.bottom_layout);
        initView();
        getInfo(ID);
        initLister();
        isHidding = false;
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
        bean.setType("IMG");
        requestData.setREQUEST_DATA(bean);
        ServerApi.request(false, requestData, InfoBean.class, OleConstants.sign)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        mDialog.showProgressDialog("加载中……", null);

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<InfoBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(InfoBean response) {
                        if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                            dismissProgressDialog();
                            datas.clear();
                            List<InfoBean.RETURNDATABean.InfoDetailBean.ImgContentBean> mList = response.getRETURN_DATA().getInfoDetail().getImgContent();
                            for (int i = 0; i < mList.size(); i++) {
                                InfoBean.RETURNDATABean.InfoDetailBean.ImgContentBean beans = mList.get(i);
                                if (beans.getType().equals("HTTP")) {
                                    beans.setUrl(mList.get(i).getUrl());
                                } else {
                                    beans.setBase64(mList.get(i).getBase64());
                                }
                                datas.add(beans);
                            }
                            isDz = response.getRETURN_DATA().getInfoDetail().isWhetherLike();
                            isSc = response.getRETURN_DATA().getInfoDetail().isWhetherFavorite();
                            plTxt.setText(response.getRETURN_DATA().getInfoDetail().getCommentCount() + "");
                            scTxt.setText(response.getRETURN_DATA().getInfoDetail().getFavoriteCount() + "");
                            dzTxt.setText(response.getRETURN_DATA().getInfoDetail().getLikeCount() + "");
                            sharetitle = response.getRETURN_DATA().getInfoDetail().getTitle();
                            shareContent = response.getRETURN_DATA().getInfoDetail().getContent();
                            shareImage = response.getRETURN_DATA().getInfoDetail().getCoverImg();
                            if (isDz) {
                                dzIbtn.setBackground(getResources().getDrawable(R.drawable.btn_dbldz_ydz));
                            }
                            if (isSc) {
                                scIbtn.setBackground(getResources().getDrawable(R.drawable.btn_dblsc_ysc));
                            }
                            pager.size(mList.size());
                            loadImage(mCurPageCanvas, 0);
                            count = datas.size();
                            if (currentIndex + 1 == count) {
                                tx_page_index.setText("最后一页");

                            } else {
                                tx_page_index.setText(currentIndex + 1 + "／" + count);

                            }
                            setAnimationView(bottom);
                            startHiddenAnimation();
                            findViewById(R.id.pl_image).setOnClickListener(new View.OnClickListener() {//评论
                                @Override
                                public void onClick(View view) {
                                    if (response.getRETURN_DATA().getInfoDetail().getId() != null && !response.getRETURN_DATA().getInfoDetail().getId().equals("")) {
                                        if (ToolUtils.isLoginStatus(mContext)) {
                                            Intent intent = new Intent();
                                            intent.setClass(ZzActivity.this, SpecialCommentActivity.class);
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


    private void initView() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
        pager = new Pager(this, screenWidth, screenHeight);
        imageLayout = (LinearLayout) findViewById(R.id.imageLayout);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        imageLayout.addView(pager, p);
        tx_page_index = (TextView) findViewById(R.id.tx_page_index);
        mCurPageBitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_4444);
        mNextPageBitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_4444);
        mCurPageCanvas = new Canvas(mCurPageBitmap);
        mNextPageCanvas = new Canvas(mNextPageBitmap);
        pagerFactory = new PagerFactory(getApplicationContext());
        pager.setBitmaps(mCurPageBitmap, mCurPageBitmap);
        pager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent e) {
                boolean ret = false;
                if (v == pager) {
                    if (e.getAction() == MotionEvent.ACTION_DOWN) {
                        startX = e.getX();
                        startY = e.getY();
                        if (pager.isInCenter(e.getX())) {
                            return true;
                        }
                        pager.calcCornerXY(e.getX(), e.getY());
                        lastBitmap = currentBitmap;
                        lastIndex = currentIndex;
                        if (pagerFactory != null) {
                            pagerFactory.onDraw(mCurPageCanvas, currentBitmap);
                        }
                        if (pager.DragToRight()) {    // 向右滑动，显示前一页
                            if (currentIndex == 0)
                                return false;
                            pager.abortAnimation();
                            currentIndex--;
                            loadImage(mNextPageCanvas, currentIndex);
                        } else {        // 向左滑动，显示后一页、
                            if (currentIndex + 1 == count)
                                return false;
                            pager.abortAnimation();
                            currentIndex++;
                            loadImage(mNextPageCanvas, currentIndex);
                        }
                    } else if (e.getAction() == MotionEvent.ACTION_MOVE) {
                        currentY = e.getY();
                        currentX = e.getX();
                        return true;
                    } else if (e.getAction() == MotionEvent.ACTION_UP) {
                        currentY = e.getY();
                        currentX = e.getX();
                        if (pager.isInCenter(startX)) {
                            if (!isHidding) {
                                startHiddenAnimation();
                            } else {
                                startApperaAnimation();
                            }
                            return true;
                            //                            if (Math.abs(currentY - startY) >0.1) {
                            //                                if (currentY - startY > 0) {//向下隐藏功能栏
                            //                                    if (!isHidding) {
                            //                                        startHiddenAnimation();
                            //                                    }
                            //                                } else {////展示功能栏
                            //                                    if (isHidding) {
                            //                                        startApperaAnimation();
                            //                                    }
                            //                                }
                            //                                return true;
                            //                            }
                        }
                        if (!pager.canDragOver()) {
                            currentIndex = lastIndex;
                            currentBitmap = lastBitmap;
                        }
                        startHiddenAnimation();
                        if (currentIndex + 1 == count) {
                            tx_page_index.setText("最后一页");

                        } else {
                            tx_page_index.setText(currentIndex + 1 + "／" + count);

                        }
                        //                        tx_page_index.setText((currentIndex + 1 == count ? count : currentIndex + 1) + "／" + count);
                    }

                    ret = pager.doTouchEvent(e);
                    return ret;
                }
                return false;
            }
        });
    }

    private void loadImage(final Canvas canvas, int index) {
        InfoBean.RETURNDATABean.InfoDetailBean.ImgContentBean beans = datas.get(index);
        if (!StringUtils.isNullOrEmpty(beans.getUrl())) {
            loadBackground(beans.getUrl(), canvas);

        }
    }


    public void loadBackground(String url, Canvas canvas) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setCrop(true)// 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                //                .setLoadingDrawableId(R.mipmap.ic_launcher)//加载中默认显示图片
                .setFailureDrawableId(R.mipmap.ic_launcher)//加载失败后默认显示图片
                //设置使用缓存
                .setUseMemCache(true)
                .build();
        x.image().loadDrawable(url, imageOptions, new Callback.CommonCallback<Drawable>() {
            @Override
            public void onSuccess(Drawable drawable) {
                BitmapDrawable bd = (BitmapDrawable) drawable;
                Bitmap bitmap = bd.getBitmap();
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                Matrix matrix = new Matrix();
                matrix.postScale(((float) screenWidth) / width, ((float) screenHeight) / height);
                currentBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
                pagerFactory.onDraw(canvas, currentBitmap);
                pager.setBitmaps(mCurPageBitmap, mNextPageBitmap);
                pager.postInvalidate();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

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
                                if (id == DZ) {
                                    isDz = true;
                                    dzIbtn.setBackground(getResources().getDrawable(R.drawable.btn_dbldz_ydz));
                                }
                                if (id == QXDZ) {
                                    isDz = false;
                                    dzIbtn.setBackground(getResources().getDrawable(R.drawable.td_dz_selector));
                                }
                                if (id == QXSC) {
                                    isSc = false;
                                    scIbtn.setBackground(getResources().getDrawable(R.drawable.td_sc_selector));

                                }
                                Log.e("操作：" + new Gson().toJson(response));
                                if (response.getRETURN_DATA() != null) {
                                    scTxt.setText(response.getRETURN_DATA().getCollections() + "");

                                }
                                if (response.getRETURN_DATA() != null) {
                                    dzTxt.setText(response.getRETURN_DATA().getLikes() + "");

                                }
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
                        CollectionUtils.getInstance().showCollectionDialog(ZzActivity.this,
                                CollectionUtils.CollectionTypeEnum.INFORMATION_COLLECTION, ID, "杂志");
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
                {
                    sharetitle = "测试标题";
                    shareImage = "https://avatars0.githubusercontent.com/u/11846662?v=4&u=c628703c746122f2c2fea0701e7bd2ed4fe71149&s=400";
                    shareContent = "测试内容";
                    shareUrl = "http://www.baidu.com";

                    UmengUtils.shareUrl(ZzActivity.this, sharetitle, shareContent, shareUrl, shareImage, R.drawable.logo_ole, new UmengUtils.ShareCallBack() {
                        @Override
                        public void onStart(UmengUtils.ShareType type) {

                        }

                        @Override
                        public void onResult(UmengUtils.ShareType type) {
                            ToastUtil.showToast("分享成功");

                        }

                        @Override
                        public void onError(UmengUtils.ShareType type, Throwable throwable) {
                            ToastUtil.showToast("分享失败");

                        }

                        @Override
                        public void onCancel(UmengUtils.ShareType type) {
                            ToastUtil.showToast("分享取消");
                        }
                    });
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // 如果是直接从相册获取
            case UploadUtils.CHOOSE_PICTURE:
                if (resultCode == RESULT_OK && data != null) {
                    switch (ToolUtils.getCropMaxType(BaseApplication.getDeviceWidth())) {
                        case 1:
                            mUploadPhotoHelper.startPhotoZoom(data.getData(), 218, 286, 218, 286);
                            break;
                        case 2:
                            mUploadPhotoHelper.startPhotoZoom(data.getData(), 327, 429, 327, 429);
                            break;
                        case 3:
                            mUploadPhotoHelper.startPhotoZoom(data.getData(), 436, 572, 436, 572);
                            break;
                    }
                }
                break;
            // 如果是调用相机拍照时
            case CapturePhotoHelper.CAPTURE_PHOTO_REQUEST_CODE:
                File photoFile = mUploadPhotoHelper.getCapturePhotoHelper().getPhoto();
                if (photoFile.exists()) {
                    switch (ToolUtils.getCropMaxType(BaseApplication.getDeviceWidth())) {
                        case 1:
                            mUploadPhotoHelper.startPhotoZoom(Uri.fromFile(photoFile), 218, 286, 218, 286);
                            break;
                        case 2:
                            mUploadPhotoHelper.startPhotoZoom(Uri.fromFile(photoFile), 327, 429, 327, 429);
                            break;
                        case 3:
                            mUploadPhotoHelper.startPhotoZoom(Uri.fromFile(photoFile), 436, 572, 436, 572);
                            break;
                    }
                    //                    mUploadPhotoHelper.startPhotoZoom(Uri.fromFile(photoFile), 218 / 286, 1, 218, 286);
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

    public void setAnimationView(final View animationView) {
        /**
         * 创建动画
         */
        hiddenAnim = ValueAnimator.ofFloat(0, animationView.getHeight());
        hiddenAnim.setDuration(500);
        hiddenAnim.setTarget(animationView);
        hiddenAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animationView.setTranslationY((Float) animation.getAnimatedValue());
                tx_page_index.setTranslationY((Float) animation.getAnimatedValue());
            }
        });
        hiddenAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isHidding = true;
            }

            @Override
            public void onAnimationStart(Animator animation) {
            }
        });
        apperaAnim = ValueAnimator.ofFloat(animationView.getHeight(), 0);
        apperaAnim.setDuration(500);
        apperaAnim.setTarget(animationView);
        apperaAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animationView.setTranslationY((Float) animation.getAnimatedValue());
                tx_page_index.setTranslationY((Float) animation.getAnimatedValue());
            }
        });

        apperaAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isHidding = false;
            }

            @Override
            public void onAnimationStart(Animator animation) {
            }
        });

    }

    private void startHiddenAnimation() {
        if (!hiddenAnim.isRunning() && !isHidding) {
            hiddenAnim.start();
        }
    }

    private void startApperaAnimation() {
        if (!apperaAnim.isRunning()) {
            apperaAnim.start();
        }
    }


    @Override
    protected void onResume() {
        ;
        super.onResume();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCurPageBitmap.recycle();
        mNextPageBitmap.recycle();
    }

}
