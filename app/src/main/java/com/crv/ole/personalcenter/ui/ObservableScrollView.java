package com.crv.ole.personalcenter.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.crv.ole.personalcenter.activity.VipCardActivity;

/**
 * Created by Fairy on 2017/7/11.
 * （会员卡中的tab悬浮需求，重写ScrollView）
 */

public class ObservableScrollView extends ScrollView {
    private VipCardActivity.ScrollChangedListener listener;

    public ObservableScrollView(Context context) {
        this(context, null);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.scrollViewStyle);
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt)
    {
        super.onScrollChanged(l,t,oldl,oldt);
        if(this.listener != null) {
            listener.scrollChanged(t, oldt);
        }
    }

    public void setListener(VipCardActivity.ScrollChangedListener listener){
        this.listener = listener;
    }


}
