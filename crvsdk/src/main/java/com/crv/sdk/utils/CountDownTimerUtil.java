package com.crv.sdk.utils;

/**
 * Created by wj_wsf on 2017/6/26 15:05.
 */

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

/**
 * //┏┓　　　┏┓
 * //┏┛┻━━━┛┻┓
 * //┃　　　　　　　┃
 * //┃　　　━　　　┃
 * //┃　┳┛　┗┳　┃
 * //┃　　　　　　　┃
 * //┃　　　┻　　　┃
 * //┃　　　　　　　┃
 * //┗━┓　　　┏━┛
 * //┃　　　┃  神兽保佑
 * //┃　　　┃  代码无BUG！
 * //┃　　　┗━━━┓
 * //┃　　　　　　　┣┓
 * //┃　　　　　　　┏┛
 * //┗┓┓┏━┳┓┏┛
 * // ┃┫┫　┃┫┫
 * // ┗┻┛　┗┻┛
 */

/**
 * 继承 CountDownTimer 防范
 * <p>
 * 重写 父类的方法 onTick() 、 onFinish()
 */
public class CountDownTimerUtil extends CountDownTimer {
    private TextView timeText;
    private String doneString;
    private String tag = "";

    private OnCountDownFinishListener onCountDownFinishListener;

    /**
     * @param millisInFuture    表示以毫秒为单位 倒计时的总数
     *                          <p>
     *                          例如 millisInFuture=1000 表示1秒
     * @param countDownInterval 表示 间隔 多少微秒 调用一次 onTick 方法
     *                          <p>
     *                          例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
     */
    public CountDownTimerUtil(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    public CountDownTimerUtil(long millisInFuture, long countDownInterval, TextView text) {
        super(millisInFuture, countDownInterval);
        this.timeText = text;
    }

    public CountDownTimerUtil(long millisInFuture, long countDownInterval, TextView text, String tag, String doneText) {
        super(millisInFuture, countDownInterval);
        this.timeText = text;
        this.doneString = doneText;
        this.tag = tag;

    }

    public CountDownTimerUtil(long millisInFuture, long countDownInterval, TextView text, String doneText) {
        super(millisInFuture, countDownInterval);
        this.timeText = text;
        this.doneString = doneText;
    }

    public void setOnCountDownFinishListener(OnCountDownFinishListener onCountDownFinishListener) {
        this.onCountDownFinishListener = onCountDownFinishListener;
    }

    @Override
    public void onFinish() {
        if (StringUtils.isNullOrEmpty(tag)) {

        } else {
            if (tag.equals("LOGIN")) {//登录注册页面调用
                timeText.setClickable(true);
            }
            timeText.setText(TextUtils.isEmpty(this.doneString) ? "00:00:00" : this.doneString);

        }
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if (onCountDownFinishListener != null)
            onCountDownFinishListener.onFinish();
        if (StringUtils.isNullOrEmpty(tag)) {
            timeText.setText("还有" + DateTimeUtil.formatSecond(millisUntilFinished/1000));
        } else {
            if (tag.equals("LOGIN")) {//登录注册页面调用
                timeText.setClickable(false);
                timeText.setText("重获验证码(" + String.valueOf(Math.abs(millisUntilFinished / 1000)) + ")");
            } else if (tag.equals("PRODUCTSALE")) {    //  详情促销活动倒计时
                timeText.setClickable(false);
                timeText.setText(doneString + " " + formatTime(millisUntilFinished));
            } else if (tag.equals("PreSalaFinish")) {//预售结束时间
                timeText.setText("距离结束:" + DateTimeUtil.getDateToString(millisUntilFinished, "HH:mm:ss"));
            } else {
                timeText.setText("仅剩" + DateTimeUtil.formatSecond(millisUntilFinished));
            }
        }
    }

    /**
     * 将毫秒数转化为 00:00:00格式
     *
     * @param mill
     * @return
     */
    private String formatTime(long mill) {
        long ss = 1000;
        long mm = ss * 60;
        long hh = mm * 60;
        long hour = mill / hh;
        long min = (mill - hour * hh) / mm;
        long second = (mill - hour * hh - min * mm) / ss;
        return hour + ":" + min + ":" + second;
    }


    public interface OnCountDownFinishListener {
        void onFinish();
    }
}
