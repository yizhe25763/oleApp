package com.crv.ole.utils.fileupload;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.crv.ole.BaseApplication;
import com.crv.ole.utils.OleConstants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 上传文件工具类
 * Created by Administrator on 2016-09-08.
 */
public class UploadUtils
{
    public static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    public static final String HEADIMG_PATH = OleConstants.TMP_PATH + File.separator + "upload.png";
    //拍照路径
    public static final String TACK_PIC_PATH = OleConstants.TMP_PATH + File.separator + "temp.png";
    //截图宽
    static int width = 200;
    //截图高
    static int height = 200;

    public static final int CROP_PICTURE = 0x30;
    public static final int CHOOSE_PICTURE = 0x20;
    public static final int TAKE_PICTURE = 0x10;
    public static final int MSG_WHAT_UPLOAD_SUCCESS = 10000;
    public static final int MSG_WHAT_UPLOAD_FAILED = 10001;

    /**
     * 裁剪图片方法实现
     *
     * @param width  图片宽
     * @param height 图片高
     */
    public static void startPhotoZoom(Activity activity, Uri uri, int width, int height)
    {
        UploadUtils.width = width;
        UploadUtils.height = height;
        startPhotoZoom(activity, uri);
    }

    /**
     * 裁剪图片方法实现
     */
    public static void startPhotoZoom(Activity activity, Uri uri)
    {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        File file = new File(HEADIMG_PATH);
        Uri fromFile = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fromFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        activity.startActivityForResult(intent, CROP_PICTURE);
    }


    /**
     * 图片等比例压缩到不超过100Kb
     */
    public static void compress(Uri uri)
    {
        compress(uri.getPath());
    }

    /**
     * 旋转照片
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degress)
    {
        if (bitmap != null)
        {
            Matrix m = new Matrix();
            m.postRotate(degress);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }

    /**
     * 获取照片角度
     */
    public static int readPictureDegree(String path)
    {
        int degree = 0;
        try
        {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation)
            {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return degree;
    }

    public static String compressImgByDingyanhua(String filePath)
    {

        // 使用采样率压缩，不需要一开始把图片完全读取到内存，而是先读取图片的边，
        // 然后设置图片的尺寸，然后再根据尺寸，选择的读取像素。
        // 这种方法避免了一开始就吧图片读入内存而造成的oom异常。
        // 但是由于像素改变，压缩容易造成失真问题
        String tempImgName = System.currentTimeMillis() + "img.jpg";
        String targetPath = MyFileUtils.getUserImageCacheDir() + File.separator + tempImgName;
        float hh = BaseApplication.getDeviceHeight();
        float ww = BaseApplication.getDeviceWidth();
        BitmapFactory.Options opts = new BitmapFactory.Options();

        opts.inJustDecodeBounds = true; // 这一句话是允许读取图片的边

        // 值得注意的是，当options.inJustDecodeBounds 的值是true，只是读取图片的边，而不是读取整个图片，
        // 所以Bitmap bitmap = BitmapFactory.decodeFile(imagePath,options);得到的bitmap是空的
        Bitmap b = BitmapFactory.decodeFile(filePath, opts);
        int w = opts.outWidth;
        int h = opts.outHeight;
        if (b != null)
            b.recycle();
        int size = 0;
        if (w <= ww && h <= hh)
        {
            size = 1;
        }
        else
        {
            double scale = w >= h ? w / ww : h / hh;
            double log = Math.log(scale) / Math.log(2);
            double logCeil = Math.ceil(log);
            size = (int) Math.pow(2, logCeil);
        }

        // 这个参数是来设置图片的压缩倍数，可以通过options得到图片的高、宽来设定压缩的倍数
        opts.inSampleSize = size;

        // 获得图片的信息后，接下来为了能保证获得正常的bitmap，
        // 所以要关闭 options.inJustDecodeBounds ，即把他的值变为false；
        opts.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, opts);

        // 旋转  解决三星等手机图片歪了的问题
        int degree = readPictureDegree(filePath);
        if (degree != 0 && bitmap != null)
        {
            bitmap = rotateBitmap(bitmap, degree);
        }

        ByteArrayOutputStream baos = null;
        try
        {
            baos = new ByteArrayOutputStream();
            int quality = 80;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);

            while (baos.size() > 200 * 1024)
            {
                //baos.reset();
                if(quality<0||quality>100)break;
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
                quality -= 5;
            }

            baos.writeTo(new FileOutputStream(targetPath));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                baos.flush();
                baos.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return targetPath;

    }

