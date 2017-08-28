package com.crv.sdk.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * 资源文件操作工具类
 * Created by Administrator on 2016-06-04.
 */
public class ResourceUtil {

    /**
     * 根据资源ID获取Drawable对象
     *
     * @param id
     * @return
     */
    public static Drawable getResourceId(Drawable id) {
        id.setBounds(0, 0, id.getMinimumWidth(), id.getMinimumHeight());
        return id;
    }

    /**
     * 根据ID从arraylist文件获取对应的字符
     *
     * @param context
     * @param pos
     * @param strArray
     * @return
     */
    public static String getStrById(Context context, int pos, int strArray) {
        String[] strings = context.getResources().getStringArray(strArray);
        return strings[pos];
    }
}
