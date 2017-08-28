package com.crv.ole.shopping.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.crv.ole.BaseApplication;
import com.crv.ole.R;
import com.crv.ole.base.BaseAppCompatActivity;
import com.crv.ole.base.BaseRequestCallback;
import com.crv.ole.base.ServiceManger;
import com.crv.ole.personalcenter.tools.CollectionUtils;
import com.crv.ole.shopping.fragment.HwDetailFragment;
import com.crv.ole.shopping.fragment.HwListFragment;
import com.crv.ole.shopping.model.CartCountResponseData;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.crv.ole.utils.ToolUtils;
import com.crv.ole.utils.UmengUtils;
import com.crv.ole.utils.fileupload.CapturePhotoHelper;
import com.crv.ole.utils.fileupload.UploadPhotoHelper;
import com.crv.ole.utils.fileupload.UploadUtils;
import com.crv.sdk.utils.FileUtils;
import com.umeng.socialize.UMShareAPI;
import com.yalantis.ucrop.UCrop;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

/**
 * create by yanghongjun 2017/08/07
 * 好物详情和好物列表
 */

public class HwDetailActivity extends BaseAppCompatActivity implements View.OnClickListener {

    ViewPager viewPager = null;
    HwDetailFragment detailFragment = null;
    HwListFragment listFragment = null;

    private UploadPhotoHelper mUploadPhotoHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment();
        initTab();
        initClick();
        mUploadPhotoHelper = new UploadPhotoHelper(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getCartCount();
    }

    private void initFragment() {
        Intent intent = getIntent();
        String activeId = intent.getStringExtra("imgLinkTo");
        String hwDetailUrl = OleConstants.BASE_HOST + "/img/oleH5/dist/index.html#/recommendedProducts?activeId=" + activeId + "&pageType=detail";
        String hwListUrl = OleConstants.BASE_HOST + "/img/oleH5/dist/index.html#/recommendedProducts?activeId=" + activeId + "&pageType=list";
        detailFragment = HwDetailFragment.newInstance(hwDetailUrl);
        listFragment = HwListFragment.newInstance(hwListUrl);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("请求码是:" + requestCode + "结果码是" + resultCode);
        if (requestCode == UploadUtils.CHOOSE_PICTURE) {
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
        } else if (requestCode == CapturePhotoHelper.CAPTURE_PHOTO_REQUEST_CODE) {
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
            }
        } else if (requestCode == UCrop.REQUEST_CROP) {
            if (mUploadPhotoHelper.getPhoto().exists()) {
                if (FileUtils.fileSize(mUploadPhotoHelper.getPhoto().getPath()) > 0) {
                    String selectImgPath = mUploadPhotoHelper.getPhoto().getPath();
                    CollectionUtils.getInstance().setSelectImgPath(selectImgPath);
                } else {
                    mUploadPhotoHelper.getPhoto().delete();
                }
            }

        } else if (resultCode == OleConstants.noticeJsLogInSuccess) {
            Log.i("登录成功后js回调来了");
            if (viewPager.getCurrentItem() == 0) {
                detailFragment.webView.post(new Runnable() {
                    @Override
                    public void run() {
                        detailFragment.webView.loadUrl(data.getStringExtra("jscallback"));
                    }
                });
            } else {
                listFragment.webView.post(new Runnable() {
                    @Override
                    public void run() {
                        listFragment.webView.loadUrl(data.getStringExtra("jscallback"));
                    }
                });
            }
        }

        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void swithWebView(String status) {
        if (status.equals(OleConstants.WEB_SCROLL_TOP)) {
            Log.i("接收到顶的通知");
            if (viewPager != null) {
                viewPager.setCurrentItem(0, true);
            }

        } else if (status.equals(OleConstants.WEB_SCROLL_BOTTOM)) {
            Log.i("接收到到底通知");
            if (viewPager != null) {
                viewPager.setCurrentItem(1, true);

            }
        } else if (status.equals(OleConstants.REFRESH_GWC_COUNT)) {
            getCartCount();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hw_detail;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back_btn: {
                finish();
                break;
            }
            case R.id.gwc_iv: { //购物车跳转
                Intent intent = new Intent(this, ShoppingCartListActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.ivShare: { //分享
                UmengUtils.shareUrl(this, "", "真的太好了", "http://www.baidu.com", null, R.drawable.seekbar_thumb, new UmengUtils.ShareCallBack() {
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
                break;
            }
        }
    }

    private void initTab() {
        HwDetailPagerAdapter pagerAdapter = new HwDetailPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.market_tab_title_layout);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);
    }

    private ImageView gwc_count_iv;

    private void initClick() {
        this.findViewById(R.id.title_back_btn).setOnClickListener(this);
        this.findViewById(R.id.ivShare).setOnClickListener(this);
        this.findViewById(R.id.gwc_iv).setOnClickListener(this);
        gwc_count_iv = (ImageView) this.findViewById(R.id.gwc_count_iv);
    }

    /**
     * 获取购物车数量
     */
    private void getCartCount() {
        ServiceManger.getInstance().getCartCount("", "", new BaseRequestCallback<CartCountResponseData>() {
            @Override
            public void onSuccess(CartCountResponseData data) {
                if (OleConstants.REQUES_SUCCESS.equals(data.getRETURN_CODE())) {
                    int count = com.crv.sdk.utils.StringUtils.stringToInt(data.getRETURN_DATA().getCount());
                    gwc_count_iv.setVisibility(count > 0 ? View.VISIBLE : View.INVISIBLE);
                }
            }
        });
    }


    private class HwDetailPagerAdapter extends FragmentPagerAdapter {
        private String tabTitles[] = new String[]{getString(R.string.market_hw_tab_1), getString(R.string.market_hw_tab_2)};

        public HwDetailPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return detailFragment;
            }
            return listFragment;

        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }
}
