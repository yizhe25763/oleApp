package com.crv.sdk.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SdCardUtils {
    // 返回值不带File seperater "/",如果没有外置第二个sd卡,返回null
    public static String getSecondExterPath() {
        List<String> paths = getAllExterSdcardPath();

        if (paths.size() == 2) {

            for (String path : paths) {
                if (path != null && !path.equals(getFirstExterPath())) {
                    return path;
                }
            }
            return null;
        } else {
            return null;
        }
    }

    public static boolean isFirstSdcardMounted() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static boolean isSecondSDcardMounted() {
        String sd2 = getSecondExterPath();
        if (sd2 == null) {
            return false;
        }
        return checkFsWritable(sd2 + File.separator);

    }

    // 测试外置sd卡是否卸载，不能直接判断外置sd卡是否为null，因为当外置sd卡拔出时，仍然能得到外置sd卡路径。我这种方法是按照android谷歌测试DICM的方法，
    // 创建一个文件，然后立即删除，看是否卸载外置sd卡
    // 注意这里有一个小bug，即使外置sd卡没有卸载，但是存储空间不够大，或者文件数已至最大数，此时，也不能创建新文件。此时，统一提示用户清理sd卡吧
    private static boolean checkFsWritable(String dir) {

        if (dir == null)
            return false;

        File directory = new File(dir);

        if (!directory.isDirectory()) {
            if (!directory.mkdirs()) {
                return false;
            }
        }

        File f = new File(directory, ".keysharetestgzc");
        try {
            if (f.exists()) {
                f.delete();
            }
            if (!f.createNewFile()) {
                return false;
            }
            f.delete();
            return true;

        } catch (Exception e) {
        }
        return false;

    }

    public static String getFirstExterPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    public static List<String> getAllExterSdcardPath() {
        List<String> SdList = new ArrayList<String>();

        String firstPath = getFirstExterPath();

        // 得到路径
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            String line;
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                // 将常见的linux分区过滤掉
                if (line.contains("secure"))
                    continue;
                if (line.contains("asec"))
                    continue;
                if (line.contains("media"))
                    continue;
                if (line.contains("system") || line.contains("cache") || line.contains("sys") || line.contains("data") || line.contains("tmpfs")
                        || line.contains("shell") || line.contains("root") || line.contains("acct") || line.contains("proc") || line.contains("misc")
                        || line.contains("obb")) {
                    continue;
                }

                if (line.contains("fat") || line.contains("fuse") || (line.contains("ntfs"))) {

                    String columns[] = line.split(" ");
                    if (columns != null && columns.length > 1) {
                        String path = columns[1];
                        if (path != null && !SdList.contains(path) && path.contains("sd"))
                            SdList.add(columns[1]);
                    }
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (!SdList.contains(firstPath)) {
            SdList.add(firstPath);
        }

        return SdList;
    }

    public static String getRootPath() {
        String SDCard = SdCardUtils.getSecondExterPath();
        if (StringUtils.isNullOrEmpty(SDCard)) {
            SDCard = Environment.getExternalStorageDirectory() + "";
        }
        return SDCard + "/BabySong/";// 文件存储路径
    }

    /**
     * 检测是否有SD卡
     *
     * @return
     */
    public static boolean isHasSdcard() {
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
    }


    /**
     * 剩余sdcard空间大小(剩余空间需要大于5M)
     *
     * @return
     */
    public static boolean readSDCard() {
        boolean bR = true;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long blockSize = sf.getBlockSize();
            long availCount = sf.getAvailableBlocks();
            if ((availCount * blockSize / 1024) < 1024 * 5) {
                bR = false;
            }
        }
        return bR;
    }

    /**
     * 剩余sdcard空间大小(剩余空间需要大于5M)
     *
     * @return
     */
    public static boolean readSDCard(long size) {
        boolean bR = true;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long blockSize = sf.getBlockSize();
            long availCount = sf.getAvailableBlocks();
            if ((availCount * blockSize / 1024) < size / 1024) {
                bR = false;
            }
        } else {
            bR = false;
        }
        return bR;
    }

    /**
     * 判断sd卡是否存在
     *
     * @return
     */
    public static boolean hasSD() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取存储盘的路径
     *
     * @param c
     * @return
     */
    public static String getPath(Context c) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        } else
            return c.getFilesDir().getAbsolutePath();
    }


    /**
     * 判断sd空间大小
     * @return boolean
     */
    public static boolean hasSDSpace(long size) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String sdcard = Environment.getExternalStorageDirectory().getPath();
            StatFs statFs = new StatFs(sdcard);
            long blockSize = statFs.getBlockSize();
            long blocks = statFs.getAvailableBlocks();
            long availableSpare = (blocks * blockSize) / (1024 * 1024);
            return size <= availableSpare;
        }
        return false;
    }

}
