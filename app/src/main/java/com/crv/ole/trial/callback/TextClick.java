package com.crv.ole.trial.callback;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.crv.ole.BaseApplication;
import com.crv.ole.R;

/**
 * 文字点击事件
 * Created by zhangbo on 2017/8/15.
 */

public class TextClick extends ClickableSpan {

    private OnTextClickListener onTextClickListener;

    public TextClick(OnTextClickListener onTextClickListener) {
        this.onTextClickListener = onTextClickListener;
    }

    @Override
    public void onClick(View widget) {
        if (onTextClickListener != null) {
            onTextClickListener.onTextClickListen();
        }
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(Color.parseColor("#d9be64"));
        ds.setUnderlineText(true);
    }
}
