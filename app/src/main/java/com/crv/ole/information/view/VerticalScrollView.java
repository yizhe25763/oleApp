package com.crv.ole.information.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.content.Context;
import android.widget.TextView;

import com.crv.ole.R;

/**
 * Created by fanhaoyi on 2017/7/18.
 */

public class VerticalScrollView extends ScrollView {

    private float xDistance, yDistance, xLast, yLast;


    //
    private ValueAnimator apperaAnim;
    private ValueAnimator hiddenAnim;
    private int downScrollY;
    private int moveScrollY;
    private boolean isHidding;
    private View contentView;

    //
    public VerticalScrollView(Context context) {
        super(context);
    }

    public VerticalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VerticalScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                xLast = ev.getX();
                yLast = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //通知他的父ViewPager现在进行的是本控件的操作，不要对我的操作进行干扰
                getParent().requestDisallowInterceptTouchEvent(true);

        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 是否直接滑动到底部
     */
    protected boolean isScrollDown()
    {
        return getHeight() + getScrollY() == contentView.getHeight();
    }

    /**
     * 是否直接滑到顶部
     */
    protected boolean isScrollUp()
    {
        return getScrollY() == 0;
    }
    private void startHiddenAnimation()
    {
        if (!hiddenAnim.isRunning() && !isHidding)
        {
            hiddenAnim.start();
        }
    }

    private void startApperaAnimation()
    {
        if (!apperaAnim.isRunning())
        {
            apperaAnim.start();
        }
    }
    public void setAnimationView(final View animationView)
    {
        /**
         * 创建动画
         */
        hiddenAnim = ValueAnimator.ofFloat(0, animationView.getHeight());
        hiddenAnim.setDuration(500);
        hiddenAnim.setTarget(animationView);
        hiddenAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                animationView.setTranslationY((Float) animation.getAnimatedValue());
            }
        });
        hiddenAnim.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                startApperaAnimation();
            }

            @Override
            public void onAnimationStart(Animator animation)
            {
                isHidding = true;
            }
        });
        apperaAnim = ValueAnimator.ofFloat(animationView.getHeight(), 0);
        apperaAnim.setDuration(500);
        apperaAnim.setTarget(animationView);
        apperaAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                animationView.setTranslationY((Float) animation.getAnimatedValue());
            }
        });

        apperaAnim.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                isHidding = false;
            }

            @Override
            public void onAnimationStart(Animator animation)
            {
            }
        });
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                downScrollY = getScrollY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveScrollY = getScrollY();
                if (moveScrollY != downScrollY)
                {
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
    @Override
    protected void onFinishInflate()
    {
        if (getChildCount() > 0)
        {
            contentView = getChildAt(0);
        }
    }
}
