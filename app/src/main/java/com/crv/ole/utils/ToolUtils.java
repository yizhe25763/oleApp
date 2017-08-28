package com.crv.ole.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.crv.ole.R;
import com.crv.sdk.utils.PreferencesHelper;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @version V1.0
 * @Title: ToolUtils.java
 * @Description: 工具类
 */
public class ToolUtils {
    public static Boolean isLoginStatus(Context instance) {

        PreferencesHelper mPreferencesHelper = new PreferencesHelper(instance);
        Boolean isLogin = mPreferencesHelper.getBoolean(OleConstants.KEY.LOGIN_STATUS);
        return isLogin;
    }

    /**
     * 设置登录状态
     *
     * @param context 上下文
     * @param state   true: 登录 ,false: 未登录
     */
    public static void setLoginState(Context context, boolean state) {
        PreferencesHelper mPreferencesHelper = new PreferencesHelper(context);
        mPreferencesHelper.put(OleConstants.KEY.LOGIN_STATUS, state);
    }

    /**
     * 判断当前是否安装有微信客户端
     */
    public static boolean isWXAppInstalledAndSupported(Context context,
                                                       IWXAPI api) {
        boolean sIsWXAppInstalledAndSupported =
                api.isWXAppInstalled() && api.isWXAppSupportAPI();
        if (!sIsWXAppInstalledAndSupported) {
            Toast.makeText(context,
                    R.string.wx_app_installed_hint,
                    Toast.LENGTH_SHORT).show();
        }
        return sIsWXAppInstalledAndSupported;
    }

    /**
     * 隐藏软键盘
     *
     * @param activity 当前activity
     */
    public static void hideInput(Activity activity) {
        try {
            InputMethodManager input =
                    (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (activity.getCurrentFocus() != null) {
                input.hideSoftInputFromWindow(activity.getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏软键盘
     */
    public static void hideInput(View v) {
        InputMethodManager imm =
                (InputMethodManager) v.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);

        }

    }

    /***
     * 获取ImageUrl地址
     *
     * @param HTML
     * @return
     */
    private static ArrayList<String> getImageUrl(String HTML) {
        // 获取img标签正则
        String IMGURL_REG = "<img.*src=(.*?)[^>]*?>";
        Matcher matcher = Pattern.compile(IMGURL_REG).matcher(HTML);
        ArrayList<String> listImgUrl = new ArrayList<String>();
        while (matcher.find()) {
            listImgUrl.add(matcher.group());
        }
        return listImgUrl;
    }

    /***
     * 获取ImageSrc地址
     *
     * @param listImageUrl
     * @return
     */
    private static ArrayList<String> getImageSrc(List<String> listImageUrl) {
        //获取src路径的正则
        String IMGSRC_REG = "http:\"?(.*?)(\"|>|\\s+)";
        ArrayList<String> listImgSrc = new ArrayList<String>();
        for (String image : listImageUrl) {
            Matcher matcher = Pattern.compile(IMGSRC_REG).matcher(image);
            while (matcher.find()) {
                listImgSrc.add(matcher.group().substring(0,
                        matcher.group().length() - 1));
            }
        }
        return listImgSrc;
    }

    public static ArrayList<String> getImageUrlFromHtml(String html) {
        if (TextUtils.isEmpty(html)) {
            return null;
        }
        return getImageSrc(getImageUrl(html));
    }


    /**
     * 根据字符来获取key value的键值对
     */
    public static Map<String, String> getKeyValueByString(String str) {
        Map<String, String> map = new HashMap<String, String>();
        if (!TextUtils.isEmpty(str)) {
            String[] arr = str.split("&");
            if (arr != null) {
                for (int i = 0; i < arr.length; i++) {
                    String[] keyValues = arr[i].split("=");
                    if (keyValues != null) {
                        if (keyValues.length == 1) {
                            map.put(keyValues[0], "");
                        } else if (keyValues.length == 2) {
                            map.put(keyValues[0], keyValues[1]);
                        }
                    }
                }
            }
        }
        return map;
    }

    private static String getProductId(String linkTo) {
        String tag = "good=";
        if (linkTo.lastIndexOf(tag) > 0) {
            return linkTo.substring(linkTo.lastIndexOf(tag) + tag.length());
        }
        return "";
    }

    /**
     * 压缩bitmap
     * <p>
     * image
     * count  压成多少kb
     *
     * @return Bitmap
     */
    public static Bitmap compressImage(Bitmap image, int count) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > count) { // 循环判断如果压缩后图片是否大于1000kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 20;// 每次都减少20
        }
        ByteArrayInputStream isBm =
                new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 压缩bitmap,反回字节数组
     * <p>
     * image
     *
     * @return Bitmap
     */
    public static byte[] getByteFromBitmap(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 24) { // 循环判断如果压缩后图片是否大于1000kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 20;// 每次都减少20
        }
        return baos.toByteArray();
    }

    /**
     * 压缩图片
     *
     * @param bitmap  要压缩的图片
     * @param maxSize 压缩到多大
     */
    public static Bitmap imageZoom(Bitmap bitmap, double maxSize) {
        //图片允许最大空间   单位：KB
        //将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        //将字节换成KB
        double mid = b.length / 1024;
        //判断bitmap占用空间是否大于允许最大空间  如果大于则压缩 小于则不压缩
        if (mid > maxSize) {
            //获取bitmap大小 是允许最大大小的多少倍
            double i = mid / maxSize;
            //开始压缩  此处用到平方根 将宽带和高度压缩掉对应的平方根倍
            //（1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
            bitmap =
                    zoomImage(bitmap,
                            bitmap.getWidth() / Math.sqrt(i),
                            bitmap.getHeight() / Math.sqrt(i));
        }
        return bitmap;
    }

    /***
     * 图片的缩放方法
     *
     * @param bgimage
     *            ：源图片资源
     * @param newWidth
     *            ：缩放后宽度
     * @param newHeight
     *            ：缩放后高度
     * @return
     */
    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                                   double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap =
                Bitmap.createBitmap(bgimage,
                        0,
                        0,
                        (int) width,
                        (int) height,
                        matrix,
                        false);
        return bitmap;
    }

