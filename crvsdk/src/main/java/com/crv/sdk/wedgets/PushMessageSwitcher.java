package com.crv.sdk.wedgets;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

/**
 * @author wsf
 * @version V1.0.0
 * @Title: PushMessageSwitcher.java
 * @Description: 消息滚动显示
 */
public class PushMessageSwitcher extends TextSwitcher implements ViewFactory {
    private final static int DEFAUL_INTERVAL = 5000;
    private final static int WHAT = 1000;

    private ArrayList<String> mMessages;
    private int mInterval = DEFAUL_INTERVAL;
    private int mCurrentShow = 0;
    private int mDefaultTextColor;
    private float textSize = 14;
    private LayoutInflater mInflater;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (mMessages != null && mMessages.size() > 0) {
                setText(mMessages.get((mCurrentShow++) % mMessages.size()));
            }
            mHandler.sendEmptyMessageDelayed(WHAT, mInterval);
        }
    };

    public PushMessageSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
        mMessages = new ArrayList<String>();
        setFactory(this);
        mInflater = LayoutInflater.from(context);
    }

    public PushMessageSwitcher(Context context) {
        this(context, null);
    }

    public void forcePause() {
        mHandler.removeMessages(WHAT);
    }

    public void forceStart() {
        mHandler.sendMessageAtFrontOfQueue(mHandler.obtainMessage(WHAT));
    }

    @Override
    public View makeView() {
        TextView textView = new TextView(getContext());
        textView.setTextSize(textSize);
        android.view.ViewGroup.LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        textView.setLayoutParams(lp);
        // textView.setBackgroundColor(getResources().getColor(R.color.blue));
        textView.setPadding(0, 2, 0, 2);
        mDefaultTextColor = Color.parseColor("#313131");
//        mDefaultTextColor = Color.parseColor("#FFFFFF");
        textView.setTextColor(mDefaultTextColor);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setSingleLine();
        textView.setEllipsize(TextUtils.TruncateAt.END);
     /*   Typeface font = Typeface.create(Typeface.MONOSPACE,Typeface.NORMAL);
        textView.setTypeface(font);*/
        return textView;
    }

    public float getSwitcherTextSize() {
        return textSize;
    }

    public void setTextColor(int color) {
        setTextColor(color);
    }

    /**
     * add message
     *
     * @param message
     * @return void
     * @throws
     */
    public void addMessage(String message) {
        mMessages.add(message);
    }

    /**
     * add messages
     *
     * @param messages
     * @return void
     * @throws
     */
    public void addMessage(ArrayList<String> messages) {
        mMessages.addAll(messages);
    }

    /**
     * clear
     *
     * @return void
     * @throws
     */
    public void clear() {
        if (mMessages != null) {
            mMessages.clear();
        }
        removeAllViews();
    }

    /**
     * clearData
     *
     * @return void
     * @throws
     */
    public void clearData() {
        if (mMessages != null) {
            mMessages.clear();
        }
    }

    public int getCurrentShow() {
        return (mCurrentShow - 1) % mMessages.size();
    }
}

