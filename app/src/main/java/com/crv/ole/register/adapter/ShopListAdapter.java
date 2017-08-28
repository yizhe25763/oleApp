package com.crv.ole.register.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.register.model.ShopInfoResultBean;

import java.util.List;

/**
 * Created by Fairy on 2017/7/25.
 * 门店适配
 */

public class ShopListAdapter extends BaseAdapter {
    private Context context;
    private List<ShopInfoResultBean.RETURNDATABean> dataList;

    public ShopListAdapter(Context context, List<ShopInfoResultBean.RETURNDATABean> messageDataList) {
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
            v = LayoutInflater.from(context).inflate(R.layout.shop_item_layout, null);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }
        viewHolder.shopName.setText(dataList.get(position).getShopname());
        return v;
    }

    private class ViewHolder {
        private TextView shopName;
        public ViewHolder(View v) {
            shopName = (TextView) v.findViewById(R.id.shopName);
        }

    }
}
