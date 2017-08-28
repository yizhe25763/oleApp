package com.crv.ole.shopping.image;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


/**
 * 
* @author pjy
* @Version 1.0
* <p>	
	自定义ViewPager，可以设置不可滑动
  </p>
 */
public class CustomViewPager extends ViewPager {
    private boolean isCanScroll = false;  
  
    public CustomViewPager(Context context) {  
        super(context);  
    }  
  
    public CustomViewPager(Context context, AttributeSet attrs) {  
        super(context, attrs);  
    }  
  
    public void setScanScroll(boolean isCanScroll) {  
        this.isCanScroll = isCanScroll;  
    }  
  
    @Override  
    public void scrollTo(int x, int y) {  
            super.scrollTo(x, y);  
    }  
  
    /*@Override  
    public boolean onTouchEvent(MotionEvent arg0) {  
        if(isCanScroll) {
        	return super.onTouchEvent(arg0); 
        } else {
        	return false;
        }
    }  */
    
    @Override  
    public boolean onInterceptTouchEvent(MotionEvent event) {
        try
        {
            if (isCanScroll) {
                return super.onInterceptTouchEvent(event);
            } else {
                return false;
            }
        }
        catch (Exception e)
        {
//            EwjLogUtils.e (Log.getStackTraceString (e));
            return false;
        }
    }

    @Override
    public boolean onTouchEvent (MotionEvent ev)
    {
        try
        {
            if (isCanScroll) {
                return super.onTouchEvent (ev);
            } else {
                return false;
            }
        }
        catch (Exception e)
        {
//            EwjLogUtils.e (Log.getStackTraceString (e));
            return false;
        }
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {  
        super.setCurrentItem(item, smoothScroll);
    }  
  
    @Override  
    public void setCurrentItem(int item) {  
        super.setCurrentItem(item);
    }  
}  
