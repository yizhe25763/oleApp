package com.crv.sdk.config;

import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2016-04-19.
 */
public class LibConstants {
    /**
     * 基础路径
     */
    public static String BASE_PATH = Environment.getExternalStorageDirectory()+ File.separator+".ljf"+File.separator;


    /**
     * 图片缓存的位置
     */
    public static String IMAGE_PATH = BASE_PATH +"images";

    /**
     * 临时文件夹
     */
    public static String TMP_PATH = BASE_PATH + "temp";
}