    /**
     * 从图片路径中截取图片名
     *
     * @param imageUrl
     * @return
     */
    public static String getImageNameFromPath(String imageUrl)
    {
        try
        {
            if (!TextUtils.isEmpty(imageUrl) && imageUrl.contains("/"))
            {
                int lastIndex = imageUrl.lastIndexOf("/");
                String imageName = imageUrl.substring(lastIndex + 1, imageUrl.length());
                return imageName;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return imageUrl;
    }

    public static void compress(String path)
    {
        float hh = BaseApplication.getDeviceHeight();
        float ww = BaseApplication.getDeviceWidth();
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        opts.inJustDecodeBounds = false;
        int w = opts.outWidth;
        int h = opts.outHeight;
        int size = 0;
        if (w <= ww && h <= hh)
        {
            size = 1;
        }
        else
        {
            double scale = w >= h ? w / ww : h / hh;
            double log = Math.log(scale) / Math.log(2);
            double logCeil = Math.ceil(log);
            size = (int) Math.pow(2, logCeil);
        }
        opts.inSampleSize = size;
        Bitmap bitmap = BitmapFactory.decodeFile(path, opts);
        int degree = readPictureDegree(path);
        if (degree != 0 && bitmap != null)
        {
            bitmap = rotateBitmap(bitmap, degree);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try
        {
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
            System.out.println(baos.toByteArray().length);
            while (baos.toByteArray().length > 100 * 1024 && quality >= 0 && quality <= 100)
            {
                baos.reset();
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
                quality -= 20;
                System.out.println(baos.toByteArray().length);
            }

            baos.writeTo(new FileOutputStream(HEADIMG_PATH));
//            EventBus.getDefault().post(new BaseEvent(EventConstants.COMPRESS_SUCCESS.ordinal()));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                baos.flush();
                baos.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * API19以下获取图片路径的方法
     */
    public static String getFilePath_below19(ContentResolver contentResolver, Uri uri)
    {
        //这里开始的第二部分，获取图片的路径：低版本的是没问题的，但是sdk>19会获取不到
        String[] proj = {MediaStore.Images.Media.DATA};
        //好像是android多媒体数据库的封装接口，具体的看Android文档
        Cursor cursor = contentResolver.query(uri, proj, null, null, null);
        //获得用户选择的图片的索引值
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        System.out.println("***************" + column_index);
        //将光标移至开头 ，这个很重要，不小心很容易引起越界
        cursor.moveToFirst();
        //最后根据索引值获取图片路径   结果类似：/mnt/sdcard/DCIM/Camera/IMG_20151124_013332.jpg
        String path = cursor.getString(column_index);
        System.out.println("path:" + path);
        return path;
    }

    /**
     * APIlevel 19以上才有
     * 创建项目时，我们设置了最低版本API Level，比如我的是10，
     * 因此，AS检查我调用的API后，发现版本号不能向低版本兼容，
     * 比如我用的“DocumentsContract.isDocumentUri(context, uri)”是Level 19 以上才有的，
     * 自然超过了10，所以提示错误。
     * 添加    @TargetApi(Build.VERSION_CODES.KITKAT)即可。
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath_above19(final Context context, final Uri uri)
    {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri))
        {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri))
            {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type))
                {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri))
            {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri))
            {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type))
                {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                }
                else if ("video".equals(type))
                {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                }
                else if ("audio".equals(type))
                {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme()))
        {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme()))
        {
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs)
    {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try
        {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst())
            {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        }
        finally
        {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri)
    {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri)
    {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri)
    {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri)
    {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    /**
     * 保存裁剪之后的图片数据
     */
    public static void setPicToView(Context context, String url, final Handler mHandler)
    {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("fileName",
                        "upload.jpg",
                        RequestBody.create(MEDIA_TYPE_PNG, new File(HEADIMG_PATH)))
                .build();
        final Request request = new Request.Builder()
//                .header("Cookie", "isid=" + isid)
                .url(url)
                .header("Accept", "*/*")
                .post(requestBody).build();

        mOkHttpClient.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                if (mHandler != null)
                {
                    mHandler.sendEmptyMessage(MSG_WHAT_UPLOAD_FAILED);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                if (mHandler != null)
                {
                    try
                    {
                        Message message = mHandler.obtainMessage();
                        message.what = MSG_WHAT_UPLOAD_SUCCESS;
                        String str = response.body().string();
                        message.obj = str;
                        mHandler.sendMessage(message);
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                    finally
                    {
                        response.body().close();
                    }
                }
            }
        });

    }


    public static String getPhotoPath(Context context, Uri uri){

        String filePath = "";
        if (uri != null) {
            String scheme = uri.getScheme();
            if (TextUtils.equals("content", scheme)) {// android 4.4以上版本处理方式
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT
                        && DocumentsContract.isDocumentUri(context, uri)) {
                    String wholeID = DocumentsContract.getDocumentId(uri);
                    String id = wholeID.split(":")[1];
                    String[] column = { MediaStore.Images.Media.DATA };
                    String sel = MediaStore.Images.Media._ID + "=?";
                    Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            column, sel, new String[] { id }, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        int columnIndex = cursor.getColumnIndex(column[0]);
                        filePath = cursor.getString(columnIndex);
                        cursor.close();
                    }
                } else {// android 4.4以下版本处理方式
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        filePath = cursor.getString(columnIndex);
                        cursor.close();
                    }
                }
            } else if (TextUtils.equals("file", scheme)) {// 小米云相册处理方式
                filePath = uri.getPath();
            }

        }

        return filePath;

    }

}
