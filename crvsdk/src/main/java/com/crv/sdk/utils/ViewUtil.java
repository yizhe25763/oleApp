package com.crv.sdk.utils;

import android.content.res.Resources;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 文本控件工具类
 * Created by Administrator on 2016-06-04.
 */
public class ViewUtil {
    /**
     * 设置部分文本字体颜色
     *
     * @param re
     * @param text
     * @param start
     * @param color
     * @return
     */
    public static SpannableStringBuilder setTextColor(Resources re, String text, int start, int color) {
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        if (!TextUtils.isEmpty(text) && start < text.length()) {
            style.setSpan(new ForegroundColorSpan(re.getColor(color)), start, text.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        return style;
    }


    /**
     * 根据listview item设置listview高度
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        totalHeight += 10;
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }



}
