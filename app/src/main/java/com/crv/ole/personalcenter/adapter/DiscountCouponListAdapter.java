package com.crv.ole.personalcenter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.personalcenter.model.CouponResponseData;

import java.util.ArrayList;
import java.util.List;

/**
 * 作用描述：优惠卷适配器
 * 创建者： lihongshi
 * 创建时间： 2017/7/12 11:44.
 */
public class DiscountCouponListAdapter extends RecyclerView.Adapter<DiscountCouponListAdapter.DiscountViewHolder> {
    private List<CouponResponseData.VoucherList> dataList;

    public DiscountCouponListAdapter(List<CouponResponseData.VoucherList> messageDataList) {
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
    public DiscountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discount_coupon_list_item_layout, parent, false);
        return new DiscountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DiscountViewHolder viewHolder, int position) {
        viewHolder.amount.setText(dataList.get(position).getAmount());
        viewHolder.desc.setText(dataList.get(position).getRuleRemarkDes());
        viewHolder.title.setText(dataList.get(position).getOuterName());
        viewHolder.time.setText(dataList.get(position).getEffectedEnd());
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    class DiscountViewHolder extends RecyclerView.ViewHolder {
        private TextView url, time, desc, amount, title, use;

        public DiscountViewHolder(View v) {
            super(v);
            amount = (TextView) v.findViewById(R.id.discount_coupon_item_amount_txt);
            time = (TextView) v.findViewById(R.id.discount_coupon_item_time_txt);
            desc = (TextView) v.findViewById(R.id.discount_coupon_item_desc_txt);
            title = (TextView) v.findViewById(R.id.discount_coupon_item_title_txt);
            use = (TextView) v.findViewById(R.id.discount_coupon_item_use_txt);
        }
    }
}
