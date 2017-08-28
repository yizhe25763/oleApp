package com.crv.sdk.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import static android.content.Context.MODE_PRIVATE;

/**
 * @author Rocky
 * @version V1.0
 *          Create Date: 2015-2-10 上午9:29:34
 * @Title: FileUtils.java
 * @Package com.ewj.lib.utils
 * @Description: 文件工具类
 * Copyright:ewj.com All Rights Reserved
 */
public class FileUtils
{
    public static final String DEFAULT_CHARSET_NAME = "UTF-8";

    /**
     * 读取文件的所有字符串
     */
    public static String read(String path)
    {
        FileInputStream is = null;
        try
        {
            is = new FileInputStream(path);
            byte[] data = new byte[is.available()];
            is.read(data);
            return new String(data, DEFAULT_CHARSET_NAME);
        }
        catch (Exception e)
        {
        }
        finally
        {
            try
            {
                if (is != null)
                    is.close();
            }
            catch (IOException e)
            {
            }
        }
        return null;
    }

    /**
     * 判断文件是否存在
     */
    public static boolean isExist(String path)
    {
        File file = new File(path);
        return file.exists();
    }

    /**
     * 从url中获取图片的名字
     */
    public static String getFileName(String url)
    {
        int index = url.lastIndexOf("/");
        String name = null;
        if (index == -1)
            return name;
        name = url.substring(index + 1);
        return name;
    }

    /**
     * 从url中获取后缀名
     */
    public static String getExtensionName(String url)
    {
        // 获取文件的后缀名
        int index = url.lastIndexOf('.');
        String fileEndName = null;
        if (index > 1)
        {
            fileEndName = url.substring(url.lastIndexOf('.') + 1);
        }
        return fileEndName;
    }

    /**
     * 创建文件夹
     */
    public static boolean createDir(String path)
    {
        File file = new File(path);
        boolean r = false;
        if (!file.exists())
        {
            r = file.mkdirs();
        }
        return r;
    }

    /**
     * 创建文件
     */
    public static boolean createFile(String fileFullPath)
    {
        File file = new File(fileFullPath);
        boolean r = false;
        if (!file.exists())
        {
            try
            {
                if (file.getParentFile() != null
                        && !file.getParentFile().exists())
                {
                    r = file.getParentFile().mkdirs();
                }
                r = file.createNewFile();
            }
            catch (IOException e)
            {
            }
        }
        return r;
    }

    /**
     * 创建文件
     */
    public static boolean createFile(File file)
    {
        boolean r = false;
        if (!file.exists())
        {
            try
            {
                if (file.getParentFile() != null
                        && !file.getParentFile().exists())
                {
                    r = file.getParentFile().mkdirs();
                }
                r = file.createNewFile();
            }
            catch (IOException e)
            {
            }
        }
        return r;
    }

    /**
     * 删除文件，可以是单个文件或文件夹
     *
     * @param fileName 待删除的文件名
     * @return 文件删除成功返回true, 否则返回false
     */

    public static boolean delete(String fileName)
    {
        File file = new File(fileName);
        if (!file.exists())
        {
            return false;
        }
        else
        {
            if (file.isFile())
            {
                return deleteFile(fileName);

            }
            else
            {
                return deleteDirectory(fileName);

            }

        }

    }

    /**
     * 把url转换成文件全路径
     */
    public static String urlToFile(String url, String path)
    {
        if (!TextUtils.isEmpty(url) && !TextUtils.isEmpty(path))
        {
            return path + File.separator + url;
        }
        return null;
    }

    /**
     * 删除文件，可以是单个文件或文件夹
     *
     * @return 文件删除成功返回true, 否则返回false
     */

    public static boolean delete(String filePath, final String filter)
    {
        File file = new File(filePath);
        String[] files = file.list(new FilenameFilter()
        {
            @Override
            public boolean accept(File dir, String filename)
            {
                return filename.toLowerCase().contains(filter);
            }
        });
        if (files != null && files.length > 0)
        {
            for (String f : files)
            {
                deleteFile(f);
            }
        }
        return true;
    }

    /**
     * 删除单个文件
     *
     * @param fileName 被删除文件的文件名
     * @return 单个文件删除成功返回true, 否则返回false
     */
    private static boolean deleteFile(String fileName)
    {
        File file = new File(fileName);
        if (file.isFile() && file.exists())
        {
            file.delete();
            return true;

        }
        else
        {
            return false;

        }
    }

