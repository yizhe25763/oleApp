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
import com.crv.ole.personalcenter.model.OrderData;
import com.crv.ole.personalcenter.model.OrderItem;
import com.crv.ole.personalcenter.model.ProcessStateInfo;
import com.crv.ole.utils.LoadImageUtil;

import java.util.List;

import me.codeboy.android.aligntextview.AlignTextView;

/**
 * Created by Fairy on 2017/7/14.
 */

public class MyOrderListAdapter extends BaseAdapter {
    private Context context;
    private List<OrderData> dataList;
    private LayoutInflater inflater;

    public MyOrderListAdapter(Context context, List<OrderData> dataList) {
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
            contentView = LayoutInflater.from(context).inflate(R.layout.my_order_item_layout, null);
            holder = new ViewHolder(contentView);
            contentView.setTag(holder);
        } else {
            holder = (ViewHolder) contentView.getTag();
        }
        holder.orderNum.setText(context.getString(R.string.order_num) + dataList.get(position).getOrderAliasCode());

        //按钮全部先隐藏
        holder.repeat.setVisibility(View.GONE);
        holder.bnShouhou.setVisibility(View.GONE);
        holder.bnEvaluate.setVisibility(View.GONE);
        holder.cancel.setVisibility(View.GONE);
        holder.pay.setVisibility(View.GONE);
        holder.bnReimburse.setVisibility(View.GONE);

        String stateStr = dataList.get(position).getPayStateInfo().getState();
        if ("p111".equalsIgnoreCase(dataList.get(position).getProcessStateInfo().getState())) {//"已取消"
            holder.repeat.setVisibility(View.VISIBLE);
            holder.state.setText("已取消");
        } else if ("p200".equalsIgnoreCase(stateStr) || "p100".equalsIgnoreCase(dataList.get(position).getProcessStateInfo().getState())) { //待支付
            String stateName = dataList.get(position).getPayStateInfo().getName();
            //普通订单
            if ("common".equals(dataList.get(position).getOrderTypeInfo().getOrderType())) {
                holder.cancel.setVisibility(View.VISIBLE);
                holder.repeat.setVisibility(View.VISIBLE);
                holder.pay.setVisibility(View.VISIBLE);
                holder.pay.setText("支付订单");
                holder.state.setText("待付款");
            }
            //预售订单
            if ("preSale".equals(dataList.get(position).getOrderTypeInfo().getOrderType())) {
                //定金预售 【 定金预售：1】,【定金预售 ，尾款不确定：2】
                if ("1".equals(dataList.get(position).getPreSaleInfo().getPreSaleType()) || "2".equals(dataList.get(position).getPreSaleInfo().getPreSaleType())) {
                    //定金未支付
                    if ("0".equals(dataList.get(position).getPreSaleInfo().getPreSalePayState())) {
                        holder.cancel.setVisibility(View.VISIBLE);
                        holder.repeat.setVisibility(View.VISIBLE);
                        holder.pay.setVisibility(View.VISIBLE);
                        holder.pay.setText("支付定金");
                        holder.state.setText("定金待付款");
                    }
                    //定金已支付,尾款未支付
                    else if ("1".equals(dataList.get(position).getPreSaleInfo().getPreSalePayState())) {
                        holder.cancel.setVisibility(View.VISIBLE);
                        holder.repeat.setVisibility(View.VISIBLE);
                        holder.pay.setVisibility(View.VISIBLE);
                        holder.pay.setText("支付尾款");
                        holder.state.setText("尾款待支付");
                    } else {
                        holder.cancel.setVisibility(View.VISIBLE);
                        holder.repeat.setVisibility(View.VISIBLE);
                        holder.pay.setVisibility(View.GONE);
                        holder.state.setText("尾款待支付");
                    }
                } else if ("3".equals(dataList.get(position).getPreSaleInfo().getPreSaleType())) {//全款预售：3
                    //2:定金与尾款都已支付
                    if ("2".equals(dataList.get(position).getPreSaleInfo().getPreSalePayState())) {
                        holder.cancel.setVisibility(View.VISIBLE);
                        holder.repeat.setVisibility(View.VISIBLE);
                        holder.pay.setVisibility(View.GONE);
                    } else {
                        holder.cancel.setVisibility(View.VISIBLE);
                        holder.repeat.setVisibility(View.VISIBLE);
                        holder.pay.setVisibility(View.VISIBLE);
                        holder.pay.setText("支付全款");
                        holder.state.setText("待付款");
                    }
                }
            }
        } else {
            //p101：待出库 p102：已发货 p112：已签收
            ProcessStateInfo processStateInfo = dataList.get(position).getProcessStateInfo();
            String state = processStateInfo.getState();
            String stateName = processStateInfo.getName();
            if ("p101".equalsIgnoreCase(state)) {//p101：待出库
                holder.repeat.setVisibility(View.VISIBLE);
                holder.bnReimburse.setVisibility(View.VISIBLE);
                if ("common".equals(dataList.get(position).getOrderTypeInfo().getOrderType())) {
                    holder.state.setText("待发货");
                } else if ("preSale".equals(dataList.get(position).getOrderTypeInfo().getOrderType())) {
                    holder.state.setText("待备货");
                } else {
                    holder.state.setText(stateName);
                }
            } else if ("p102".equalsIgnoreCase(state)) {//p102：已发货待签收
                holder.repeat.setVisibility(View.VISIBLE);
                holder.state.setText("待签收");
            } else if ("p112".equalsIgnoreCase(state)) {//p112：已签收待评价
                holder.repeat.setVisibility(View.VISIBLE);
                holder.bnShouhou.setVisibility(View.VISIBLE);
                holder.bnEvaluate.setVisibility(View.VISIBLE);
                if (dataList.get(position).getBuyerReviewInfo() != null) {
                    if ("br102".equals(dataList.get(position).getBuyerReviewInfo().getState())) {//已评价
                        holder.bnEvaluate.setText("查看评价");
                        holder.state.setText("已完成");
                    } else {//未评价
                        holder.state.setText("待评价");
                    }
                } else {//未评价
                    holder.state.setText("待评价");
                }
            } else {//已完成
                holder.repeat.setVisibility(View.VISIBLE);
                holder.bnShouhou.setVisibility(View.VISIBLE);
                holder.state.setText("已完成");
            }
        }


