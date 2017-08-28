package com.crv.ole.db;

import org.xutils.DbManager;
import org.xutils.x;

import java.io.File;

/**
 * Created by lihongshi on 2017/7/20.
 */

public class DBHelper {
    private static DBHelper instance;
    private DbManager db;

    // 定义一个私有构造方法
    private DBHelper() {
        initDaoConfig();
    }

    /**
     * 单例模式最优方案
     * 线程安全  并且效率高
     */
    public static DBHelper getInstance() {
        if (instance == null) {
            synchronized (DBHelper.class) {
                if (instance == null) {
                    instance = new DBHelper();
                }
            }
        }
        return instance;
    }

    private void initDaoConfig() {
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName("oleApp.db")
                //设置数据库路径，默认存储在app的私有目录
             //   .setDbDir(new File("/mnt/sdcard/"))
                .setDbVersion(1)
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        // 开启WAL, 对写入加速提升巨大
                        db.getDatabase().enableWriteAheadLogging();
                    }
                })
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                    }
                });
        db = x.getDb(daoConfig);
    }

    public DbManager getDbManager() {
        return db;
    }


}
