package com.crv.ole.pay.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.pay.model.OrderConfirmAllCardsData;

import java.util.ArrayList;
import java.util.List;

/**
 * 作用描述：使用优惠卷适配器
 * 创建者： wj_wsf
 * 创建时间： 2017/7/12 11:44.
 */
public class ChooseDiscountCouponListAdapter extends BaseAdapter {
    private List<OrderConfirmAllCardsData> dataList;

    public ChooseDiscountCouponListAdapter(List<OrderConfirmAllCardsData> messageDataList) {
        this.dataList = messageDataList == null ? new ArrayList<>() : messageDataList;
    }

    public void setAllItem(List list) {
        dataList.clear();
        dataList.addAll(list);
        notifyDataSetChanged();
    }

    public void addAllItem(List list) {
        dataList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        //  return 4;
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View v, ViewGroup parent) {
        ViewHolder viewHolder;
        if (v == null) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.discount_coupon_list_item_layout, null);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }

        viewHolder.amount.setText(dataList.get(position).getFaceValue()+"");
        viewHolder.desc.setText(dataList.get(position).getName());
        viewHolder.title.setText(dataList.get(position).getRuleRemarkDes());
        viewHolder.time.setText(dataList.get(position).getEffectedEnd());

        return v;
    }

    private static class ViewHolder {
        private TextView url, time, desc, amount, title, use;

        public ViewHolder(View v) {
            amount = (TextView) v.findViewById(R.id.discount_coupon_item_amount_txt);
            time = (TextView) v.findViewById(R.id.discount_coupon_item_time_txt);
            desc = (TextView) v.findViewById(R.id.discount_coupon_item_desc_txt);
            title = (TextView) v.findViewById(R.id.discount_coupon_item_title_txt);
            use = (TextView) v.findViewById(R.id.discount_coupon_item_use_txt);
        }
    }
}
