package com.crv.ole.push;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.crv.ole.utils.Log;
import com.igexin.sdk.GTServiceManager;

/**
 * Created by yanghongjun on 17/8/11.
 */

public class GetuiPushService extends Service {


    @Override
    public void onCreate() {
        // 该行日志在 release 版本去掉
        Log.d(" call -> onCreate -------");

        super.onCreate();
        GTServiceManager.getInstance().onCreate(this);
    }

//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        super.onStartCommand(intent, flags, startId);
//
//        return 1;
//
//      //  return  GTServiceManager.getInstance().onStartCommand(this,intent,flags,startId);
//    }

    @Override
    public IBinder onBind(Intent intent) {
        // 该行日志在 release 版本去掉
        Log.d("onBind -------");
        return GTServiceManager.getInstance().onBind(intent);
    }

    @Override
    public void onDestroy() {
        // 该行日志在 release 版本去掉
        Log.d("onDestroy -------");

        super.onDestroy();
        GTServiceManager.getInstance().onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        GTServiceManager.getInstance().onLowMemory();
    }
}
