package com.crv.ole.personalcenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.crv.ole.R;
import com.crv.ole.base.BaseItemTouchListener;
import com.crv.ole.personalcenter.model.OrderData;
import com.crv.ole.personalcenter.model.OrderInfoReslt;
import com.crv.ole.utils.ImageUtils;
import com.crv.ole.utils.LoadImageUtil;
import com.lzy.imagepicker.bean.ImageItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lihongshi on 2017/8/17.
 */

public class AfterSaleListAdapter extends BaseAdapter {
    private List<OrderInfoReslt.RETURNDATABean.ItemsBean> mList;
    private Context mContext;
    private boolean isChcek=false;

    public AfterSaleListAdapter(Context context, List<OrderInfoReslt.RETURNDATABean.ItemsBean> dataList) {
        mContext = context;
        mList = dataList;
    }

    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    public Object getItem(int position) {
        return mList == null ? null : mList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View contentView, ViewGroup parent) {
        ViewHolder holder = null;
        if (contentView == null) {
            contentView = LayoutInflater.from(mContext).inflate(R.layout.item_order_listl, null);
            holder = new ViewHolder();
            holder.checkBox = (ImageView) contentView.findViewById(R.id.checkBox);
            holder.ivProduceImage = (ImageView) contentView.findViewById(R.id.ivProduceImage);
            holder.tvTitle = (TextView) contentView.findViewById(R.id.tvTitle);
            holder.tvPrice = (TextView) contentView.findViewById(R.id.tvPrice);
            holder.ivAdd = (ImageView) contentView.findViewById(R.id.ivAdd);
            holder.tvCount = (TextView) contentView.findViewById(R.id.tvCount);
            holder.ivDel = (ImageView) contentView.findViewById(R.id.ivDel);
            holder.tvYiJiang = (TextView) contentView.findViewById(R.id.tvYiJiang);
            contentView.setTag(holder);
        } else {
            holder = (ViewHolder) contentView.getTag();
        }
        holder.tvCount.setText(mList.get(position).getAmount() + "");
        holder.tvTitle.setText(mList.get(position).getProductName());
        holder.tvPrice.setText(mList.get(position).getFTotalPrice());
        LoadImageUtil.getInstance().loadImage(holder.ivProduceImage, mList.get(position).getLogoUrl(), false);
        holder.checkBox.setImageResource(R.drawable.checkbox_nor);
        ViewHolder finalHolder = holder;
        holder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    int num = mList.get(position).getAmount();
                    num++;
                    mList.get(position).setAmount(num);
                    mListener.onAddtClick(mList.get(position));
                    finalHolder.tvCount.setText(num + "");
                }
            }
        });
        holder.ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    int num = mList.get(position).getAmount();
                    num--;
                    if(num > 0){
                        mList.get(position).setAmount(num);
                        mListener.onJSClick(mList.get(position));
                        finalHolder.tvCount.setText(num + "");
                    }

                }
            }
        });
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    if(isChcek){
                        isChcek = false;
                        mList.get(position).setCheck(false);
                        finalHolder.checkBox.setImageResource(R.drawable.checkbox_nor);
                    }else{
                        isChcek = true;

                        mList.get(position).setCheck(true);
                        finalHolder.checkBox.setImageResource(R.drawable.checkbox_check);

                    }
                    mListener.onCheckClick(mList.get(position));
                }
            }
        });
        return contentView;
    }


    private class ViewHolder {
        private ImageView checkBox;
        private ImageView ivProduceImage;
        private TextView tvTitle;
        private TextView tvPrice;
        private ImageView ivAdd;
        private TextView tvCount;
        private ImageView ivDel;
        private TextView tvYiJiang;
    }


    public interface OnOrderClickListener {
        void onAddtClick(OrderInfoReslt.RETURNDATABean.ItemsBean data);//加

        void onJSClick(OrderInfoReslt.RETURNDATABean.ItemsBean data);//减

        void onCheckClick(OrderInfoReslt.RETURNDATABean.ItemsBean data);//复选


    }


    private OnOrderClickListener mListener;

    public void setOrderClickListener(OnOrderClickListener listener) {
        this.mListener = listener;
    }
}
