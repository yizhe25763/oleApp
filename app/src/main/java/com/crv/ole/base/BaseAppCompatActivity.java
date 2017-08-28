package com.crv.ole.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.utils.Log;
import com.crv.sdk.dialog.DialogFrag;
import com.crv.sdk.dialog.FragmentDialog;
import com.crv.sdk.dialog.IDialog;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.ButterKnife;

/**
 * Created by lihongshi on 2017/7/28.
 * note:toobar里的ID不要使用databingd，使用get...
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity {
    public final String TAG = BaseAppCompatActivity.class.getSimpleName();
    protected Context mContext;
    private TextView mToolbarTitle;
    private TextView mToolbarSubTitle;
    private Toolbar mToolbar;
    private ViewDataBinding mViewDataBinding;
    protected IDialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("激活页面:" + getClass().getSimpleName());
        mContext = this;
        mDialog = new FragmentDialog(this);
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        //ButterKnife.bind(this);
        initToolBar();
    }

    /**
     * this activity layout res
     * 设置layout布局,在子类重写该方法.
     *
     * @return res layout xml id
     */
    protected abstract int getLayoutId();

    public Object getViewDataBinding() {
        return mViewDataBinding;
    }

    /**
     * 设置状态栏颜色
     */
    private void initSystemBarTint() {
        Window window = getWindow();
        if (translucentStatusBar()) {
            // 设置状态栏全透明
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            return;
        }
        // 沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0以上使用原生方法
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(setStatusBarColor());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4-5.0使用三方工具类，有些4.4的手机有问题，这里为演示方便，不使用沉浸式
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(setStatusBarColor());
        }
    }

    /**
     * 子类可以重写决定是否使用透明状态栏
     */
    private boolean translucentStatusBar() {
        return false;
    }

    /**
     * 子类可以重写改变状态栏颜色
     */
    private int setStatusBarColor() {
        return getColorPrimary();
    }

    /**
     * 获取主题色
     */
    private int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }


    public void initCustomToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
    }


    /**
     * 初始化 Toolbar
     */
    private void initToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolBar);
        mToolbarTitle = (TextView) findViewById(R.id.toolbarTitle);
        mToolbarSubTitle = (TextView) findViewById(R.id.toolbarSubtitle);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);//将Toolbar显示到界面
        }

        if (getToolbarTitle() != null) {
            setToolBarTitle(getTitle()); //getTitle()的值是activity的android:lable属性值
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);  //设置默认的标题不显示
        }

        if (null != getToolbar() && isShowBacking()) {
            showBack();
        }
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    /**
     * 获取头部标题的TextView
     *
     * @return
     */
    public TextView getToolbarTitle() {
        return mToolbarTitle;
    }

    /**
     * 获取头部标题的TextView
     *
     * @return
     */
    public TextView getToolbarSubTitle() {
        return mToolbarSubTitle;
    }

    /**
     * 设置头部标题
     *
     * @param title
     */
    public void setToolBarTitle(CharSequence title) {
        if (mToolbarTitle != null) {
            mToolbarTitle.setText(title);
        } else {
            getToolbar().setTitle(title);
        }
    }

    public void setToolbarSubTitle(String title) {
        if (getToolbarSubTitle() != null) {
            mToolbarSubTitle.setText(title);
        }
    }

    /**
     * 是否显示后退按钮,默认显示,可在子类重写该方法.
     *
     * @return
     */
    public boolean isShowBacking() {
        return true;
    }

    /**
     * 版本号小于21的后退按钮图片
     */
    private void showBack() {
        getToolbar().setNavigationIcon(R.drawable.navigation_back_button_selector);
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * @param msg
     * @param callback
     */
    public void showProgressDialog(String msg, DialogFrag.DialogCallBackListerner callback) {
        if (mDialog != null)
            mDialog.showProgressDialog(msg, callback);
    }

    /**
     * 关闭dialog
     */
    public void dismissProgressDialog() {
        if (mDialog != null)
            mDialog.dissmissDialog();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ServiceManger.getInstance().onDestory();
    }
}
