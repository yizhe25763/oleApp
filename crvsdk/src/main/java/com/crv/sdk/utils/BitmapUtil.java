package com.crv.sdk.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * bitmap处理
 */
public class BitmapUtil {
    private Context context;

    public BitmapUtil(Context context) {
        this.context = context;
    }

    /**
     * 创建本地图
     */
    public static void createLocalFile(final Bitmap bm, final String iconPath) {

        if (bm == null) {
        }
        FileOutputStream m_fileOutPutStream = null;
        //        File cache = new File(getSavePath());
        //        if (!cache.exists()) {
        //            cache.mkdirs();
        //        }
        try {
            m_fileOutPutStream = new FileOutputStream(iconPath);// 写入的文件路径
            Log.i("开始写图片", "----------->");
            bm.compress(CompressFormat.JPEG, 100, m_fileOutPutStream);
            Log.i("结束图片", "----------->");
            m_fileOutPutStream.flush();
            m_fileOutPutStream.close();
        } catch (Exception e) {

        }
    }

    /**
     * 得到放缩后的图片
     */
    public synchronized Bitmap getBitmap(String path, int newWidth,
                                         int newHeight) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true; // 只得到图片边框信息。
        BitmapFactory.decodeFile(path, opts);
        if (opts.outHeight > newHeight || opts.outWidth > newWidth) {
            int insamplesizeH = opts.inSampleSize = opts.outHeight / newHeight; // 压缩比的设置高压缩
            int insamplesizeW = opts.inSampleSize = opts.outWidth / newWidth; // 压缩比的设置宽压缩
            opts.inSampleSize = opts.outHeight / newHeight; // 压缩比的设置高压缩

            if (insamplesizeW > insamplesizeH) {
                opts.inSampleSize = opts.outWidth / newWidth; // 压缩比的设置宽压缩
            }
        } else {
            opts.inSampleSize = opts.outHeight / newHeight; // 压缩比的设置 高压缩 溢出问题
        }
        // 这里一定要将其设置回false，因为之前我们将其设置成了true
        opts.inJustDecodeBounds = false;
        try {
            opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
            opts.inPurgeable = true;
            opts.inInputShareable = true;
            Bitmap bmp = BitmapFactory.decodeFile(path, opts);
            return bmp;
        } catch (OutOfMemoryError err) {

        }
        return null;
    }

    /**
     * 选择图像
     */
    public Bitmap creatBitmap(Bitmap bitmapImage, int w, int h, Matrix mx) {

        double s = 0.999999; // 默认按图片正常大小显示
        w = bitmapImage.getWidth();
        h = bitmapImage.getHeight();
        double temp1 = 0;// 图片压缩比
        double temp2 = 0;// 图片压缩比
        if (w > 1280) {
            temp1 = 1280.0 / w;
        }
        if (h > 752) {
            temp2 = 752.0 / h;
        }
        if (temp1 > 0 && temp2 > 0) {
            if (temp1 > temp2) {
                s = temp2;
            } else {
                s = temp1;
            }
        } else if (temp1 > 0 && temp2 == 0) {
            s = temp1;
        } else if (temp2 > 0 && temp1 == 0) {
            s = temp2;
        }
        Log.v("ViewImage", "---------------->>>s = " + s);
        float sw = 1, sh = 1;
        sw = (float) (sw * s);
        sh = (float) (sh * s);
        mx = new Matrix();
        mx.postScale(sw, sh);
        Bitmap resizeBmp = Bitmap.createBitmap(bitmapImage, 0, 0, w, h, mx, true);
        return resizeBmp;
    }


    /**
     * 获取保存图片的目录
     */
    public String getSavePath() {
        String savePath;
        if (SdCardUtils.isHasSdcard()) {
            savePath = Environment.getExternalStorageDirectory().getPath() + "/ticketImg/";
        } else {
            savePath = context.getFilesDir().toString() + "/ticketImg/";
        }
        return savePath;
    }

    /**
     * 合成图片
     */
    public static Bitmap combineBitmap(Bitmap background, Bitmap foreground) {
        if (background == null) {
            return null;
        }
        int bgWidth = background.getWidth();
        int bgHeight = background.getHeight();
        int fgWidth = foreground.getWidth();
        int fgHeight = foreground.getHeight();
        Bitmap newmap = Bitmap
                .createBitmap(bgWidth, bgHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newmap);
        canvas.drawBitmap(background, 0, 0, null);
        canvas.drawBitmap(foreground, (bgWidth - fgWidth) / 2,
                (bgHeight - fgHeight) / 2, null);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return newmap;
    }

    /**
     * 将bitmap保存为PNG图片
     */
    public static void saveBitmap(Bitmap mBitmap, String path, String name)
            throws IOException {
        File filedir = new File(path);
        if (!filedir.exists()) {
            filedir.mkdir();
        }

        File file = new File(path + name);
        file.createNewFile();
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据角度旋转图片
     */
    public static Bitmap matrixBitmap(Bitmap paramBitmap, float paramFloat) {
        if ((paramFloat < 1.0F) || (paramFloat > 359.0F)) {
            return paramBitmap;
        }

        Matrix matrix = new Matrix();
        matrix.setRotate(paramFloat);

        return Bitmap.createBitmap(paramBitmap, 0, 0, paramBitmap.getWidth(), paramBitmap.getHeight(), matrix, true);
    }

    /**
     * 选择图片并返回Drawable对象
     */
    @SuppressWarnings("deprecation")
    public static Drawable getDrawableByMatrix(Bitmap paramBitmap, float paramFloat) {
        return new BitmapDrawable(matrixBitmap(paramBitmap, paramFloat));
    }

    /**
     * bitmap转字节
     */
    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        try {
            imageZoom(bmp, 50);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            bmp.compress(CompressFormat.PNG, 100, output);
            if (needRecycle) {
                bmp.recycle();
            }
            byte[] result = output.toByteArray();

            output.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 图片压缩方法：（使用compress的方法）
     *
     * @param bitmap  要压缩的图片
     * @param maxSize 压缩后的大小，单位kb
     * @explain 如果bitmap本身的大小小于maxSize，则不作处理
     */
    public static void imageZoom(Bitmap bitmap, double maxSize) {
        // 将bitmap放至数组中，意在获得bitmap的大小（与实际读取的原文件要大）
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 格式、质量、输出流
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        // 将字节换成KB
        double mid = b.length / 1024;
        // 获取bitmap大小 是允许最大大小的多少倍
        double i = mid / maxSize;
        // 判断bitmap占用空间是否大于允许最大空间 如果大于则压缩 小于则不压缩
        if (i > 1) {
            // 缩放图片 此处用到平方根 将宽带和高度压缩掉对应的平方根倍
            // （保持宽高不变，缩放后也达到了最大占用空间的大小）
            scale(bitmap, bitmap.getWidth() / Math.sqrt(i), bitmap.getHeight() / Math.sqrt(i));
        }
    }

    /***
     * 图片的缩放方法
     *
     * @param src       ：源图片资源
     * @param newWidth  ：缩放后宽度
     * @param newHeight ：缩放后高度
     */
    public static Bitmap scale(Bitmap src, double newWidth, double newHeight) {
        // 记录src的宽高
        float width = src.getWidth();
        float height = src.getHeight();
        // 创建一个matrix容器
        Matrix matrix = new Matrix();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 开始缩放
        matrix.postScale(scaleWidth, scaleHeight);
        // 创建缩放后的图片
        return Bitmap.createBitmap(src, 0, 0, (int) width, (int) height, matrix, true);
    }

    /**
     * 以最省内存的方式读取图片
     */
    public static Bitmap readBitmap(final String path) {
        try {
            FileInputStream stream = new FileInputStream(new File(path + "test.jpg"));
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inSampleSize = 8;
            opts.inPurgeable = true;
            opts.inInputShareable = true;
            Bitmap bitmap = BitmapFactory.decodeStream(stream, null, opts);
            return bitmap;
        } catch (OutOfMemoryError e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据一个网络连接(String)获取bitmap图像
     */
    public static Bitmap getbitmap(String imageUri) {
        // 显示网络上的图片
        Bitmap bitmap = null;
        try {
            URL myFileUrl = new URL(imageUri);
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            bitmap = null;
        } catch (IOException e) {
            e.printStackTrace();
            bitmap = null;
        }
        return bitmap;
    }

    //view 转bitmap
    public static Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }
}
