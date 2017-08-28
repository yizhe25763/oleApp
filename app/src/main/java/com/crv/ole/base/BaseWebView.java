package com.crv.ole.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.crv.ole.utils.ToastUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by yanghongjun on 17/8/9.
 */

public class BaseWebView extends WebView {

    private OnScrollChangeListener mOnScrollChangeListener;

    boolean isTop = false;
    boolean isBottom = false;
    float startX;
    float startY;

    float currentX;
    float currentY;


    private boolean isHidding;
    private int moveScrollY;
    private int downScrollY;
    private ValueAnimator apperaAnim;//隐藏
    private ValueAnimator hiddenAnim;//显示


    public BaseWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.getSettings().setJavaScriptEnabled(true);
        this.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        //跨域设置
        try {
            if (Build.VERSION.SDK_INT >= 16) {
                Class<?> clazz = this.getSettings().getClass();
                Method method = clazz.getMethod("setAllowUniversalAccessFromFileURLs", boolean.class);
                if (method != null) {
                    method.invoke(this.getSettings(), true);
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        this.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                ToastUtil.showToast("网页加载出错");
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
        if (Math.abs(webcontent - webnow) <= 0) {
            //处于底端
            isBottom = true;
        } else if (getScrollY() == 0) {
            //处于顶端
            isTop = true;
        } else {
            if (mOnScrollChangeListener!=null){
                mOnScrollChangeListener.onScrollChanged(l, t, oldl, oldt);
            }

        }
    }

    public void setOnScrollChangeListener(OnScrollChangeListener listener) {
        this.mOnScrollChangeListener = listener;
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
    public boolean onTouchEvent(MotionEvent event) {

        if ((event.getAction() == MotionEvent.ACTION_DOWN)) {
             startY = event.getY();
        } else if((event.getAction() == MotionEvent.ACTION_UP )){
            currentY  =event.getY();
            if(Math.abs(currentY-startY)>60){
                if(currentY-startY>0){  //  下滑
                    if(isTop){
                        isTop = false;
                        if (mOnScrollChangeListener!=null){
                            mOnScrollChangeListener.onPageTop(0, 0, 0, 0);
                        }

                    }
                }else {
                    if(isBottom){
                        isBottom = false;
                        if (mOnScrollChangeListener!=null){
                            mOnScrollChangeListener.onPageEnd(0, 0, 0, 0);
                        }

                    }
                }
            }
            startY = event.getY();
        } else if(event.getAction()== MotionEvent.ACTION_MOVE){
            currentY  =event.getY();
        }

        return super.onTouchEvent(event);
    }

    public interface OnScrollChangeListener {

        void onPageEnd(int l, int t, int oldl, int oldt);

        void onPageTop(int l, int t, int oldl, int oldt);

        void onScrollChanged(int l, int t, int oldl, int oldt);

    }
}
