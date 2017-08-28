package com.crv.ole.personalcenter.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.personalcenter.model.MessageData;
import com.crv.ole.personalcenter.model.MessageItemData;

import java.util.List;

/**
 * Created by crv on 2017/7/10.
 */

public class MsgListAdapter extends BaseAdapter {
    private Context context;
    private List<MessageItemData> dataList;

    public MsgListAdapter(Context context, List<MessageItemData> messageDataList) {
        this.context = context;
        this.dataList = messageDataList;
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
            v = LayoutInflater.from(context).inflate(R.layout.msg_list_item_layout, null);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) v.getTag();

        MessageItemData data = dataList.get(position);
        if (TextUtils.equals(data.getMessageType(), "orderShipping")) {
            viewHolder.ic.setImageResource(R.drawable.msg_list_wlzs_ic);
            viewHolder.name.setText("物流助手");
        } else if (TextUtils.equals(data.getMessageType(), "live")) {
            viewHolder.ic.setImageResource(R.drawable.msg_list_zbtx_ic);
            viewHolder.name.setText("直播提醒");
        } else if (TextUtils.equals(data.getMessageType(), "shopping")) {
            viewHolder.ic.setImageResource(R.drawable.msg_list_gwzl_ic);
            viewHolder.name.setText("购物助理");
        }

        viewHolder.title.setText(data.getContent());

        return v;
    }

    private class ViewHolder {
        public ViewHolder(View v) {
            ic = (ImageView) v.findViewById(R.id.msg_list_item_iv);
            title = (TextView) v.findViewById(R.id.msg_list_item_title);
            name = (TextView) v.findViewById(R.id.msg_list_item_name);

        }

        private ImageView ic;
        private TextView title, name;
    }
}
