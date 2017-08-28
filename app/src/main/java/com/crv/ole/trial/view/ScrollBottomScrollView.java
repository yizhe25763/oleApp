package com.crv.ole.trial.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by zhangbo on 2017/8/16.
 */

public class ScrollBottomScrollView extends ScrollView {

    private OnScrollBottomListener listener;
    private OnScrollNotInBottomListener onScrollNotInBottomListener;
    private int calCount;

    public interface OnScrollBottomListener {
        void scrollToBottom();
    }

    public interface OnScrollNotInBottomListener {
        void ScrollNotInBottom();
    }

    public void onScrollViewScrollToBottom(OnScrollBottomListener l) {
        listener = l;
    }

    public void onScrollNotInBottom(OnScrollNotInBottomListener onScrollNotInBottomListener) {
        this.onScrollNotInBottomListener = onScrollNotInBottomListener;
    }

    public void unRegisterOnScrollViewScrollToBottom() {
        listener = null;
    }

    public ScrollBottomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        View view = this.getChildAt(0);
        if (this.getHeight() + this.getScrollY() + 150 >= view.getHeight()) {
            calCount++;
            if (calCount == 1) {
                if (listener != null) {
                    listener.scrollToBottom();
                }
            }
        } else {
            calCount = 0;
            if (onScrollNotInBottomListener != null) {
                onScrollNotInBottomListener.ScrollNotInBottom();
            }
        }
    }
}
