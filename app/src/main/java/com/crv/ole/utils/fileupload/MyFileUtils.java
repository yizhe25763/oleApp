package com.crv.ole.utils.fileupload;


import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.crv.ole.BaseApplication;
import com.crv.ole.utils.Log;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class MyFileUtils extends FileUtils
{
    /**
     *
     */
    public static String ROOT_PATH = null;

    /**
     * 公共资源下载缓存文件路径
     */
    public static String RESOURCE_COMMON_CACHE_PATH = null;
    /**
     * 用户文件资源下载缓存文件路径
     */
    public static String RESOURCE_USER_FILE_CACHE_PATH = null;
    /**
     * 用户图片资源下载缓存文件路径
     */
    public static String RESOURCE_USER_IMAGE_CACHE_PATH = null;
    /**
     * 用户vCard缓存文件路径
     */
    public static String RESOURCE_USER_VCARD_CACHE_PATH = null;
    /**
     * 程序安装目录中存放本地静态文件的路径。通常是在第一次运行程序是拷贝安装目录时用
     */
    public static final String LOCAL_FILE_PATH = "/data/data/com.strangecity/files";

    /**
     * 用户头像
     **/
    public static String RESOURCE_USER_HEAD_IMAGE_CACHE_PATH = null;
    /**
     * 版本更新路径
     **/
    public static String USER_UPDATE_APP_PATH = null;
    /**
     * 版本更新路径
     **/
    public static String ADDRESS_DB_PATH = null;
    /**
     * 热更新路径
     **/
    public static String RESOURCE_APATCH_PATH = null;
    /**
     * 离线资源路径
     **/
    public static String RESOURCE_OFFLINE_RES_PATH = null;


    /**
     * 用户个人文件
     */
    public static void initStoragePth()
    {
        String userId = BaseApplication.getInstance().getUserId() + "";
        ROOT_PATH = createRootDir(BaseApplication.getInstance(), "myFile").getAbsolutePath();
        if (ROOT_PATH != null && !TextUtils.isEmpty(userId))
        {
            // 资源目录开发中先不建隐藏目录。
            RESOURCE_USER_FILE_CACHE_PATH = ROOT_PATH + File.separator
                    + userId + File.separator + "file";
            RESOURCE_USER_IMAGE_CACHE_PATH = ROOT_PATH + File.separator
                    + userId + File.separator + "image";
            RESOURCE_USER_VCARD_CACHE_PATH = ROOT_PATH + File.separator
                    + userId + File.separator + "vcard";
            //头像不需要根据用户区分地址
            RESOURCE_USER_HEAD_IMAGE_CACHE_PATH = ROOT_PATH + File.separator
                    + userId + File.separator + "head_image";
            //版本升级不需要根据用户区分地址
            USER_UPDATE_APP_PATH = ROOT_PATH + File.separator
                    + "update_app";
            //地址数据库
            ADDRESS_DB_PATH = ROOT_PATH + File.separator
                    + "address_db";
            RESOURCE_APATCH_PATH = ROOT_PATH + File.separator
                    + "apatch";
            RESOURCE_OFFLINE_RES_PATH = ROOT_PATH + File.separator
                    + "offline_res";
        }
        else if (ROOT_PATH != null)
        {
            RESOURCE_USER_FILE_CACHE_PATH = ROOT_PATH + File.separator
                    + "file";
            RESOURCE_USER_IMAGE_CACHE_PATH = ROOT_PATH + File.separator
                    + "image";
            RESOURCE_USER_VCARD_CACHE_PATH = ROOT_PATH + File.separator
                    + "vcard";
            RESOURCE_USER_HEAD_IMAGE_CACHE_PATH = ROOT_PATH
                    + File.separator + "head_image";
            USER_UPDATE_APP_PATH = ROOT_PATH + File.separator
                    + "update_app";
            ADDRESS_DB_PATH = ROOT_PATH + File.separator
                    + "address_db";
            RESOURCE_APATCH_PATH = ROOT_PATH + File.separator
                    + "apatch";
            RESOURCE_OFFLINE_RES_PATH = ROOT_PATH + File.separator
                    + "offline_res";
        }
    }

    /**
     * 用户个人文件
     */
    public static void initStoragePath()
    {
        String userId="";
        userId = BaseApplication.getInstance().getUserId() + "";
        ROOT_PATH = createRootDir(BaseApplication.getInstance(), "hrtFile").getAbsolutePath();
        if (ROOT_PATH != null && !TextUtils.isEmpty(userId))
        {
            // 资源目录开发中先不建隐藏目录。
            RESOURCE_USER_FILE_CACHE_PATH = ROOT_PATH + File.separator
                    + userId + File.separator + "file";
            RESOURCE_USER_IMAGE_CACHE_PATH = ROOT_PATH + File.separator
                    + userId + File.separator + "image";
            RESOURCE_USER_VCARD_CACHE_PATH = ROOT_PATH + File.separator
                    + userId + File.separator + "vcard";
            //头像不需要根据用户区分地址
            RESOURCE_USER_HEAD_IMAGE_CACHE_PATH = ROOT_PATH + File.separator
                    + userId + File.separator + "head_image";
            //版本升级不需要根据用户区分地址
            USER_UPDATE_APP_PATH = ROOT_PATH + File.separator
                    + "update_app";
            //地址数据库
            ADDRESS_DB_PATH = ROOT_PATH + File.separator
                    + "address_db";
            RESOURCE_APATCH_PATH = ROOT_PATH + File.separator
                    + "apatch";
            RESOURCE_OFFLINE_RES_PATH = ROOT_PATH + File.separator
                    + "offline_res";
        }
        else if (ROOT_PATH != null)
        {
            RESOURCE_USER_FILE_CACHE_PATH = ROOT_PATH + File.separator
                    + "file";
            RESOURCE_USER_IMAGE_CACHE_PATH = ROOT_PATH + File.separator
                    + "image";
            RESOURCE_USER_VCARD_CACHE_PATH = ROOT_PATH + File.separator
                    + "vcard";
            RESOURCE_USER_HEAD_IMAGE_CACHE_PATH = ROOT_PATH
                    + File.separator + "head_image";
            USER_UPDATE_APP_PATH = ROOT_PATH + File.separator
                    + "update_app";
            ADDRESS_DB_PATH = ROOT_PATH + File.separator
                    + "address_db";
            RESOURCE_APATCH_PATH = ROOT_PATH + File.separator
                    + "apatch";
            RESOURCE_OFFLINE_RES_PATH = ROOT_PATH + File.separator
                    + "offline_res";
        }
    }

    /**
     * 用户头像 缓存目录 userId一般为userID
     **/
    public static String getUserHeadImageCacheDir()
    {
        initStoragePath();
        checkDirectory(new File(RESOURCE_USER_HEAD_IMAGE_CACHE_PATH));
        return RESOURCE_USER_HEAD_IMAGE_CACHE_PATH;
    }

    /**
     * 个人相册 图片
     ***/
    public static String getUserImageCacheDir()
    {
        initStoragePath();
        checkDirectory(new File(RESOURCE_USER_IMAGE_CACHE_PATH));
        return RESOURCE_USER_IMAGE_CACHE_PATH;
    }

    /**
     * 用户临时文件
     ***/
    public static String getUserFileCacheDir()
    {
        initStoragePath();
        checkDirectory(new File(RESOURCE_USER_FILE_CACHE_PATH));
        return RESOURCE_USER_FILE_CACHE_PATH;
    }

    /**
     * 用户临时文件
     ***/
    public static String getUserVcardCacheDir()
    {
        initStoragePath();
        checkDirectory(new File(RESOURCE_USER_VCARD_CACHE_PATH));
        return RESOURCE_USER_VCARD_CACHE_PATH;
    }

    /**
     * APP 更新
     ***/
    public static String getUpdateAppPath()
    {
        initStoragePath();
        checkDirectory(new File(USER_UPDATE_APP_PATH));
        return USER_UPDATE_APP_PATH;
    }

    /**
     * 地址
     ***/
    public static String getAddressDbPath()
    {
        initStoragePath();
        checkDirectory(new File(ADDRESS_DB_PATH));
        return ADDRESS_DB_PATH;
    }

    /**
     * 离线资源
     ***/
    public static String getOfflineResPath()
    {
        initStoragePath();
        checkDirectory(new File(RESOURCE_OFFLINE_RES_PATH));
        return RESOURCE_OFFLINE_RES_PATH;
    }

    /**
     * 热 更新
     ***/
    public static String getApatchPath()
    {
        initStoragePath();
        checkDirectory(new File(RESOURCE_APATCH_PATH));
        return RESOURCE_APATCH_PATH;
    }


    public static String getUserVcardFilePath(String vcardname)
    {
        return getUserVcardCacheDir() + File.separator + vcardname + ".vcf";
    }

    public static void checkDirectory(File dir)
    {
        if (dir.exists())
        {
            if (!dir.isDirectory() && !dir.delete() && !dir.mkdirs())
            {
                throw new RuntimeException("create file(" + dir + ") fail.");
            }
        }
        else if (!dir.mkdirs())
        {
            throw new RuntimeException("create file(" + dir + ") fail.");
        }
    }

    /**
     * 获取APP 存储的路径
     */
    public static String getAppStorageDir(Context context)
    {
        // 获取Android程序在Sd上的保存目录约定 当程序卸载时，系统会自动删除。
        File f = context.getExternalFilesDir(null);
        // 如果约定目录不存在
        if (null == f)
        {
            // 获取外部存储目录即 SDCard
            return getStorageDir(context);
        }
        else
        {
            String storageDirectory = f.getAbsolutePath();
            Log.w("项目存储路径采用系统给的路径地址  storageDirectory:" + storageDirectory);
            return storageDirectory;
        }
    }

    /***
     * 异常log信息存储 目录
     */
    public static String getCrashErrorPath()
    {
        initStoragePath();
        return ROOT_PATH + "/" + "error_crash";
    }

    /**
     * 获取可用的sdcard路径
     */
    public static String getStorageDir(Context context)
    {
        return getStorageDir(context, true);
    }

    /**
     * 获取可用的sdcard路径
     */
    public static String getStorageDir(Context context, boolean isAllowUseCache)
    {
        // 获取外部存储目录即 SDCard
        String storageDirectory = Environment.getExternalStorageDirectory().toString();
        File fDir = new File(storageDirectory);
        // 如果sdcard目录不可用
        if (!fDir.canWrite())
        {
            // 获取可用
            storageDirectory = getSDCardDir();
            if (storageDirectory != null)
            {
                storageDirectory = storageDirectory + File.separator + ".Hrt";
                Log.w("项目存储路径采用自动找寻可用存储空间的方式   storageDirectory:" + storageDirectory);
                return storageDirectory;

            }
            else
            {
                if (isAllowUseCache)
                {
                    Log.w("没有找到可用的存储路径  采用cachedir");
                    storageDirectory = LOCAL_FILE_PATH;
                    return storageDirectory;
                }
                else
                {
                    return null;
                }
            }
        }
        else
        {
            storageDirectory = storageDirectory + File.separator + ".Hrt";
            Log.w("项目存储路径采用sdcard的地址   storageDirectory:" + storageDirectory);
            return storageDirectory;
        }
    }

    public static boolean isFile(String filePath)
    {
        if (filePath != null)
        {
            if (new File(filePath).isFile())
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取一个可用的存储路径（可能是内置的存储路径）
     *
     * @return 可用的存储路径
     */
    public static String getSDCardDir()
    {
        String pathDir = null;
        // 先获取内置sdcard路径
        File sdfile = Environment.getExternalStorageDirectory();
        // 获取内置sdcard的父路径
        File parentFile = sdfile.getParentFile();
        // 列出该父目录下的所有路径
        File[] listFiles = parentFile.listFiles();
        // 如果子路径可以写 就是拓展卡（包含内置的和外置的）

        long freeSizeMax = 0L;
        for (int i = 0; i < listFiles.length; i++)
        {
            if (listFiles[i].canWrite())
            {
                // listFiles[i]就是SD卡路径
                String tempPathDir = listFiles[i].getAbsolutePath();
                long tempSize = getSDFreeSize(tempPathDir);
                if (tempSize > freeSizeMax)
                {
                    freeSizeMax = tempSize;
                    pathDir = tempPathDir;
                }
            }
        }
        return pathDir;
    }

    public static String getHrtStorageDir(Context context, boolean isAllowUseCache)
    {
        // 获取外部存储目录即 SDCard
        String storageDirectory = Environment.getExternalStorageDirectory().toString();
        File fDir = new File(storageDirectory);
        // 如果sdcard目录不可用
        if (!fDir.canWrite())
        {
            // 获取可用
            storageDirectory = getSDCardDir();
            if (storageDirectory != null)
            {
                storageDirectory = storageDirectory + File.separator + ".Hrt";
                Log.w("项目存储路径采用自动找寻可用存储空间的方式   storageDirectory:" + storageDirectory);
                return storageDirectory;

            }
            else
            {
                if (isAllowUseCache)
                {
                    Log.w("没有找到可用的存储路径  采用cachedir");
                    storageDirectory = LOCAL_FILE_PATH;
                    return storageDirectory;
                }
                else
                {
                    return null;
                }
            }
        }
        else
        {
            storageDirectory = storageDirectory + File.separator + ".Hrt";
            Log.w("项目存储路径采用sdcard的地址   storageDirectory:" + storageDirectory);
            return storageDirectory;
        }
    }

    /**
     * 获取指定目录剩余空间
     */
    public static long getSDFreeSize(String filePath)
    {

        try
        {
            File file = new File(filePath);
            if (!file.exists())
            {
                file.mkdirs();
            }

            android.os.StatFs statfs = new android.os.StatFs(filePath);

            long nBlocSize = statfs.getBlockSize(); // 获取SDCard上每个block的SIZE

            long nAvailaBlock = statfs.getAvailableBlocks(); // 获取可供程序使用的Block的数量

            long nSDFreeSize = nAvailaBlock * nBlocSize; // 计算 SDCard
            // 剩余大小B
            return nSDFreeSize;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return -1;
    }

    /**
     * 清除缓存文件
     */
    public static void clearAllData(Context context)
    {
        File file = new File("/data/data/" + context.getPackageName());
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++)
        {
            File f = files[i];
            if (!f.getName().equals("lib"))
            {
                deleteFile(f);
            }
        }
        File file2 = new File(ROOT_PATH);
        deleteFile(file2);
    }

    /**
     * 删除文件
     */
    public static boolean deleteFile(File file)
    {
        boolean result = false;
        try
        {
            if (file != null && file.exists())
            {
                if (file.isDirectory())
                {
                    Log.w("deleteFile  正在删除文件夹：" + file.getPath());
                    File[] files = file.listFiles();
                    if (files.length >= 1)
                    {
                        Log.w("deleteFile  文件夹 包含" + files.length + "个File");
                        for (int i = 0; i < files.length; i++)
                        {
                            deleteFile(files[i]);
                        }
                    }
                    boolean ch = file.delete();
                    Log.w("deleteFile  删除文件夹(" + file.getPath() + ")：" + ch);
                }
                else
                {
                    Log.w("deleteFile  正在删除文件：" + file.getPath());
                    boolean ch = file.delete();
                    Log.w("deleteFile  删除文件(" + file.getPath() + ")：" + ch);
                }
            }
            result = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result = false;
        }
        return result;

    }

    public static String getFileName(String filePath)
    {
        if (filePath != null && filePath.length() > 0)
        { // --截取文件名
            int i = filePath.lastIndexOf("/");
            return filePath.substring(i + 1, filePath.length());
        }
        return null;
    }

    private static boolean makeDir(String dir)
    {
        File destFloderPath = new File(dir);
        if (!destFloderPath.isDirectory())
        {
            return destFloderPath.mkdirs();
        }
        return true;
    }


    public static String getUserFileCacheFilePath(String userId, String postfix)
    {
        return getUserFileCacheDir() + File.separator + postfix + ".txt";
    }

    public static boolean isMounted()
    {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /**
     * 判断SD是否可以
     */
    public static boolean isSdcardExist()
    {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /***
     * 获取文件大小
     */
    private static long getFileSize(File file) throws Exception
    {
        long size = 0;
        File flist[] = file.listFiles();
        for (int i = 0; i < flist.length; i++)
        {
            if (flist[i].isDirectory())
            {
                size = size + getFileSize(flist[i]);
            }
            else
            {
                size = size + flist[i].length();
            }
        }
        return size;
    }

    /**
     * @param fileSize
     * @return
     */
    private static String FormetFileSize(long fileSize)
    {
        if (fileSize == 0)
        {
            return "0KB";
        }
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileSize < 1024)
        {
            fileSizeString = df.format((double) fileSize) + "B";
        }
        else if (fileSize < 1048576)
        {
            fileSizeString = df.format((double) fileSize / 1024) + "K";
        }
        else if (fileSize < 1073741824)
        {
            fileSizeString = df.format((double) fileSize / 1048576) + "M";
        }
        else
        {
            fileSizeString = df.format((double) fileSize / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 创建根目录
     *
     * @param path 目录路径
     */
    public static void createDirFile(String path)
    {
        File dir = new File(path);
        if (!dir.exists())
        {
            dir.mkdirs();
        }
    }

    /**
     * 创建文件
     *
     * @param path 文件路径
     * @return 创建的文件
     */
    public static File createNewFile(String path)
    {
        File file = new File(path);
        if (!file.exists())
        {
            try
            {
                file.createNewFile();
            }
            catch (IOException e)
            {
                return null;
            }
        }
        return file;
    }

    /**
     * 删除文件
     *
     * @param path 文件的路径
     */
    public static void delAllFile(String path)
    {
        File file = new File(path);
        if (!file.exists())
        {
            return;
        }
        if (!file.isDirectory())
        {
            return;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++)
        {
            if (path.endsWith(File.separator))
            {
                temp = new File(path + tempList[i]);
            }
            else
            {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile())
            {
                temp.delete();
            }
            if (temp.isDirectory())
            {
                delAllFile(path + "/" + tempList[i]);
                delFolder(path + "/" + tempList[i]);
            }
        }
    }

    /**
     * 删除文件夹
     *
     * @param folderPath 文件夹的路径
     */
    public static void delFolder(String folderPath)
    {
        delAllFile(folderPath);
        String filePath = folderPath;
        filePath = filePath.toString();
        File myFilePath = new File(filePath);
        myFilePath.delete();
    }

    // /** 获取用户头像 **/
    // public static void getUserHeadImage(FileFinishListener l) {
    // User user = JHDDataManager.getInstance().getUser();
    // if (user != null && user.imageUrl != null && user.imageUrl.length > 0
    // && user.imageUrl[0] != null) {
    // String path = user.imageUrl[0];
    // FileDownload_Upload.downloadFile(path, l,
    // getUserHeadImageCacheDir(String.valueOf(user.userID)));
    // }
    // }
    public static String splitUrl(String url)
    {
        if (url == null || url.trim().length() == 0)
        {
            return null;
        }
        String[] split = url.split(File.separator);
        if (split == null || split.length == 0)
        {
            return null;
        }
        return split[split.length - 1];
    }
}
