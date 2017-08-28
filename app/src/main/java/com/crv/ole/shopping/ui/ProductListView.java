package com.crv.ole.shopping.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by Fairy on 2017/8/7.
 * 解决ViewPager嵌套ListView只显示一行的问题
 */
public class ProductListView extends ListView {

    public ProductListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProductListView(Context context) {
        super(context);
    }

    public ProductListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        getParent().requestDisallowInterceptTouchEvent(false);
        return super.onTouchEvent(event);
    }
}
