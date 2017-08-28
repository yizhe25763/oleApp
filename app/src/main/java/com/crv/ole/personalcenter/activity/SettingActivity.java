package com.crv.ole.personalcenter.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.crv.ole.BaseApplication;
import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.utils.DataCleanManager;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.crv.ole.view.CustomDiaglog;
import com.vondear.rxtools.view.dialog.RxDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 个人中心 - 设置页面
 */

public class SettingActivity extends BaseActivity {
    private RxDialog dialog;
    @BindView(R.id.set_clear_num)
    TextView set_clear_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initTitleViews();
        initBackButton();
        setBarTitle(R.string.set_title);
        showCacheSize();
    }

    @OnClick({R.id.set_pwd, R.id.set_third, R.id.set_clear_layout, R.id.set_about, R.id.exit_btn})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.set_pwd:
                startActivityWithAnim(PwdSettingActivity.class);
                break;
            case R.id.set_third:
                startActivityWithAnim(ThirdBindActivity.class);
                break;
            case R.id.set_clear_layout:    //清除缓存
                View clearView = LayoutInflater.from(mContext).inflate(R.layout.setting_clear_layout, null);
                clearView.findViewById(R.id.clear_confirm).setOnClickListener(this);
                clearView.findViewById(R.id.clear_cancel).setOnClickListener(this);
                dialog = new RxDialog(mContext, 0, Gravity.BOTTOM);
                dialog.setFullScreenWidth();
                dialog.setContentView(clearView);
                dialog.getWindow().setWindowAnimations(R.style.dialogBottomStyle);
                dialog.show();
                break;
            case R.id.set_about:
                startActivityWithAnim(AboutActivity.class);
                break;
            case R.id.clear_confirm:
                dialog.dismiss();
                DataCleanManager.cleanAppData(mContext);
                set_clear_num.setText("0M");
                ToastUtil.showToast("缓存清除成功！");
                break;
            case R.id.clear_cancel:
                dialog.dismiss();
                break;
            case R.id.exit_btn:
                exitOpertion();
                break;
        }
    }

    /**
     * 退出登录
     */
    private void exitOpertion() {
        showAlertDialog("您确定要退出吗?", "退出", "取消", new CustomDiaglog.OnConfimClickListerner() {
            @Override
            public void onCanleClick() {

            }
            @Override
            public void OnConfimClick() {
                mPreferencesHelper.remove(OleConstants.KEY.LOGIN_STATUS);
                mPreferencesHelper.remove(OleConstants.KEY.USER_ID);
                mPreferencesHelper.remove(OleConstants.KEY.PWD_KEY);
                mPreferencesHelper.remove(OleConstants.KEY.ACCOUNT_KEY);
                mPreferencesHelper.remove(OleConstants.KEY.REQUES_TOKEN);
                BaseApplication.getInstance().setRequestCookieParams(null);
                BaseApplication.getInstance().setUserCenter(null);
                BaseApplication.getInstance().setRequestCookieParams(null);
                finish();
            }
        });
    }

    /**
     * 设置页面获取应用缓存大小并显示
     */
    private void showCacheSize() {
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {
                set_clear_num.setText(s);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        Observable.just(getAppCacheSize(mContext))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }


    /**
     * 获取应用缓存大小
     * 内置cache目录:/data/data/包名/cache
     * 内置file目录:/data/data/包名/files
     * 外置的cache目录:/mnt/sdcard/Android/data/包名/cache
     * 外置file目录:/mnt/sdcard/Android/data/包名/files
     * 其他目录暂时不清除
     * 获取常用目录一览
     * <p>
     * Environment.getDataDirectory().getAbsolutePath():/data
     * <p>
     * Environment.getExternalStorageDirectory:/mnt/sdcard
     * <p>
     * context.getExternalFilesDir:/mnt/sdcard/Android/data/com.yeamo4/files
     * <p>
     * context.getFiledir:/data/data/com.yeamo4/files
     * <p>
     * context.getCachedir:/data/data/com.yeamo4/cache
     * <p>
     * Environment.getExternalStorageDirectory:/mnt/sdcard
     * <p>
     * context.getExternalFilesDir:/mnt/sdcard/Android/data/com.yeamo4/files
     * <p>
     * context.getFiledir:/data/data/com.yeamo4/files
     * <p>
     * context.getCachedir:/data/data/com.yeamo4/cache
     * <p>
     * context.getExternalCacheDir():/mnt/sdcard/Android/data/com.yeamo4/cache
     */
    private String getAppCacheSize(Context context) {
        long cacheDirSize = 0, filesDirsize = 0, extrenalCacheDirSize = 0, extrenalFilesDirsize = 0;
        try {
            cacheDirSize = DataCleanManager.getFolderSize(context.getCacheDir());
            filesDirsize = DataCleanManager.getFolderSize(context.getFilesDir());
            extrenalCacheDirSize = DataCleanManager.getFolderSize(context.getExternalCacheDir());
            extrenalFilesDirsize = DataCleanManager.getFolderSize(context.getExternalFilesDir(null));
        } catch (Exception e) {
            e.printStackTrace();
        }
        long totalSize = cacheDirSize + filesDirsize + extrenalCacheDirSize + extrenalFilesDirsize;
        return DataCleanManager.getFormatSize(totalSize);
    }

}
