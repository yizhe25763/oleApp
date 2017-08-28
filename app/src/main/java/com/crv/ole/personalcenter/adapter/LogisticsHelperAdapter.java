package com.crv.ole.personalcenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.personalcenter.model.LogisticsListData;
import com.crv.ole.personalcenter.model.MessageItemData;

import java.util.List;

/**
 * Created by crv on 2017/7/10.
 */

public class LogisticsHelperAdapter extends BaseAdapter {
    private Context context;
    private List<MessageItemData> dataList;

    public LogisticsHelperAdapter(Context context, List<MessageItemData> logisticsDataList) {
        this.context = context;
        this.dataList = logisticsDataList;
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList == null ? null : dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        ViewHolder viewHolder;
        if (v == null) {
            v = LayoutInflater.from(context).inflate(R.layout.logistics_list_item_layout, null);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) v.getTag();

        viewHolder.time.setText(dataList.get(position).getSendTime().substring(dataList.get(position).getSendTime().indexOf(" ") + 1));
        viewHolder.date.setText(dataList.get(position).getSendTime().substring(0, dataList.get(position).getSendTime().indexOf(" ")));
        viewHolder.title.setText(dataList.get(position).getContent());

        return v;
    }

    private class ViewHolder {
        public ViewHolder(View v) {
            title = (TextView) v.findViewById(R.id.logistics_list_item_title);
            time = (TextView) v.findViewById(R.id.logistics_list_item_time);
            date = (TextView) v.findViewById(R.id.logistics_list_item_date);
        }

        private TextView title, time, date;
    }
}
