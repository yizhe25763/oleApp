package com.crv.ole.information.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;

import com.crv.ole.utils.ToastUtil;

/**
 * Created by fanhaoyi on 2017/7/21.
 */

public class NewWebView extends WebView {

    private OnScrollChangeListener mOnScrollChangeListener;

    public NewWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    private boolean isHidding;
    private int moveScrollY;
    private int downScrollY;
    private ValueAnimator apperaAnim;//隐藏
    private ValueAnimator hiddenAnim;//显示


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downScrollY = getScrollY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveScrollY = getScrollY();
                if (moveScrollY != downScrollY) {
                    startHiddenAnimation();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                moveScrollY = 0;
                downScrollY = 0;
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void startHiddenAnimation() {//显示动画
        if (!hiddenAnim.isRunning() && !isHidding) {
            hiddenAnim.start();
        }
    }

    private void startApperaAnimation() {//隐藏动画
        if (!apperaAnim.isRunning()) {
            apperaAnim.start();
        }
    }

    public void setAnimationView(final View animationView) {

        /**
         * 创建动画
         */
        hiddenAnim = ValueAnimator.ofFloat(0, animationView.getHeight());
        hiddenAnim.setDuration(500);
        hiddenAnim.setTarget(animationView);
        hiddenAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animationView.setTranslationY((Float) animation.getAnimatedValue());
            }
        });
        hiddenAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                startApperaAnimation();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                isHidding = true;
            }
        });
        apperaAnim = ValueAnimator.ofFloat(animationView.getHeight(), 0);
        apperaAnim.setDuration(500);
        apperaAnim.setTarget(animationView);
        apperaAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animationView.setTranslationY((Float) animation.getAnimatedValue());
            }
        });

        apperaAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isHidding = false;
            }

            @Override
            public void onAnimationStart(Animator animation) {
            }
        });
    }



    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        // webview的高度
        float webcontent = getContentHeight() * getScale();
        // 当前webview的高度
        float webnow = getHeight() + getScrollY();
        if (Math.abs(webcontent - webnow) < 1) {
            //处于底端
            mOnScrollChangeListener.onPageEnd(l, t, oldl, oldt);
        } else if (getScrollY() == 0) {
            //处于顶端
            mOnScrollChangeListener.onPageTop(l, t, oldl, oldt);
        } else {
            mOnScrollChangeListener.onScrollChanged(l, t, oldl, oldt);
        }
    }


    public void setOnScrollChangeListener(OnScrollChangeListener listener) {
        this.mOnScrollChangeListener = listener;
    }

    public interface OnScrollChangeListener {

        void onPageEnd(int l, int t, int oldl, int oldt);

        void onPageTop(int l, int t, int oldl, int oldt);

        void onScrollChanged(int l, int t, int oldl, int oldt);


    }

}