    /**
     * 时间转换，毫秒转为yyyy-MM-dd hh:mm:ss
     *
     * @return String
     */
    public static String getStringTime(String time) {
        String temp = "";
        if (!TextUtils.isEmpty(time)) {
            Date date = new Date(Long.parseLong(time));
            SimpleDateFormat formatter =
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            temp = formatter.format(date);
        }
        return temp;
    }

    /**
     * 时间转换，毫秒转为yyyy-MM-dd hh:mm:ss
     *
     * @return String
     */
    public static String getStringTime(String time, String format) {
        String temp = "";
        if (!TextUtils.isEmpty(time)) {
            Date date = new Date(Long.parseLong(time));
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            temp = formatter.format(date);
        }
        return temp;
    }

    /**
     * 获取当前价格的折扣
     *
     * @param oldPrice 原价
     * @param curPrice 当前价（会员价）
     */
    public static String getBoldPrice(String oldPrice, String curPrice) {
        String temp = "";
        if (!TextUtils.isEmpty(oldPrice) && !TextUtils.isEmpty(curPrice)) {
            try {
                double old = Double.parseDouble(oldPrice);
                double cur = Double.parseDouble(curPrice);
                double f = 10 * cur / old;
                BigDecimal b = new BigDecimal(f);
                double a =
                        b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
                if (a > 0 && a < 10) {
                    if (10 * a % 10 == 0) {
                        temp = "已" + (int) a + "折";
                    } else {
                        temp = "已" + a + "折";
                    }
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return temp;
    }

    /**
     * 改变一个字符串中某些字符的颜色
     *
     * @param text  字符串
     * @param color 颜色
     * @param start 起始位置
     * @param end   结束位置
     * @return SpannableStringBuilder
     */
    public static SpannableStringBuilder changeTextColor(String text,
                                                         int color, int start, int end) {
        SpannableStringBuilder textStyle = new SpannableStringBuilder(text);
        textStyle.setSpan(new ForegroundColorSpan(color),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return textStyle;
    }


    /**
     * 身份证验证开始 true表示正确证件号码
     */

    // wi =2(n-1)(mod 11);加权因子
    final static int[] wi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4,
            2, 1};

    // 校验码
    final static int[] vi = {1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2};

    private static int[] ai = new int[18];

    // 校验身份证的校验码
    public static boolean identityIsVerify(String idcard) {
        if (idcard.length() == 15) {
            idcard = uptoeighteen(idcard);
        }
        if (idcard.length() != 18) {
            return false;
        }
        String verify = idcard.substring(17, 18);
        return verify.equals(getVerify(idcard));
    }

    // 15位转18位
    public static String uptoeighteen(String fifteen) {
        StringBuffer eighteen = new StringBuffer(fifteen);
        eighteen = eighteen.insert(6, "19");
        return eighteen.toString();
    }

    // 计算最后一位校验值
    public static String getVerify(String eighteen) {
        int remain = 0;
        if (eighteen.length() == 18) {
            eighteen = eighteen.substring(0, 17);
        }
        if (eighteen.length() == 17) {
            int sum = 0;
            for (int i = 0; i < 17; i++) {
                String k = eighteen.substring(i, i + 1);
                ai[i] = Integer.valueOf(k);
            }
            for (int i = 0; i < 17; i++) {
                sum += wi[i] * ai[i];
            }
            remain = sum % 11;
        }
        return remain == 2 ? "X" : String.valueOf(vi[remain]);

    }

    /**
     * 将两张图片横向拼接画成一个图
     */
    public static Bitmap add2BitmapH(Bitmap first, Bitmap second, int distance,
                                     String text1, String text2, int textSize) {
        int width = 0;
        int height = 0;
        if (second != null) {
            width = first.getWidth() + second.getWidth() + distance;
            height = Math.max(first.getHeight(), second.getHeight());
        } else {
            width = first.getWidth() + distance;
            height = first.getHeight();
        }
        Bitmap result =
                Bitmap.createBitmap(width, height + distance, Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        Canvas canvas = new Canvas(result);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(first, 0, (height - first.getHeight()) / 2, paint);
        if (second != null) {
            canvas.drawBitmap(second,
                    first.getWidth() + distance,
                    (height - second.getHeight()) / 2,
                    paint);
        }
        paint.setColor(Color.RED);
        paint.setTextSize(textSize);
        Rect rect = new Rect();
        int strWidth = 0;
        if (!TextUtils.isEmpty(text1)) {
            paint.getTextBounds(text1, 0, text1.length(), rect);
            strWidth = rect.width();
            canvas.drawText(text1,
                    first.getWidth() - strWidth - distance,
                    height,
                    paint);
        }
        if (!TextUtils.isEmpty(text2)) {
            paint.getTextBounds(text2, 0, text2.length(), rect);
            strWidth = rect.width();
            canvas.drawText(text2, width - strWidth - distance, height, paint);
        }
        return result;
    }

    /**
     * 将三张图片横向拼接画成一个图
     */
    public static Bitmap add3BitmapH(Bitmap first, Bitmap second, Bitmap third,
                                     int distance) {
        int width =
                first.getWidth() + second.getWidth() + third.getWidth() + 2
                        * distance;
        int height = Math.max(first.getHeight(), second.getHeight());
        height = Math.max(height, third.getHeight());
        Bitmap result = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        Canvas canvas = new Canvas(result);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(first, 0, (height - first.getHeight()) / 2, paint);
        canvas.drawBitmap(second,
                first.getWidth() + distance,
                (height - second.getHeight()) / 2,
                paint);
        canvas.drawBitmap(third, first.getWidth() + second.getWidth() + 2
                * distance, (height - third.getHeight()) / 2, null);
        return result;
    }

    /**
     * 将两张图片竖向拼接画成一个图
     */
    public static Bitmap add2BitmapV(Bitmap first, Bitmap second) {
        int height = first.getHeight() + second.getHeight();
        int width = Math.max(first.getWidth(), second.getWidth());
        Bitmap result = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        Canvas canvas = new Canvas(result);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(first, 0, 0, paint);
        canvas.drawBitmap(second, 0, first.getHeight(), paint);
        return result;
    }

    public static Bitmap bigBitmap(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(2.0f, 2.0f); //长和宽放大缩小的比例
        Bitmap resizeBmp =
                Bitmap.createBitmap(bitmap,
                        0,
                        0,
                        bitmap.getWidth(),
                        bitmap.getHeight(),
                        matrix,
                        true);
        return resizeBmp;
    }

    /**
     * 测量文本 text 所占的长度
     */
    public static float getTextWidth(String text, float textSize) {
        Paint mTextPaint = new Paint();
        mTextPaint.setTextSize(textSize); // 指定字体大小
        //Typeface font = Typeface.create(Typeface.MONOSPACE,Typeface.NORMAL);
        //   mTextPaint.setTypeface(font);
        //mTextPaint.setFakeBoldText(true); // 粗体
        return mTextPaint.measureText(text);
    }

    /**
     * 限制输入两们小数
     */
    public static InputFilter getNumberDecimal(final int DECIMAL_DIGITS) {
        InputFilter lengthfilter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                // 删除等特殊字符，直接返回
                if ("".equals(source.toString())) {
                    return null;
                }
                String dValue = dest.toString();
                String[] splitArray = dValue.split("//.");
                if (splitArray.length > 1) {
                    String dotValue = splitArray[1];
                    int diff = dotValue.length() + 1 - DECIMAL_DIGITS;
                    if (diff > 0) {
                        return source.subSequence(start, end - diff);
                    }
                }
                return null;
            }
        };
        return lengthfilter;
    }

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

    public static int getCropMaxType(int deviceWidth) {
        if (deviceWidth == 1440)
            return 3;
        else if (deviceWidth == 1080)
            return 2;
        else if (deviceWidth == 720)
            return 1;
        else
            return 1;
    }

    public static String getRandomString(int length) {
        String string = "abcdefghijklmnopqrstuvwxyz";
        StringBuffer sb = new StringBuffer();
        int len = string.length();
        for (int i = 0; i < length; i++) {
            sb.append(string.charAt(getRandom(len - 1)));
        }
        return sb.toString();
    }

    private static int getRandom(int count) {
        return (int) Math.round(Math.random() * (count));
    }

    public static int getWordCount(String s) {
        if (s==null) return 0;
        int length = 0;
        for (int i = 0; i < s.length(); i++) {
            int ascii = Character.codePointAt(s, i);
            if (ascii >= 0 && ascii <= 255)
                length++;
            else
                length += 2;

        }
        return length;

    }


}
