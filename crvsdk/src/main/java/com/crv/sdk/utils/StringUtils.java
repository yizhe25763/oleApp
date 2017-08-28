package com.crv.sdk.utils;

import android.text.TextUtils;
import android.widget.EditText;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    /**
     * 验证是否为数字(小数或整数)，正则表达式匹配
     *
     * @param str
     * @return
     * @author 李俊峰
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("^[1-9]+(([0-9]*\\.{1}[0-9])|[0-9]*)+[0-9]*$|^0{1}\\.[0-9]*$");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    /**
     * 小数点后有大于0的值则保留，否则不保留
     *
     * @param num
     * @return
     */
    public static String doubleTrans(double num) {
        if (num % 1.0 == 0) {
            return String.valueOf((long) num);
        }
        return String.valueOf(num);
    }

    public static boolean isUrl(String str)

    {
        return (str.endsWith("jsp") || str.endsWith("html") || str.startsWith("http://"));

    }

    /**
     * 保留小数点后两位
     *
     * @param num
     * @return
     */

    public static String decimalFormat(double num) {
        DecimalFormat df = new DecimalFormat("###.00");
        String result = df.format(num);
        return result;
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static String mul(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return doubleTrans(b1.multiply(b2).doubleValue());
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static String sub(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return doubleTrans(b1.subtract(b2).doubleValue());
    }

    /**
     * 小数点后有大于0的值则保留，否则不保留
     *
     * @param num
     * @return
     */
    public static String doubleTrans(String num) {
        try {
            if (!isNullOrEmpty(num)) {
                return doubleTrans(Double.parseDouble(num));
            }
        } catch (Exception ex) {
            return num;
        }
        return num;
    }

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isNullOrEmpty(EditText str) {
        return str.getText().toString() == null || str.getText().toString().trim().length() == 0;
    }

    /**
     * 字符串的最小最大长度
     *
     * @param str
     * @return
     */
    public static boolean requireLength(String str, int minLenth, int maxLength) {
        return !(str.length() < minLenth || str.length() > maxLength);
    }

    /**
     * 以中文,字母,英文和数字组成的字符串
     *
     * @param str
     * @return true表示
     */
    public static boolean stringFormat_one(String str) {
        return str.matches("|(^[A-Za-z0-9\u4e00-\u9fa5]+$)");
    }

    /**
     * 判断是否以字母,数字或符号组成字符串
     *
     * @param str
     * @return
     */
    public static boolean stringFormat_two(String str) {
        return !Pattern.compile("[\u4e00-\u9fa5\\s]").matcher(str).find();
    }


    /**
     * 电话号码国家码（+86或0086）
     *
     * @param str
     * @return true正确，false错误
     */
    public static boolean stringFormat_coutrycode(String str) {
        return Pattern.compile("^\\+{0,1}[0-9]*$").matcher(str).find();
    }

    /**
     * 验证邮箱正在表达式
     *
     * @param str
     * @return true正确，false错误
     */
    public static boolean stringFormat_email(String str) {
        Pattern pattern = Pattern
                .compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        return pattern.matcher(str).find();
    }

    /**
     * 以中文,字母,英文和数字组成的字符串
     *
     * @param str
     * @return
     */
    public static boolean stringFormat_three(String str) {
        return str.matches("|(^[A-Za-z0-9]+$)");
    }

    /**
     * 以整数组成的字符串
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        return str.matches("|(^[0-9]+$)");
    }


    /**
     * 大于0的正整数
     *
     * @param str
     * @return
     */
    public static boolean isPositiveNumber(String str) {
        return str.matches("/^\\+?[1-9]\\d*$/");
    }

    /**
     * 验证密码
     *
     * @param str
     * @return
     */
    public static boolean isReasonablePwd(String str) {
        return str.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,10}$");
    }

    /**
     * 验证身份证号(15位 & 18位)
     *
     * @param str
     * @return
     */
    public static boolean isCardId(String str) {
        return str.matches("^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}[0-9Xx]$") ||
                str.matches("^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$");
    }


    /**
     * 电话号码长度为11位的数字
     *
     * @param s
     * @return
     */
    public static boolean isPhone(String s) {
        if (isNullOrEmpty(s)) {
            return false;
        }
        if (s.length() != 11) {
            return false;
        }
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(s.trim());
        return m.matches();

    }

    /**
     * 是否是手机号
     *
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile) {
        if (mobile == null) return false;
        Pattern p = Pattern.compile("^(0|86|17951)?(13[0-9]|15[012356789]|17[0-9]|18[0-9]|14[57])[0-9]{8}$");
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    /**
     * 手机号中间四位变成*
     *
     * @param mobile
     * @return
     */
    public static String replaceMobile(String mobile) {
        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 是否是车牌号
     *
     * @param plateNo
     * @return
     */
    public static boolean isPlateNo(String plateNo) {
        Pattern p = Pattern.compile("^[\\u4e00-\\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$");
        Matcher m = p.matcher(plateNo);
        return m.matches();
    }

    /**
     * 保留一位小数
     *
     * @param num
     * @return
     */
    public static String Keepadecimal(double num) {
        DecimalFormat df = new DecimalFormat("######0.0");
        return df.format(num);
    }


    /**
     * 判断是否为移动的号码
     *
     * @return
     */
    public static boolean isMobileByIMSI(String imsi) {
        return imsi.startsWith("46000") || imsi.startsWith("46002");
    }


    /**
     * judge the Email style
     *
     * @param str
     * @return
     */
    public static boolean isEmail(String str) {
        if (null == str) return false;
        boolean cf = isContainChr(str);
        if (cf) return false;
        Pattern pp = Pattern.compile("^[\\w|_]+[\\w|.]*@{1}[A-Za-z0-9_.]+\\.{1}\\w+");
        Matcher match1 = pp.matcher(str);
        boolean ff = match1.matches();
        pp = null;
        match1 = null;
        return ff;
    }

    /**
     * judge the Email style
     *
     * @param str
     * @return
     */
    public static boolean isAllNumber(String str) {
        if (null == str) return false;
        Pattern pp = Pattern.compile("^[0-9]+");
        Matcher match1 = pp.matcher(str);
        boolean ff = match1.matches();
        pp = null;
        match1 = null;
        return ff;
    }

    /**
     * judge the telephone style
     *
     * @param str
     * @return
     */
    public static boolean isTelephone(String str) {
        if (null == str) return false;
        Pattern pp = Pattern.compile("^\\d+$");
        Matcher match1 = pp.matcher(str);
        boolean ff = match1.matches();
        pp = null;
        match1 = null;
        return ff;
    }


    /**
     * 是否为中文
     *
     * @param c
     * @return
     */
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }


    /**
     * 获取字符串长度,两个英文字母为一个汉字
     *
     * @param s
     * @return
     */
    public static int lenChar(String s) {
        if (null == s || "".equals(s.trim()))
            return 0;
        int len = s.length();
        int chinese = 0;
        int engs = 0;
        for (int i = 0; i < len; i++) {
            boolean cf = isChinese(s.charAt(i));
            if (cf)
                chinese++;
            else {
                engs++;
            }
        }

        return chinese = chinese + engs / 2;
    }

    /**
     * 是否包含了汉字
     *
     * @param str
     * @return
     */
    public static boolean isContainChr(String str) {
        boolean cf = false;
        for (int i = 0; i < str.length(); i++) {
            cf = isChinese(str.charAt(i));
            if (cf) break;
        }
        return cf;
    }

    /**
     * 把arraylist转换成string
     *
     * @param lists
     * @return
     */
    public static String arrayListToString(List<? extends Object> lists) {
        return arrayListToString(lists, "\n");
    }

    /**
     * 把arraylist转换成string
     *
     * @param lists
     * @param splitChar
     * @return
     */
    public static String arrayListToString(List<? extends Object> lists, String splitChar) {
        StringBuffer buffer = new StringBuffer(64);
        if (lists != null && lists.size() > 0) {
            for (Object o : lists) {
                buffer.append(o.toString());
                buffer.append(splitChar);
            }
        }
        return buffer.toString();
    }

    /**
     * 把array转换成string
     *
     * @param lists
     * @return
     */
    public static String arrayToString(Object[] lists) {
        StringBuffer buffer = new StringBuffer(128);
        if (lists != null && lists.length > 0) {
            int i = 0;
            for (Object o : lists) {
                if (i == 0) {
                    i = 1;
                } else {
                    buffer.append(",");
                }
                buffer.append(o.toString());
            }
        }
        return buffer.toString();
    }

    /**
     * 把string转换成list
     *
     * @param text
     * @return
     */
    public static List<String> stringToList(String text) {
        return stringToList(text, ",");
    }

    /**
     * 把string转换成list
     *
     * @param text
     * @param splitChar 分隔字符串
     * @return
     */
    public static List<String> stringToList(String text, String splitChar) {
        if (TextUtils.isEmpty(text)) {
            return null;
        }
        return Arrays.asList(text.split(splitChar));
    }

    /**
     * 米转千米（带单位）
     *
     * @param meter
     * @return
     */
    public static String meter2Kilometer(double meter) {
        String result = (int) meter + "m";
        if (meter / 1000 < 1) {
            return result;
        }
        if (meter % 1000 == 0) {
            int kilo = (int) meter / 1000;
            result = kilo + "Km";
        } else {
            double kilo = getDouble(2, meter / 1000);
            result = kilo + "Km";
        }
        return result;
    }

    /**
     * 四舍五入获取小数点后一位的double
     *
     * @param doub 要四舍五入精简长度的double变量
     * @return double
     */
    public static double getDouble(int scale, double doub) {
        BigDecimal bd = new BigDecimal(doub);
        bd = bd.setScale(scale, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }

    /**
     * 米转千米
     *
     * @param meter
     * @return
     */
    public static String meterTransKilometer(double meter) {
        String result = (int) meter + "";
        if (meter / 1000 < 1) {
            return result;
        }
        if (meter % 1000 == 0) {
            int kilo = (int) meter / 1000;
            result = kilo + "";
        } else {
            double kilo = getDouble(2, meter / 1000);
            result = kilo + "";
        }
        return result;
    }


    /**
     * 格式化文件大小（返回带单位kb..）
     *
     * @param fileS
     * @return
     */
    public static String formetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 去除字符串的前后空格及回车
     *
     * @param str
     * @return
     */
    public static String getStringNoBlank(String str) {
        if (str != null && !"".equals(str)) {
            str.replaceAll("\n", "");
            Pattern p = Pattern.compile("(^\\s*)|(\\s*$)");
            Matcher m = p.matcher(str);
            String strNoBlank = m.replaceAll("");
            return strNoBlank;
        } else {
            return "";
        }
    }

    /**
     * string 转 int
     *
     * @param number
     * @return
     */
    public static int stringToInt(String number) {
        Integer integer = 0;
        try {
            integer = Integer.parseInt(number);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return integer;
    }

}
