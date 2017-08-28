package com.crv.ole.base;

import android.os.Build;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.crv.ole.BaseApplication;
import com.crv.ole.utils.Log;


/**
 * Created by yanghongjun on 17/8/10.
 */

public class CookieHelper {

    public static boolean syncCookie(String url) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(BaseApplication.getInstance());
        }
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setCookie(url, BaseApplication.getInstance().getRequestCookieParams());
        String newCookie = cookieManager.getCookie(url);
        Log.i("cookie是这个:" + newCookie);
        return !TextUtils.isEmpty(newCookie);
    }

}
