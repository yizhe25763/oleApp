package com.crv.sdk.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.crv.sdk.dialog.FragmentDialog;
import com.crv.sdk.dialog.IDialog;
import com.crv.sdk.utils.PreferencesHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * Created by Administrator on 2016-04-16.
 */
public class BaseFragment extends Fragment {
    public String TAG = getClass().getSimpleName(); // 标注页面名称，便于打印，在页面中自己设置
    protected IDialog mDialog;
    protected String dialogTag;
    protected FragmentActivity activity;
    protected View view;
    protected Context mContext;
    protected PreferencesHelper mPreferencesHelper;
    /**
     * 表示页面加载过数据，和showThisPage配合使用
     */
    protected boolean isLoad = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        activity = getActivity();
        mContext = getActivity();
        mDialog = new FragmentDialog(activity);
        mPreferencesHelper = new PreferencesHelper(mContext);
        Log.i("ole", "创建:" + getClass().getSimpleName());
    }

    private CompositeDisposable compositeDisposable;

    public void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    public void dispose() {

        if (compositeDisposable != null) compositeDisposable.dispose();
    }

    /**
     * 表示切换到当前fragment
     */
    public void showThisPage() {
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        Log.i("ole", "销毁:" + getClass().getSimpleName());
        dispose();
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void busEvent(String event) {
        eventBus(event);
    }

    //子类需要监听的地方需要复写该方法
    public void eventBus(String event) {

    }
}
