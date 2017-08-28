package com.crv.ole.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crv.ole.R;

/**
 * Created by lihongshi on 2017/8/23.
 */

public class TwoTextView extends LinearLayout {

    private TextView leftTextView;
    private TextView rightTextView;

    private String leftTitle;
    private int leftTitleColor;
    private float leftTitleSize;

    private String rightTitle;
    private int rightTitleColor;
    private float rightTitleSize;


    public TwoTextView(Context context) {
        super(context);
    }

    public TwoTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initTypedArray(context, attrs);
        initView(context);
    }

    public TwoTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTypedArray(context, attrs);
        initView(context);
    }


    private void initTypedArray(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TwoTextView);
        leftTitle = typedArray.getString(R.styleable.TwoTextView_leftTitle);
        leftTitleColor = typedArray.getColor(R.styleable.TwoTextView_leftTitleColor, context.getResources().getColor(R.color.c_999999));
        leftTitleSize = typedArray.getDimension(R.styleable.TwoTextView_leftTitleSize, 14);
        rightTitle = typedArray.getString(R.styleable.TwoTextView_rightTitle);
        rightTitleColor = typedArray.getColor(R.styleable.TwoTextView_rightTitleColor, context.getResources().getColor(R.color.c_666666));
        rightTitleSize = typedArray.getDimension(R.styleable.TwoTextView_rightTitleSize, 14);
        //获取资源后要及时回收
        typedArray.recycle();
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.two_text_view, this, true);
        leftTextView = (TextView) findViewById(R.id.leftTextView);
        rightTextView = (TextView) findViewById(R.id.rightTextView);
        setLeftTitle(leftTitle);
        setLeftTitleSize(leftTitleSize);
        setLeftTitleColor(leftTitleColor);
        setRightTitle(rightTitle);
        setRightTitleSize(rightTitleSize);
        setRightTitleColor(rightTitleColor);
    }

    public void setLeftTitle(String title) {
        this.leftTitle = title;
        leftTextView.setText(title);
    }

    public void setLeftTitleSize(float size) {
        this.leftTitleSize = size;
        leftTextView.setTextSize(size);
    }

    public void setLeftTitleColor(int color) {
        this.leftTitleColor = color;
        leftTextView.setTextColor(color);
    }


    public void setRightTitle(String title) {
        this.rightTitle = title;
        rightTextView.setText(title);
    }

    public void setRightTitleSize(float size) {
        this.rightTitleSize = size;
        rightTextView.setTextSize(size);
    }

    public void setRightTitleColor(int color) {
        this.rightTitleColor = color;
        rightTextView.setTextColor(color);
    }

}
