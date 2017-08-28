package com.crv.ole.personalcenter.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.personalcenter.model.MessageItemData;
import com.crv.ole.personalcenter.model.OrderInfoReslt;
import com.crv.ole.utils.LoadImageUtil;
import com.crv.sdk.utils.StringUtils;

import java.util.List;

/**
 * Created by crv on 2017/7/10.
 */

public class OrderInfoListAdapter extends BaseAdapter {
    private Context context;
    private List<OrderInfoReslt.RETURNDATABean.ItemsBean> dataList;

    public OrderInfoListAdapter(Context context, List<OrderInfoReslt.RETURNDATABean.ItemsBean> messageDataList) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.order_list, null);
            viewHolder = new ViewHolder();
            viewHolder.ic = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.title = (TextView) convertView.findViewById(R.id.name);
            viewHolder.prices = (TextView) convertView.findViewById(R.id.price);
            viewHolder.number = (TextView) convertView.findViewById(R.id.order_number);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        OrderInfoReslt.RETURNDATABean.ItemsBean bean = dataList.get(position);
        viewHolder.number.setText(bean.getAmount()+"");

        if (!StringUtils.isNullOrEmpty(bean.getProductName())) {
            viewHolder.title.setText(bean.getProductName());

        }
        if (!StringUtils.isNullOrEmpty(bean.getLogoUrl())) {
            LoadImageUtil.getInstance().loadImage(viewHolder.ic, bean.getLogoUrl());

        }
        if (!StringUtils.isNullOrEmpty(bean.getFTotalPrice())) {
            viewHolder.prices.setText("￥" + bean.getFTotalPrice());

        }
        return convertView;
    }

    private class ViewHolder {
        private ImageView ic;
        private TextView title;
        private TextView prices;
        private TextView number;

    }
}
