package com.crv.ole.information.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.BaseWebView;
import com.crv.ole.base.JSHook;
import com.crv.ole.base.JsCallbackLister;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.home.model.FetchBean;
import com.crv.ole.home.model.ToogleLoading;
import com.crv.ole.information.model.ArticleBean;
import com.crv.ole.information.model.ArticleImageBean;
import com.crv.ole.information.model.LikeBean;
import com.crv.ole.information.model.SpecialDerailResult;
import com.crv.ole.login.activity.LoginActivity;
import com.crv.ole.personalcenter.tools.CollectionUtils;
import com.crv.ole.shopping.activity.LookPicActivity;
import com.crv.ole.shopping.model.PhotoInfo;
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

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SpecialDeatail1Activity2 extends BaseActivity {


    @BindView(R.id.webview)
    BaseWebView webView;

    @BindView(R.id.bottom_layout)
    LinearLayout bottom;

    @BindView(R.id.pl_txt)
    TextView plTxt;

    @BindView(R.id.sc_txt)
    TextView scTxt;

    @BindView(R.id.dz_txt)
    TextView dzTxt;

    @BindView(R.id.sc_image)
    ImageButton scIbtn;

    @BindView(R.id.dz_image)
    ImageButton dzIbtn;

    JSHook jsHook = null;

    private UploadPhotoHelper mUploadPhotoHelper;
    String url = "";
    private String shareUrl = "http://www.baidu.com";
    private String sharetitle = "太好了";
    private String shareContent = "是真的吗";
    private String shareImage = "http://imgsrc.baidu.com/imgad/pic/item/267f9e2f07082838b5168c32b299a9014c08f1f9.jpg";

    private Boolean isDz = false;//点赞
    private Boolean isSc = false; //收藏
    private String DZ = "1";//点赞
    private String QXDZ = "2";//取消点赞
    private String SC = "3";//收藏
    private String QXSC = "4";//取消收藏
    private String articleId = "";

    private  ArrayList<PhotoInfo> imgsList = new ArrayList<PhotoInfo>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_deatail12);
        ButterKnife.bind(this);
        initLister();
        initData();
    }

    private void initData() {
        mUploadPhotoHelper = new UploadPhotoHelper(this);
        articleId = getIntent().getStringExtra("id");
       // bottom.setVisibility(View.GONE);
        url = OleConstants.BASE_HOST + "/img/oleH5/dist/index.html#/ThematicDetail?id=" + articleId;

        jsHook = new JSHook(new JsCallbackLister() {
            @Override
            public void goTo(String json) {
                Log.i("GoTo另外一篇文章:" + json);
                ArticleBean articleBean = new Gson().fromJson(json, ArticleBean.class);
                articleId = articleBean.getParams().get("id");
                getInfo(articleId, true);
               //webView.loadUrl(url);
            }

            @Override
            public void showPictureCollection(String json) {
                imgsList.clear();
                ArticleImageBean articleImageBean = new Gson().fromJson(json,ArticleImageBean.class);
                for (String url:articleImageBean.getImgs()){
                    PhotoInfo info = new PhotoInfo();
                    info.setSourcePath(url);
                    imgsList.add(info);
                }
                Log.i("图片集是:" + json);
                Intent intent = new Intent(mContext, LookPicActivity.class);
                intent.putExtra("EXTRA_IMAGE_LIST", imgsList);
                intent.putExtra("EXTRA_CURRENT_IMG_POSITION", articleImageBean.getIndex());
                mContext.startActivity(intent);
            }

            @Override
            public void fetch(String json) {
                FetchBean fetchBean = new Gson().fromJson(json, FetchBean.class);
                Log.i("fetch结果是:" + fetchBean.toString());
                String callbackStr = fetchBean.getCallback();
                if ("1".equals(fetchBean.getRequireLogin()) && !ToolUtils.isLoginStatus(SpecialDeatail1Activity2.this)) {
                    Intent intent = new Intent(SpecialDeatail1Activity2.this, LoginActivity.class);
                    String callbackFuntion = "javascript:H5.callback.login('loginsuccess')";
                    intent.putExtra("jscallback", callbackFuntion);
                    startActivityForResult(intent, OleConstants.noticeJsLogInSuccess);
                    return;
                }
                Log.i("apiId:" + fetchBean.getApiId());

                ServiceManger.getInstance().fetchData(fetchBean.getApiId(), fetchBean.getParams(), new BaseRequestCallback<String>() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("成功:" + response);
                        if (callbackStr != null) {
                            String callbackFuntion = "";
                            if (fetchBean.getCallbackData() != null) {
                                callbackFuntion = "javascript:H5." + callbackStr + "(" + response + "," + fetchBean.getCallbackData() + ")";
                            } else {
                                callbackFuntion = "javascript:H5." + callbackStr + "(" + response + ")";
                            }
                            Log.i("callback是:" + callbackFuntion);
                            String finalCallbackFuntion = callbackFuntion;
                            webView.post(new Runnable() {
                                @Override
                                public void run() {
                                    webView.loadUrl(finalCallbackFuntion);
                                }
                            });

                            if (OleConstants.CART_ADD.equals(fetchBean.getApiId())) {
                                EventBus.getDefault().post(OleConstants.REFRESH_GWC_COUNT);
                            }
                        }

                    }

                    @Override
                    public void onFailed(String msg) {
                        ToastUtil.showToast(msg);
                    }
                });

            }

            @Override
            public void showToast(String json) {
                ToogleLoading toogleLoading = new Gson().fromJson(json, ToogleLoading.class);
                if (toogleLoading.getContent() != null) {
                    ToastUtil.showToast(toogleLoading.getContent());
                }
            }

            @Override
            public void toogleLoading(String json) {
                ToogleLoading toogleLoading = new Gson().fromJson(json, ToogleLoading.class);
                Log.i("toogleLoading:" + toogleLoading.toString());
                if (toogleLoading.getVisible().equals("1")) {
                    showProgressDialog("加载中...", null);
                } else {
                    dismissProgressDialog();
                }
            }
        });

        webView.addJavascriptInterface(jsHook, "NATIVE");
        webView.loadUrl(url);
        webView.setOnScrollChangeListener(new BaseWebView.OnScrollChangeListener() {
            @Override
            public void onPageEnd(int l, int t, int oldl, int oldt) {
                bottom.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageTop(int l, int t, int oldl, int oldt) {
                bottom.setVisibility(View.VISIBLE);
            }

            @Override
            public void onScrollChanged(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if ((scrollY - oldScrollY) > 0) {//向下滑动
                    bottom.setVisibility(View.GONE);
                } else if ((oldScrollY - scrollY) > 0) {//向上滑动
                    bottom.setVisibility(View.VISIBLE);
                }
                webView.setAnimationView(bottom);
            }
        });
        getInfo(articleId, false);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // 如果是直接从相册获取
            case UploadUtils.CHOOSE_PICTURE:
                if (resultCode == RESULT_OK && data != null) {
                    mUploadPhotoHelper.startPhotoZoom(data.getData(), 690, 900, 690, 900);
                }
                break;
            // 如果是调用相机拍照时
            case CapturePhotoHelper.CAPTURE_PHOTO_REQUEST_CODE:
                File photoFile = mUploadPhotoHelper.getCapturePhotoHelper().getPhoto();
                if (photoFile.exists()) {
                    mUploadPhotoHelper.startPhotoZoom(Uri.fromFile(photoFile), 690, 900, 690, 900);
                }
                break;
            // 取得裁剪后的图片
            case UCrop.REQUEST_CROP:
                if (mUploadPhotoHelper.getPhoto().exists()) {
                    if (FileUtils.fileSize(mUploadPhotoHelper.getPhoto().getPath()) > 0) {
                        String selectImgPath = mUploadPhotoHelper.getPhoto().getPath();
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
                    } else { //收藏
                        CollectionUtils.getInstance().showCollectionDialog(SpecialDeatail1Activity2.this,
                                CollectionUtils.CollectionTypeEnum.INFORMATION_COLLECTION, articleId, "文章");
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

                UmengUtils.shareUrl(SpecialDeatail1Activity2.this, sharetitle, shareContent, shareUrl, shareImage, R.drawable.logo_ole, new UmengUtils.ShareCallBack() {
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
    }

    /**
     * 获取文章详情
     *
     * @param id 文章id
     */
    private void getInfo(String id, boolean reload) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);
        ServiceManger.getInstance().articleDetail(map, new BaseRequestCallback<SpecialDerailResult>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(SpecialDerailResult response) {
                if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                    dismissProgressDialog();
                    isDz = response.getRETURN_DATA().getInfoDetail().isWhetherLike();
                    isSc = response.getRETURN_DATA().getInfoDetail().isWhetherFavorite();
                    plTxt.setText(response.getRETURN_DATA().getInfoDetail().getCommentCount() + "");
                    scTxt.setText(response.getRETURN_DATA().getInfoDetail().getFavoriteCount() + "");
                    dzTxt.setText(response.getRETURN_DATA().getInfoDetail().getLikeCount() + "");
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
                                    intent.setClass(SpecialDeatail1Activity2.this, SpecialCommentActivity.class);
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
            public void onEnd() {
                dismissProgressDialog();
            }

            @Override
            public void onFailed(String msg) {
                ToastUtil.showToast("失败:" + msg);
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
        HashMap<String, String> map = new HashMap<>();
        map.put("isComment", id);
        map.put("articleId", articleId);
        if (!contextSS.equals("")) {
            map.put("articleFileName", contextSS);
        }
        ServiceManger.getInstance().zanComment(map, new BaseRequestCallback<LikeBean>() {
            @Override
            public void onStart() {
                showProgressDialog("加载中...", null);
            }

            @Override
            public void onSuccess(LikeBean response) {
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
                } else {
                    ToastUtil.showToast("失败:" + response.getRETURN_DESC());
                }
            }

            @Override
            public void onEnd() {
                dismissProgressDialog();
            }

            @Override
            public void onFailed(String msg) {
                dismissProgressDialog();
                ToastUtil.showToast("点赞失败:" + msg);
            }
        });
    }

}
