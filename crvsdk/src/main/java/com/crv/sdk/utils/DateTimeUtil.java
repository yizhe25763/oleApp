package com.crv.sdk.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.text.format.Time;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

@SuppressLint("SimpleDateFormat")
public class DateTimeUtil {
    final static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取系统当前日期（格式2012-12-28）
     */
    public static String getSysDateYMD() {
        return getDate(new Date(System.currentTimeMillis()), "yyyy-MM-dd");
    }

    /**
     * 获取系统当前日期（格式2012-12）
     */
    public static String getSysDateYM() {
        return getDate(new Date(System.currentTimeMillis()), "yyyy-MM");
    }

    /**
     * 获取系统当前时间（格式13:58:00）
     */
    public static String getSysTimeHMS() {
        return getDate(new Date(System.currentTimeMillis()), "HH:mm:ss");
    }

    /**
     * 获取系统当前时间（格式2012-12-28 13:58:00）
     */
    public static String getSysTimeYMDHMS() {
        return getDate(new Date(System.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss");
    }


    public static String getSysTimeYMDHM() {
        return getDate(new Date(System.currentTimeMillis()), "yyyy-MM-dd HH:mm");
    }

    /**
     * 日期转获取字符串
     *
     * @param date   日期
     * @param format 格式
     *               ("yyyy-MM-dd hh:mm:ss")
     */
    public static String getDate(Date date, String format) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 格式时间，如果不是两位数的前面补0
     */
    public static String format(int x) {
        String s = "" + x;
        if (s.length() == 1) {
            s = "0" + s;
        }
        return s;
    }

    /**
     * 字符串转日期
     */
    public static Date StringToDate(String dateStr, String formatStr) {
        DateFormat dd = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = dd.parse(dateStr);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * 格式化日期
     * 去掉秒
     */
    public static String dateFormat(String dateStr) {
        if (TextUtils.isEmpty(dateStr)) {
            return "";
        }
        return getDate(StringToDate(dateStr, "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm");
    }

    /**
     * 获取上个月 2015-05
     *
     * @param dateStr 2015-06
     */
    public static String preMonth(String dateStr) {
        Date date = StringToDate(dateStr, "yyyy-MM");
        Date pre = addMonth(date, -1);
        return getDate(pre, "yyyy-MM");
    }

    /**
     * 获取下个月 2015-05
     *
     * @param dateStr 2015-04
     */
    public static String nextMonth(String dateStr) {
        Date date = StringToDate(dateStr, "yyyy-MM");
        Date pre = addMonth(date, 1);
        return getDate(pre, "yyyy-MM");
    }

    public static String TimestampToString(Timestamp dateStr) {
        if (dateStr != null) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateStr);
        } else {
            return null;
        }
    }


    public static String dateFormat2(String dateStr) {
        if (TextUtils.isEmpty(dateStr)) {
            return "";
        }
        return getDate(StringToDate(dateStr, "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd");
    }

    public static Timestamp StringToTimestamp(String dateStr) {
        return Timestamp.valueOf(dateStr);
    }

    public static String TimestampToString(Timestamp dateStr, String format) {
        return new SimpleDateFormat(format).format(dateStr);
    }

    public static Timestamp DoubleToTimespan(double time) {
        return longToTimespan((long) time);
    }

    public static Timestamp longToTimespan(long time) {
        return new Timestamp(time);
    }

    public static double TimespanToDouble(Timestamp time) {
        return time.getTime();
    }

    /**
     * 日期增加N天
     *
     * @param day  当前日期
     * @param days 增加天数
     */
    public static Date addDay(Date day, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(day); // 设置当前日期
        c.add(Calendar.DATE, days); // 日期加7
        return c.getTime();
    }

    public static String addDay(String strDate, int days) {
        Date date = StringToDate(strDate, "yyyy-MM-dd");
        Date newdate = addDay(date, days);
        return getDate(newdate, "yyyy-MM-dd");
    }

    /**
     * 日期增加N月
     *
     * @param day 当前日期
     */
    public static Date addMonth(Date day, int month) {
        Calendar c = Calendar.getInstance();
        c.setTime(day); // 设置当前日期
        c.add(Calendar.MONTH, month); // 日期加7
        return c.getTime();
    }

    /**
     * 日期增加N年
     *
     * @param day   当前日期
     * @param years 增加天数
     */
    public static Date addYear(Date day, int years) {
        Calendar c = Calendar.getInstance();
        c.setTime(day); // 设置当前日期
        c.add(Calendar.YEAR, years);
        return c.getTime();
    }

    public static Date addHour(Date day, int hour) {
        Calendar c = Calendar.getInstance();
        c.setTime(day); // 设置当前日期
        c.add(Calendar.HOUR, hour); // 时间增加
        return c.getTime();
    }

    public static Date addMinute(Date day, int minute) {
        Calendar c = Calendar.getInstance();
        c.setTime(day); // 设置当前日期
        c.add(Calendar.MINUTE, minute); // 时间增加
        return c.getTime();
    }

    public static Date addSecond(Date day, int second) {
        Calendar c = Calendar.getInstance();
        c.setTime(day); // 设置当前日期
        c.add(Calendar.SECOND, second); // 时间增加
        return c.getTime();
    }

    public static String formatSeconds(long second) {
        String h = "0";
        String d = "0";
        String s = "0";
        long temp = second % 3600;
        if (second > 3600) {
            h = String.valueOf(second / 3600);
            if (Integer.parseInt(h) < 10) {
                h = "0" + h;
            }
            if (temp != 0) {
                if (temp > 60) {
                    d = String.valueOf(temp / 60);
                    if (Integer.parseInt(d) < 10) {
                        d = "0" + d;
                    }
                    if (temp % 60 != 0) {
                        s = String.valueOf(temp % 60);
                        if (Integer.parseInt(s) < 10) {
                            s = "0" + s;
                        }
                    }
                } else {
                    s = String.valueOf(temp);
                    d = "00";
                    if (Integer.parseInt(s) < 10) {
                        s = "0" + s;
                    }
                }
            } else {
                d = "00";
                s = "00";
            }
        } else {
            h = "00";
            d = String.valueOf(second / 60);

            if (Integer.parseInt(d) > 0 && Integer.parseInt(d) < 10) {
                d = "0" + d;
            }

            if (Integer.parseInt(d) % 60 == 0) {

                h = String.valueOf((Integer.parseInt(d) / 60));
                if (Integer.parseInt(h) > 0 && Integer.parseInt(h) < 10) {
                    h = "0" + h;
                }
                d = "00";
            }
            s = "00";
            if (second % 60 != 0) {
                s = String.valueOf(second % 60);
                if (Integer.parseInt(s) > 0 && Integer.parseInt(s) < 10) {
                    s = "0" + s;
                }
            }
        }

        return h + ":" + d + ":" + s;
        /*
         * SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss",
		 * Locale.getDefault()); sdf.setTimeZone(TimeZone.getTimeZone("GMT+0"));
		 * return sdf.format(s * 1000);
		 */
        /*
         * Context context =
		 * HBApplication.getInstance().getApplicationContext(); int hours =
		 * (int) (s / 3600); int minutes = (int) ((s - hours * 3600) / 60); int
		 * seconds = (int) (s - hours * 3600 - minutes * 60); StringBuffer
		 * buffer = new StringBuffer(""); if (hours > 0) { buffer =
		 * buffer.append("").append(hours).append(context.getString(R.string
		 * .hour)); } if (minutes > 0) { buffer =
		 * buffer.append("").append(minutes).append(context.getString(R.
		 * string.min)); } if (seconds > 0) { buffer =
		 * buffer.append("").append(seconds).append(context.getString(R.
		 * string.sec)); } if (hours == 0 && minutes == 0 && seconds == 0) {
		 * buffer = buffer.append("--"); } return buffer.toString();
		 */
    }

    /**
     * 时间差，返回秒
     */
    public static long diffTime(String start, String end) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dt_start = formatter.parse(start);
            Date dt_end = formatter.parse(end);
            return (dt_end.getTime() - dt_start.getTime()) / 1000;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 日期差
     */
    public static long diffDate(String start, String end) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt_start = formatter.parse(start);
            Date dt_end = formatter.parse(end);

            return (dt_end.getTime() / 1000 - dt_start.getTime() / 1000) / (60 * 60 * 24);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 是否小于今天
     */
    public static boolean Lessthantoday(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt_start = formatter.parse(getSysDateYMD());
            Date dt_end = formatter.parse(date);

            if ((dt_end.getTime() / 1000 - dt_start.getTime() / 1000) / (60 * 60 * 24) < 0) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 是否超过预售期
     */
    public static boolean Morethanpredate(String date, int predate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt_start = formatter.parse(getSysDateYMD());
            Date dt_end = formatter.parse(date);

            if ((dt_end.getTime() / 1000 - dt_start.getTime() / 1000) / (60 * 60 * 24) > predate) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static long stringToLong(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dtdate = formatter.parse(date);
            return dtdate.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 获取两个日期之间的间隔天数
     */
    public static int getGapCount(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
    }

    /**
     * 日期大小比较
     */
    public static boolean after(String beginDate, String endDate) {
        Date b = DateTimeUtil.StringToDate(beginDate, "yyyy-MM-dd");
        Date e = DateTimeUtil.StringToDate(endDate, "yyyy-MM-dd");
        return b.after(e);
    }

    /**
     * 日期时间比较
     */
    public static boolean compareTo(String beginDate, String endDate) {
        try {
            Date b = DateTimeUtil.StringToDate(beginDate, "yyyy-MM-dd HH:mm");
            Date e = DateTimeUtil.StringToDate(endDate, "yyyy-MM-dd HH:mm");
            return b.after(e);
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * 日期比较
     */
    public static boolean compareDate(String beginDate, String endDate) {
        try {
            Date b = DateTimeUtil.StringToDate(beginDate, "yyyy-MM-dd");
            Date e = DateTimeUtil.StringToDate(endDate, "yyyy-MM-dd");
            return b.after(e);
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean equal(String beginDate, String endDate) {
        Date b = DateTimeUtil.StringToDate(beginDate, "yyyy-MM-dd");
        Date e = DateTimeUtil.StringToDate(endDate, "yyyy-MM-dd");
        return b.equals(e);
    }

    public static String getToday() {
        StringBuffer day = new StringBuffer();
        Time time = new Time();
        time.setToNow();
        return day.append(time.year).append((time.month + 1)).append(time.monthDay).toString();
    }

    /**
     * 获取上个月的今天
     */
    public static String getLastMonthToday() {
        StringBuffer stringBuffer = new StringBuffer();
        Time time = new Time();
        time.setToNow();
        String month = String.valueOf(time.month);
        String day = String.valueOf(time.monthDay);
        if (month.length() < 2) {
            month = "0" + month;
        }
        if (day.length() < 2) {
            day = "0" + day;
        }

        return stringBuffer.append(time.year).append("-").append(month).append("-").append(day).toString();
    }


    public static String getTomorrow() {
        StringBuffer stringBuffer = new StringBuffer();
        Time time = new Time();
        time.setToNow();
        String month = String.valueOf(time.month + 1);
        String day = String.valueOf(time.monthDay + 1);
        if (month.length() < 2) {
            month = "0" + month;
        }
        if (day.length() < 2) {
            day = "0" + day;
        }
        return stringBuffer.append(time.year).append(month).append(day).toString();
    }

    /**
     * + : 正确的时间 - : 过去的时间,错误的
     * hw 直接获取的10天以内的时间无需判断
     */
    public static long getDaysBetween(int[] compare) {

        Time t = new Time();
        t.set(compare[2], compare[1], compare[0]);
        long t1m = t.toMillis(false);

        Time now = new Time();
        now.setToNow();
        int y = now.year;
        int m = now.month;
        int d = now.monthDay;
        now.set(d, m, y);
        long tnm = now.toMillis(false);

        long days = (t1m - tnm) / (1000 * 60 * 60 * 24);
        return days;
    }

    /**
     * 获取星期
     */
    public static String getWeekStr(String strDate) {
        if (StringUtils.isNullOrEmpty(strDate)) {
            return "";
        }
        Calendar c = Calendar.getInstance();
        Date date = StringToDate(strDate, "yyyy-MM-dd");
        c.setTime(date);
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if ("1".equals(mWay)) {
            mWay = "日";
        } else if ("2".equals(mWay)) {
            mWay = "一";
        } else if ("3".equals(mWay)) {
            mWay = "二";
        } else if ("4".equals(mWay)) {
            mWay = "三";
        } else if ("5".equals(mWay)) {
            mWay = "四";
        } else if ("6".equals(mWay)) {
            mWay = "五";
        } else if ("7".equals(mWay)) {
            mWay = "六";
        }
        return "星期" + mWay;
    }

    public static String getShortWeekStr(String strDate) {
        if (StringUtils.isNullOrEmpty(strDate)) {
            return "";
        }
        Calendar c = Calendar.getInstance();
        Date date = StringToDate(strDate, "yyyy-MM-dd");
        c.setTime(date);
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if ("1".equals(mWay)) {
            mWay = "天";
        } else if ("2".equals(mWay)) {
            mWay = "一";
        } else if ("3".equals(mWay)) {
            mWay = "二";
        } else if ("4".equals(mWay)) {
            mWay = "三";
        } else if ("5".equals(mWay)) {
            mWay = "四";
        } else if ("6".equals(mWay)) {
            mWay = "五";
        } else if ("7".equals(mWay)) {
            mWay = "六";
        }
        return "周" + mWay;
    }

    /**
     * 某月第一天
     */
    public static String getFirstDay(String dateStr) {
        if (StringUtils.isNullOrEmpty(dateStr)) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = StringToDate(dateStr, "yyyy-MM");
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(date);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        return df.format(gcLast.getTime());
    }

    /**
     * 某月最后一天
     */
    public static String getLastDay(String dateStr) {
        if (StringUtils.isNullOrEmpty(dateStr)) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = StringToDate(dateStr, "yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1); // 加一个月
        calendar.set(Calendar.DATE, 1); // 设置为该月第一天
        calendar.add(Calendar.DATE, -1); // 再减一天即为上个月最后一天
        return df.format(calendar.getTime());
    }

    public static String[] format_time(String time) {
        if (StringUtils.isNullOrEmpty(time)) {
            return null;
        }
        String[] t = time.split(":");

        String[] times = new String[4];
        if (t[0].length() == 1) {
            times[0] = "0";
            times[1] = t[0];
        } else {
            times[0] = t[0].substring(0, 1);
            times[1] = t[0].substring(1, 2);
        }

        if (t[1].length() == 1) {
            times[2] = "0";
            times[3] = t[1];
        } else {
            times[2] = t[1].substring(0, 1);
            times[3] = t[1].substring(1, 2);
        }
        return times;
    }

    /**
     * 求二个日期字符串相差的天数
     *
     * @return int
     */
    public static int getBetweenDay(String date1, String date2) {
        if (TextUtils.isEmpty(date1) || TextUtils.isEmpty(date2))
            return 0;
        return date1.compareTo(date2);
    }

    /**
     * long转time string
     */
    public static String getDateToString(long time) {
        Date d = new Date(time);
        return sf.format(d);
    }

    private static SimpleDateFormat sf2;

    /**
     * long转time string
     */
    public static String getDateToString(long time, String type) {
        Date d = new Date();
        d.setTime(time);
        if (sf2 == null) {
            sf2 = new SimpleDateFormat(type);
            sf2.setTimeZone(TimeZone.getTimeZone("GMT+0:00"));
        }
        return sf2.format(d);
    }

    /**
     * 秒转化为天小时分秒字符串
     *
     * @param seconds
     * @return String
     */
    public static String formatSecond(long seconds) {
        String timeStr = seconds + "秒";
        if (seconds > 60) {
            long second = seconds % 60;
            long min = seconds / 60;
            timeStr = min + "分" + second + "秒";
            if (min > 60) {
                min = (seconds / 60) % 60;
                long hour = (seconds / 60) / 60;
                timeStr = hour + "小时" + min + "分" + second + "秒";
                if (hour > 24) {
                    hour = ((seconds / 60) / 60) % 24;
                    long day = (((seconds / 60) / 60) / 24);
                    timeStr = day + "天" + hour + "小时" + min + "分" + second + "秒";
                }
            }
        }
        return timeStr;
    }
}