    /**
     * 删除目录（文件夹）以及目录下的文件
     *
     * @param dir 被删除目录的文件路径
     * @return 目录删除成功返回true, 否则返回false
     */
    private static boolean deleteDirectory(String dir)
    {
        if (!dir.endsWith(File.separator))
        {// 如果dir不以文件分隔符结尾，自动添加文件分隔符
            dir = dir + File.separator;
        }
        File dirFile = new File(dir);
        if (!dirFile.exists() || !dirFile.isDirectory())
        {// 如果dir对应的文件不存在，或者不是一个目录，则退出
            return false;
        }
        boolean flag = true;
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++)
        {// 删除子文件
            if (files[i].isFile())
            {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag)
                {
                    break;
                }
            }
            else
            {// 删除子目录
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag)
                {
                    break;
                }
            }
        }
        if (!flag)
        {
            return false;

        }
        // 删除当前目录
        return dirFile.delete();
    }

    /**
     * 创建文件夹
     */
    public static boolean createDir(File path)
    {
        boolean r = false;
        if (path != null && !path.exists())
        {
            r = path.mkdirs();
        }
        return r;
    }

    /**
     * 得到父目录
     */
    public static String getParentPath(String path)
    {
        File file = new File(path);
        return file.getParent();
    }

    /**
     * 把字符串写入文件
     */
    public static void write(String path, byte[] str)
    {
        if (path != null && str != null)
        {
            FileOutputStream fos = null;
            try
            {
                fos = new FileOutputStream(path);
                fos.write(str);
            }
            catch (Exception e)
            {
            }
            finally
            {
                try
                {
                    if (fos != null)
                        fos.close();
                }
                catch (IOException e)
                {
                }
            }
        }
    }


    /**
     * 文件复制
     */
    public static void copy(File src, File dst) throws IOException
    {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);
        copyStream(in, out);
    }

    /**
     * 文件复制
     */
    public static void copy(String src, String dst) throws IOException
    {
        if (!TextUtils.isEmpty(src) && isExist(src) && !TextUtils.isEmpty(dst))
        {
            if (!isExist(dst)) createFile(dst);
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dst);
            copyStream(in, out);
        }
    }

    /**
     * 流复制
     */
    public static void copyStream(InputStream in, OutputStream out)
            throws IOException
    {
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0)
        {
            out.write(buf, 0, len);
        }
        out.flush();
        in.close();
        out.close();
    }

    /**
     * 流复制
     */
    public static void copyStream(InputStream in, String fileName)
            throws IOException
    {
        if (isExist(fileName))
        {
            delete(fileName);
        }
        copyStream(in, new BufferedOutputStream(new FileOutputStream(fileName)));
    }

    /**
     * 获取文件的大小
     */
    public static long fileSize(String file)
    {
        if (TextUtils.isEmpty(file))
        {
            return 0;
        }
        File f = new File(file);
        return f.exists() ? f.length() : 0;
    }

    /**
     * 保存图片至sd卡
     *
     * @param bm           图片
     * @param fullFileName 文件全名
     */
    public static void saveBitmapToSd(Bitmap bm, String fullFileName)
    {
        if (bm == null)
        {
            return;
        }
        File file = new File(fullFileName);
        OutputStream outStream = null;
        try
        {
            file.createNewFile();
            outStream = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
        }
        catch (Exception e)
        {
        }
        finally
        {
            if (outStream != null) closeStream(outStream);
        }
    }

    /**
     * 复制assets下的文件到指定路径
     *
     * @param fileName 文件名
     * @param path     文件路径
     */
    public static void copyAssetsData(Context context, String fileName, String path)
            throws IOException
    {
        String name = fileName.contains(File.separator) ? fileName.substring(fileName.lastIndexOf(File.separator) + 1) : fileName;
        FileUtils.copyStream(context.getAssets().open(name), new BufferedOutputStream(new FileOutputStream(path + File.separator + fileName)));
    }

    /**
     * 关闭流
     */
    public static void closeStream(Closeable stream)
    {
        if (stream != null)
        {
            try
            {
                stream.close();
            }
            catch (IOException e)
            {
            }
        }
    }

    /**
     * 将文件大小由字节转化为M
     */
    public static String fileSize2M(long size)
    {
        if (size / 1024 / 1024 >= 1)
        {
            return size / 1024 / 1024 + (size / 1024 % 1024 / 1024.0 + "").substring(1, 3) + "MB";
        }
        else
        {
            return size / 1024 + (size % 1024 / 1024.0 + "").substring(1, 3) + "KB";
        }
    }

    public static File createRootDir(Context context, String rootPath)
    {
        File file = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            (file = context.getExternalFilesDir(rootPath)).mkdirs();
        }
        else
        {
            (file = new File(context.getFilesDir(), rootPath)).mkdirs();
        }
        return file;
    }

    /**
     * 写入对象到文件
     */
    public static void writeFile(Context context, String fileName, String content)
    {
        try
        {

            FileOutputStream fout = context.openFileOutput(fileName, MODE_PRIVATE);

            byte[] bytes = content.getBytes();

            fout.write(bytes);

            fout.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 从本地文件获取内容
     */
    public static String readFile(Context context, String fileName)
    {
        String res = "";
        try
        {
            FileInputStream fin = context.openFileInput(fileName);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            res = new String(buffer, "UTF-8");
            fin.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return res;

    }

    /**
     * 从本地文件获取内容
     */
    public static String readAssetsFile(Context context, String fileName)
    {
        String result = "";
        try
        {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            while ((line = bufReader.readLine()) != null)
                result += line;
            return result;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;

    }

    /**
     * 根据指定的图像路径和大小来获取缩略图
     * 此方法有两点好处：
     * 1. 使用较小的内存空间，第一次获取的bitmap实际上为null，只是为了读取宽度和高度，
     * 第二次读取的bitmap是根据比例压缩过的图像，第三次读取的bitmap是所要的缩略图。
     * 2. 缩略图对于原图像来讲没有拉伸，这里使用了2.2版本的新工具ThumbnailUtils，使
     * 用这个工具生成的图像不会被拉伸。
     *
     * @param imagePath 图像的路径
     * @param width     指定输出图像的宽度
     * @param height    指定输出图像的高度
     * @return 生成的缩略图
     */
    public static Bitmap getImageThumbnail(String imagePath, int width, int height)
    {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 获取这个图片的宽和高，注意此处的bitmap为null
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        options.inJustDecodeBounds = false; // 设为 false
        // 计算缩放比
        int h = options.outHeight;
        int w = options.outWidth;
        int beWidth = w / width;
        int beHeight = h / height;
        int be = 1;
        if (beWidth < beHeight)
        {
            be = beWidth;
        }
        else
        {
            be = beHeight;
        }
        if (be <= 0)
        {
            be = 1;
        }
        options.inSampleSize = be;
        // 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        // 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    /**
     * 获取视频的缩略图
     * 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
     * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
     *
     * @param videoPath 视频的路径
     * @param width     指定输出视频缩略图的宽度
     * @param height    指定输出视频缩略图的高度度
     * @param kind      参照MediaStore.Images.Thumbnails类中的常量MINI_KIND和MICRO_KIND。
     *                  其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
     * @return 指定大小的视频缩略图
     */
    public static Bitmap getVideoThumbnail(String videoPath, int width, int height, int kind)
    {
        Bitmap bitmap = null;
        // 获取视频的缩略图
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        System.out.println("w" + bitmap.getWidth());
        System.out.println("h" + bitmap.getHeight());
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    /**
     * Uri转真实路径
     */
    public static String getRealFilePath(final Context context, final Uri uri)
    {
        if (null == uri)
            return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme))
        {
            data = uri.getPath();
        }
        else if (ContentResolver.SCHEME_CONTENT.equals(scheme))
        {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor)
            {
                if (cursor.moveToFirst())
                {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1)
                    {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 根据Uri获取文件名
     */
    public static String getFileName(final Context context, final Uri uri)
    {
        String filePath = getRealFilePath(context, uri);
        if (StringUtils.isNullOrEmpty(filePath))
        {
            return null;
        }
        return filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length());
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath "/storage/sdcard0/Manual/test.pdf"
     */
    public static boolean fileIsExists(String filePath)
    {
        try
        {
            File f = new File(filePath);
            if (!f.exists())
            {
                return false;
            }
        }
        catch (Exception e)
        {
            return false;
        }
        return true;
    }

    public static byte[] toByteArray(String filename) throws IOException {

        File f = new File(filename);
        if (!f.exists()) {
            throw new FileNotFoundException(filename);
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(f));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bos.close();
        }
    }

    /**
     * NIO way
     *
     * @param filename
     * @return
     * @throws IOException
     */
    public static byte[] toByteArray2(String filename) throws IOException {

        File f = new File(filename);
        if (!f.exists()) {
            throw new FileNotFoundException(filename);
        }

        FileChannel channel = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {
                // do nothing
                // System.out.println("reading");
            }
            return byteBuffer.array();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Mapped File way MappedByteBuffer 可以在处理大文件时，提升性能
     *
     * @param filename
     * @return
     * @throws IOException
     */
    public static byte[] toByteArray3(String filename) throws IOException {

        FileChannel fc = null;
        try {
            fc = new RandomAccessFile(filename, "r").getChannel();
            MappedByteBuffer byteBuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0,
                    fc.size()).load();
            System.out.println(byteBuffer.isLoaded());
            byte[] result = new byte[(int) fc.size()];
            if (byteBuffer.remaining() > 0) {
                // System.out.println("remain");
                byteBuffer.get(result, 0, byteBuffer.remaining());
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                fc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
