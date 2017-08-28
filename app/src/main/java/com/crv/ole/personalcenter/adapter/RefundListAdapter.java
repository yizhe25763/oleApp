package com.crv.ole.personalcenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.personalcenter.model.RefundListData;
import com.crv.ole.shopping.model.RefundDetailResponseData;
import com.crv.ole.utils.LoadImageUtil;
import com.crv.sdk.utils.TextUtil;

import java.util.List;

import me.codeboy.android.aligntextview.AlignTextView;

/**
 * 退货退款列表
 * Created by Fairy on 2017/7/14.
 */

public class RefundListAdapter extends BaseAdapter {
    private Context context;
    private List<RefundListData> dataList;
    private LayoutInflater inflater;

    public RefundListAdapter(Context context, List<RefundListData> dataList) {
        this.context = context;
        this.dataList = dataList;
        inflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public Object getItem(int position) {
        return dataList == null ? null : dataList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View contentView, ViewGroup parent) {
        ViewHolder holder = null;
        if (contentView == null) {
            contentView = LayoutInflater.from(context).inflate(R.layout.refund_item_layout, null);
            holder = new ViewHolder(contentView);
            contentView.setTag(holder);
        } else {
            holder = (ViewHolder) contentView.getTag();
        }
        //TODO
        holder.tx_applay_time.setText("申请日期：" + dataList.get(position).getFormatCreateTime());

        holder.type.setText(dataList.get(position).getType().getDesc());
        holder.state.setText(dataList.get(position).getProcessState().getDesc());

        //按钮全部先隐藏
        holder.bnInputWL.setVisibility(View.GONE);

        if ("1".equals(dataList.get(position).getProcessState().getState())) {//待退货--只有有实物退款的时候才有退货
            holder.bnInputWL.setVisibility(View.VISIBLE);//填写物流单号
        }
        int count = dataList.get(position).getItems().size();
        holder.ll_order_pdt_list.removeAllViews();
        for (int i = 0; i < dataList.get(position).getItems().size(); i++) {
            RefundDetailResponseData.Items item = dataList.get(position).getItems().get(i);
            View view = inflater.inflate(R.layout.my_order_product_item_layout, null);
            view.setTag(dataList.get(position).getItems().get(i));
            ImageView img = (ImageView) view.findViewById(R.id.img);
            AlignTextView name = (AlignTextView) view.findViewById(R.id.name);
            TextView txprice = (TextView) view.findViewById(R.id.price);
            TextView num = (TextView) view.findViewById(R.id.num);
            RelativeLayout rl_pre = (RelativeLayout) view.findViewById(R.id.rl_pre);
            rl_pre.setVisibility(View.GONE);
            if (!TextUtil.isEmpty(item.getImgUrl())) {
                LoadImageUtil.getInstance().loadImage(img, item.getImgUrl());
            }
            name.setText(item.getName());
            txprice.setText("¥" + item.getPriceInfo().getFUnitPrice());
            num.setText(item.getSignedAmount() + "");
            holder.ll_order_pdt_list.addView(view);
        }
        holder.desc.setText(String.format(context.getString(R.string.myOrder_totle), count + "", dataList.get(position).getTotalRefundPrice() + ""));

        holder.bnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onDetailClick(dataList.get(position));
                }
            }
        });

        holder.bnInputWL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onInputWL(dataList.get(position));
                }
            }
        });
        return contentView;
    }

    private class ViewHolder {

        private TextView tx_applay_time;
        private TextView state;
        private TextView type;
        private TextView desc;

        private Button bnInputWL;
        private Button bnDetail;
        private LinearLayout ll_order_pdt_list;

        private ViewHolder(View v) {
            tx_applay_time = (TextView) v.findViewById(R.id.tx_applay_time);
            state = (TextView) v.findViewById(R.id.state);
            type = (TextView) v.findViewById(R.id.type);
            ll_order_pdt_list = (LinearLayout) v.findViewById(R.id.ll_order_pdt_list);
            desc = (TextView) v.findViewById(R.id.desc);
            bnDetail = (Button) v.findViewById(R.id.bnDetail);
            bnInputWL = (Button) v.findViewById(R.id.bnInputWL);
        }
    }


    public interface OnRefundClickListener {

        void onDetailClick(RefundListData data);//查看详情

        void onInputWL(RefundListData data);//填写运单
    }

    private OnRefundClickListener mListener;

    public void setOnRefundClickListener(OnRefundClickListener listener) {
        this.mListener = listener;
    }
}