        int count = dataList.get(position).getItems().size();
        holder.ll_order_pdt_list.removeAllViews();
        for (int i = 0; i < dataList.get(position).getItems().size(); i++) {
            OrderItem item = dataList.get(position).getItems().get(i);
            View view = inflater.inflate(R.layout.my_order_product_item_layout, null);
            view.setTag(dataList.get(position).getItems().get(i));
            ImageView img = (ImageView) view.findViewById(R.id.img);
            RelativeLayout rl_pre = (RelativeLayout) view.findViewById(R.id.rl_pre);

            if ("preSale".equals(dataList.get(position).getOrderTypeInfo().getOrderType())) {
                rl_pre.setVisibility(View.VISIBLE);
            } else {
                rl_pre.setVisibility(View.GONE);
            }
            AlignTextView name = (AlignTextView) view.findViewById(R.id.name);
            TextView txprice = (TextView) view.findViewById(R.id.price);
            TextView num = (TextView) view.findViewById(R.id.num);
            LoadImageUtil.getInstance().loadImage(img, item.getLogoUrl());
            name.setText(item.getProductName());
            txprice.setText("¥" + item.getfTotalPrice());
            num.setText(item.getAmount() + "");
            holder.ll_order_pdt_list.addView(view);
        }
        holder.desc.setText(String.format(context.getString(R.string.myOrder_totle), count + "", dataList.get(position).getfTotalOrderPrice() + ""));

        holder.repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onRepeatClick(dataList.get(position));
                }
            }
        });
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onCancleClick(dataList.get(position));
                }
            }
        });
        holder.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onPayClick(dataList.get(position));
                }
            }
        });
        holder.rl_order_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onOrderNumClick(dataList.get(position));
                }
            }
        });
        holder.bnShouhou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onShoHouClick(dataList.get(position));
                }

            }
        });
        holder.bnEvaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onEvaluateClick(dataList.get(position));
                }
            }
        });
        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(dataList.get(position));
                }
            }
        });

        holder.bnReimburse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onReimburse(dataList.get(position));
                }
            }
        });
        return contentView;
    }

    private class ViewHolder {

        private TextView orderNum;
        private TextView state;
        private TextView desc;
        private Button repeat;
        private Button cancel;
        private Button pay;
        private Button bnShouhou;
        private Button bnReimburse;
        private Button bnEvaluate;
        private LinearLayout ll_order_pdt_list;
        private RelativeLayout rl_order_num;
        private LinearLayout ll_item;

        private ViewHolder(View v) {
            orderNum = (TextView) v.findViewById(R.id.orderNum);
            state = (TextView) v.findViewById(R.id.state);
            ll_order_pdt_list = (LinearLayout) v.findViewById(R.id.ll_order_pdt_list);
            desc = (TextView) v.findViewById(R.id.desc);
            repeat = (Button) v.findViewById(R.id.repeat);
            cancel = (Button) v.findViewById(R.id.cancel);
            pay = (Button) v.findViewById(R.id.pay);
            bnReimburse = (Button) v.findViewById(R.id.bnReimburse);
            bnShouhou = (Button) v.findViewById(R.id.bnShouhou);
            bnEvaluate = (Button) v.findViewById(R.id.bnEvaluate);
            rl_order_num = (RelativeLayout) v.findViewById(R.id.rl_order_num);
            ll_item = (LinearLayout) v.findViewById(R.id.ll_item);
        }
    }


    public interface OnOrderClickListener {
        void onRepeatClick(OrderData data);//复购

        void onCancleClick(OrderData data);//取消订单

        void onPayClick(OrderData data);//支付

        void onShoHouClick(OrderData data);//发起售后

        void onEvaluateClick(OrderData data);//评价

        void onOrderNumClick(OrderData data);//订单详情

        void onItemClick(OrderData data);//条目点击

        void onReimburse(OrderData data);//申请退款
    }


    private OnOrderClickListener mListener;

    public void setOrderClickListener(OnOrderClickListener listener) {
        this.mListener = listener;
    }


}
