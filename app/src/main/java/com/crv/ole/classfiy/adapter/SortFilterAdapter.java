package com.crv.ole.classfiy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.base.BaseItemTouchListener;

import java.util.List;

/**
 * 筛选条件Adapter
 * Created by zhangbo on 2017/8/7.
 */

public class SortFilterAdapter extends BaseAdapter {

    private Context context;

    private List<String> titles;

    private LayoutInflater inflater;

    private int type;

    private BaseItemTouchListener listener;

    public SortFilterAdapter(Context context, List<String> titles, int type) {
        this.context = context;
        this.titles = titles;
        this.type = type;
        inflater = LayoutInflater.from(context);
    }

    public void setBaseItemTouchListener(BaseItemTouchListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getCount() {
        return titles == null ? 0 : titles.size();
    }

    @Override
    public Object getItem(int position) {
        return titles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SortFilterHolder holder;
        if (convertView == null) {
            if (type == 0) {
                convertView = inflater.inflate(R.layout.item_sort_filter_left_layout, parent, false);
            }
            if (type == 1) {
                convertView = inflater.inflate(R.layout.item_sort_filter_right_layout, parent, false);
            }
            holder = new SortFilterHolder();
            holder.rl_base = (LinearLayout) convertView.findViewById(R.id.rl_base);
            holder.ll_item = (LinearLayout) convertView.findViewById(R.id.ll_item);
            holder.tx_title = (TextView) convertView.findViewById(R.id.tx_title);
            convertView.setTag(holder);
        } else {
            holder = (SortFilterHolder) convertView.getTag();
        }
        holder.tx_title.setText(titles.get(position));
        holder.rl_base.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(titles.get(position), position);
                }
            }
        });
        return convertView;
    }


    public static class SortFilterHolder {
        LinearLayout rl_base;
        LinearLayout ll_item;
        TextView tx_title;
    }
}
