package com.crv.ole.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fairy on 2017/7/27.
 * 用户注册及开绑卡成功后结束掉中心页面工具类
 */

public class FinishUtils {
    private List<Activity> activityList;
    private static FinishUtils finishUtils;

    public static FinishUtils getInstance(){
        if(finishUtils == null){
            finishUtils = new FinishUtils();
        }
        return finishUtils;
    }

    public void addActivity(Activity activity){
        if(activityList == null){
            activityList = new ArrayList<>();
        }
        activityList.add(activity);
    }

    public void clearActivityList(){
        activityList.clear();
    }

    public void finishActivityList(){
        for(Activity activity : activityList){
            if(activity != null){
                activity.finish();
            }
        }
    }

}
