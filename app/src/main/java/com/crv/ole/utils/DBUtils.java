package com.crv.ole.utils;

import org.xutils.DbManager;
import org.xutils.x;

/**
 * 数据库工具类
 * 使用xUtils中的数据库组件
 * Created by wj_wsf on 2017/7/5 14:20.
 */

public class DBUtils {

    private static int DB_VERSION = 1;
    private static String DB_NAME = "Ole_db";
    private static DbManager.DaoConfig daoConfig;

    public static DbManager getDbManager() {
        return x.getDb(getDaoConfig());
    }

    private static DbManager.DaoConfig getDaoConfig() {
        //可以设置数据库文件存放位置
//        File file = new File(Environment.getExternalStorageDirectory().getPath());
        if (daoConfig == null) {
            daoConfig = new DbManager.DaoConfig()
                    .setDbName(DB_NAME)
//                    .setDbDir(file)
                    .setDbVersion(DB_VERSION)
                    .setAllowTransaction(true)//允许开启事物
                    .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                        @Override
                        public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                            //数据库有更新时回调此方法
                        }
                    });
        }
        return daoConfig;
    }

    public static int getDbVersion() {
        return DB_VERSION;
    }

    public static String getDbName() {
        return DB_NAME;
    }

}
