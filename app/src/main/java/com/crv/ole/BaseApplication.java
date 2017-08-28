package com.crv.ole;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.crv.ole.config.OleUnicornImageLoader;
import com.crv.ole.home.activity.MainActivity;
import com.crv.ole.home.model.UserCenterData;
import com.crv.ole.utils.OleConstants;
import com.crv.sdk.utils.PreferencesHelper;
import com.lzy.okgo.OkGo;
import com.qiyukf.unicorn.api.SavePowerConfig;
import com.qiyukf.unicorn.api.StatusBarNotificationConfig;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.YSFOptions;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.common.QueuedWork;
import com.vondear.rxtools.RxUtils;

import org.xutils.x;

import java.lang.reflect.Method;


/**
 * Created by Administrator on 2016-04-15.
 */

public class BaseApplication extends Application {
    private static BaseApplication instance;
    private static int deviceWidth;
    private static int deviceHeight;
    private PreferencesHelper mPreferencesHelper;
    private UserCenterData.UserCenter userCenter;
    private String requestCookieParams;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        instance = this;
        getScreenSize(this);
        mPreferencesHelper = new PreferencesHelper(this);
    }

    public String getUserId() {
        return mPreferencesHelper.getString(OleConstants.KEY.USER_ID, "");
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    /***
     * 此方法用于获取带虚拟按键机型的手机屏幕尺寸
     */
    private void getScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        @SuppressWarnings("rawtypes") Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked") Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);

            //当高度小于宽度时，设置宽度的值为高度
            deviceWidth = Math.min(dm.widthPixels, dm.heightPixels);

            //当宽度大于高度时，设置高度的值为宽度
            deviceHeight = Math.max(dm.widthPixels, dm.heightPixels);
        } catch (Exception e) {
            Log.e("ToolUtils", e.getMessage());
        }
    }

    public static int getDeviceWidth() {
        return deviceWidth;
    }

    public static int getDeviceHeight() {
        return deviceHeight;
    }

    public UserCenterData.UserCenter getUserCenter() {
        return userCenter;
    }

    public void setUserCenter(UserCenterData.UserCenter userCenter) {
        this.userCenter = userCenter;
    }

    public String getRequestCookieParams() {
        return requestCookieParams;
    }

    public void setRequestCookieParams(String requestCookieParams) {
        this.requestCookieParams = requestCookieParams;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 请在此处做一些初始化操作,禁止耗时的处理
     */
    private void init() {
        //集成友盟分享/登录
        Config.DEBUG = BuildConfig.DEBUG;
        QueuedWork.isUseThreadPool = false;
        PlatformConfig.setWeixin("wx2f5cf9427fe5667b", "070c4bffeb870adb76cd6382a4aef6bc");
        PlatformConfig.setSinaWeibo("273950325", "d34499cb7e82e5f40ce984a39a698c14","http://www.crvole.com.cn/");
        //PlatformConfig.setQQZone("1106273233", "KEYBbz9nrZiHXJXb2w6");
       // PlatformConfig.setQQZone("1106294125", "DLm7igRnQSvUagiS");
        PlatformConfig.setQQZone("1106365962", "mZY9wCOJeJgQzJOj");
        UMShareAPI.get(this);
        // 处理app crash
        initBugly();

        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);

        OkGo.getInstance().init(this);

        RxUtils.init(this);

        //初始化网易七鱼(在线客服)
        Unicorn.init(this, "f23f1207a75594a259a94f555cef203b", options(), new OleUnicornImageLoader(this));
        //绑定百度推送
        PushManager.startWork(this, PushConstants.LOGIN_TYPE_API_KEY, OleConstants.BAIDU_API_KEY);
    }


    // 如果返回值为null，则全部使用默认参数。
    private YSFOptions options() {
        YSFOptions options = new YSFOptions();
        options.statusBarNotificationConfig = new StatusBarNotificationConfig();
        options.statusBarNotificationConfig.notificationEntrance = MainActivity.class;
        options.statusBarNotificationConfig.notificationSmallIconId = R.drawable.logo;
        options.savePowerConfig = new SavePowerConfig();
        return options;
    }

    private void initBugly() {
        /**
         * 设置通知栏大图标，largeIconId为项目中的图片资源;
         */
        Beta.largeIconId = R.drawable.logo_ole;
        /**
         * 只允许在MainActivity上显示更新弹窗，其他activity上不显示弹窗;
         * 不设置会默认所有activity都可以显示弹窗;
         */
        //bugly初始化
        Bugly.init(this, "b015338602", BuildConfig.ShowBuglyLog);

    }
}


