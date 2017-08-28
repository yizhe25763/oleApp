package com.crv.ole.home.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.home.fragment.FindFragment;
import com.crv.ole.home.fragment.HomeFragment;
import com.crv.ole.home.fragment.MarketFragment;
import com.crv.ole.home.fragment.UserFragment;
import com.crv.ole.home.model.TabEntity;
import com.crv.ole.login.activity.LoginActivity;
import com.crv.ole.personalcenter.tools.CollectionUtils;
import com.crv.ole.push.GeTuiIntentService;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.ToastUtil;
import com.crv.ole.utils.ToolUtils;
import com.crv.ole.utils.fileupload.CapturePhotoHelper;
import com.crv.ole.utils.fileupload.UploadPhotoHelper;
import com.crv.ole.utils.fileupload.UploadUtils;
import com.crv.ole.view.CustomDiaglog;
import com.crv.sdk.utils.FileUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.igexin.sdk.PushManager;
import com.umeng.socialize.UMShareAPI;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wj_wsf on 2017/6/23 12:39.
 */

public class MainActivity extends BaseActivity {

    @BindView(R.id.layoutContainer)
    FrameLayout mLayoutContainer;
    @BindView(R.id.commonTabLayout)
    CommonTabLayout mCommonTabLayout;

    private HomeFragment homeFragment;
    private FindFragment findFragment;

    private static final int REQUEST_PERMISSION = 0;

    private int index = -1;

    private int[] mTitles = {R.string.tab_home_page, R.string.tab_find_page,
            R.string.tab_market_page, R.string.tab_myewj_sth};
    private int[] mIconUnselectIds = {R.drawable.btn_sysytu_normal,
            R.drawable.btn_sytdtb_normal, R.drawable.btn_sycstb_normal, R.drawable.btn_sygrtb_normal};
    private int[] mIconSelectIds = {R.drawable.btn_sysytu_pressed,
            R.drawable.btn_sytdtb_pressed, R.drawable.btn_sycstb_pressed,
            R.drawable.btn_sygrtb_pressed};

    private UploadPhotoHelper mUploadPhotoHelper;


    //生命周期方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_main);
        ButterKnife.bind(this);
        mUploadPhotoHelper = new UploadPhotoHelper(this);
        initView();
        initGeTuiPush();
    }

    private void initGeTuiPush() {

        PackageManager pkgManager = getPackageManager();
        boolean sdCardWritePermission =
                pkgManager.checkPermission("android.permission.WRITE_EXTERNAL_STORAGE", getPackageName()) == PackageManager.PERMISSION_GRANTED;

        // read phone state用于获取 imei 设备信息
        boolean phoneSatePermission =
                pkgManager.checkPermission("android.permission.READ_PHONE_STATE", getPackageName()) == PackageManager.PERMISSION_GRANTED;

        if (Build.VERSION.SDK_INT >= 23 && !sdCardWritePermission || !phoneSatePermission) {
            requestPermission();
        } else {
            PushManager.getInstance().initialize(this.getApplicationContext(), null);
        }
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), GeTuiIntentService.class);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            if ((grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                PushManager.getInstance().initialize(this.getApplicationContext(), null);
            } else {
                Log.e("注册推送失败");
                PushManager.getInstance().initialize(this.getApplicationContext(), null);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE},
                REQUEST_PERMISSION);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        index = intent.getIntExtra("index", -1);
        if (index != -1) {
            mCommonTabLayout.setCurrentTab(index);
        }
    }

    private long exitTime = 0;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            ToastUtil.showToast("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
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
                    mUploadPhotoHelper.startPhotoZoom(data.getData(), 690, 900, 690, 900);
//                            break;
//                    }
                }
                break;
            // 如果是调用相机拍照时
            case CapturePhotoHelper.CAPTURE_PHOTO_REQUEST_CODE:
                File photoFile = mUploadPhotoHelper.getCapturePhotoHelper().getPhoto();
                if (photoFile.exists()) {
//                    switch (ToolUtils.getCropMaxType(BaseApplication.getDeviceWidth())) {
//                        case 1:
                    mUploadPhotoHelper.startPhotoZoom(Uri.fromFile(photoFile), 690, 900, 690, 900);
//                            break;
//                        case 2:
//                            mUploadPhotoHelper.startPhotoZoom(Uri.fromFile(photoFile), 327, 429, 327, 429);
//                            break;
//                        case 3:
//                            mUploadPhotoHelper.startPhotoZoom(Uri.fromFile(photoFile), 436, 572, 436, 572);
//                            break;
//                    }
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
                UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    /**
     * 初始化view
     */
    private void initView() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        homeFragment = HomeFragment.getInstance();
        findFragment = FindFragment.getInstance();
        fragments.add(homeFragment);
        fragments.add(findFragment);
        fragments.add(MarketFragment.getInstance());
        fragments.add(UserFragment.getInstance());

        ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            tabEntities.add(new TabEntity(getString(mTitles[i]), mIconSelectIds[i], mIconUnselectIds[i]));
        }

        mCommonTabLayout.setTabData(tabEntities, this, R.id.layoutContainer, fragments);
        mCommonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                Log.i("onTabSelect" + position);
                if (position == 0) {
                    homeFragment.showThisPage();
                } else if (position == 1) {
                    findFragment.showThisPage();
                }
            }

            @Override
            public void onTabReselect(int position) {
                Log.i("onTabReselect" + position);
                if (position == 0) {
                    homeFragment.showThisPage();
                }
            }
        });
    }

    @Override
    public void onEvent(String event) {
        if (event.startsWith("olepush:")) {
            String pushContent = event.substring(8, event.length());
            showAlertDialog("推送消息?", "确定", "取消", new CustomDiaglog.OnConfimClickListerner() {
                @Override
                public void onCanleClick() {
                    Log.i("点击了取消");
                }

                @Override
                public void OnConfimClick() {
                    if (!ToolUtils.isLoginStatus(MainActivity.this)) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
            });

        }
    }
}
