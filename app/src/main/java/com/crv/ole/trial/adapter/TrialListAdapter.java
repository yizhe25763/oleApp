package com.crv.ole.trial.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.trial.callback.OnItemButtonClickListener;
import com.crv.ole.trial.model.TrialItemData;
import com.crv.ole.utils.LoadImageUtil;
import com.crv.sdk.utils.DateUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 试用记录适配器
 * Created by zhangbo on 2017/8/17.
 */

public class TrialListAdapter extends RecyclerView.Adapter<TrialListAdapter.TrialListHolder> {

    private Context context;

    private List<TrialItemData> list;

    private OnItemButtonClickListener listener;

    private View mfootView;

    public static final int TYPE_FOOTVIEW = 0;  //说明是带有footView的
    public static final int TYPE_NORMAL = 1;  //正常


    public TrialListAdapter(Context context, List<TrialItemData> mList) {
        this.context = context;
        this.list = mList;
    }

    //FootView
    public View getFootView() {
        return mfootView;
    }

    public void setFootView(View footView) {
        mfootView = footView;
        notifyItemInserted(getItemCount() - 1);
    }


    @Override
    public TrialListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mfootView != null && viewType == TYPE_FOOTVIEW) {
            return new TrialListHolder(mfootView);
        }
        return new TrialListHolder(LayoutInflater.from(context).inflate(R.layout.trial_list_item_layout, null));
    }

    @Override
    public void onBindViewHolder(TrialListHolder viewHolder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            Date date = DateUtil.parse(list.get(position).getCreateTime(), "yyyy-MM-dd HH:mm:ss");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dat = calendar.get(Calendar.DATE);
            viewHolder.tx_data.setText("申请日期: " + year + "年" + month + "月" + dat + "日");
            LoadImageUtil.getInstance().loadBackground(viewHolder.img, list.get(position).getProductLogo());
            viewHolder.tx_product_name.setText(list.get(position).getProductName());
            if (list.get(position).getApplyState() == 0) {//未通过
                viewHolder.unpass_state.setVisibility(View.VISIBLE);
                viewHolder.pass_state.setVisibility(View.GONE);
                viewHolder.da_pass_state.setVisibility(View.GONE);
                viewHolder.info.setText("不好意思,您未获取此次试用资格");
                viewHolder.bt_click.setVisibility(View.INVISIBLE);
            } else if (list.get(position).getApplyState() == 1) {//审核通过
                viewHolder.pass_state.setVisibility(View.VISIBLE);
                viewHolder.da_pass_state.setVisibility(View.GONE);
                viewHolder.unpass_state.setVisibility(View.GONE);
                viewHolder.info.setText("订单号:" + list.get(position).getAliasCode());
                if ("TA002".equals(list.get(position).getOrderState())) {//未支付
                    viewHolder.bt_click.setText("去支付");
                    viewHolder.bt_click.setVisibility(View.VISIBLE);
                } else if ("TA001".equals(list.get(position).getOrderState())) {//支付时间超时
                    //TODO 支付按钮变成灰色 并弹出提示
                    viewHolder.bt_click.setText("去支付");
                    viewHolder.bt_click.setVisibility(View.VISIBLE);

                } else if ("TA004".equals(list.get(position).getOrderState())) {//TA004:试用报告未提交
                    viewHolder.bt_click.setVisibility(View.VISIBLE);
                    viewHolder.bt_click.setText("填写试用报告");
                } else if ("TA003".equals(list.get(position).getOrderState())) {//TA003:试用报告已提交
                    viewHolder.bt_click.setVisibility(View.VISIBLE);
                    viewHolder.bt_click.setText("查看试用报告");
                } else if ("TA005".equals(list.get(position).getOrderState())) {//TA005:试用报告提交超时
                    //TODO 填写试用报告按钮变成灰色 并弹出提示
                    viewHolder.bt_click.setVisibility(View.VISIBLE);
                    viewHolder.bt_click.setText("填写试用报告");
                } else if ("TP006".equals(list.get(position).getOrderState())) {//TP006:仍在物流配送中
                    viewHolder.bt_click.setVisibility(View.VISIBLE);
                    viewHolder.bt_click.setText("查看物流");
                }
            } else if (list.get(position).getApplyState() == 2) {//待审核
                viewHolder.da_pass_state.setVisibility(View.VISIBLE);
                viewHolder.unpass_state.setVisibility(View.GONE);
                viewHolder.pass_state.setVisibility(View.GONE);
                viewHolder.bt_click.setVisibility(View.INVISIBLE);

                viewHolder.info.setText("已申请,请耐心等待");

            }
            viewHolder.info.setTag(position);
            viewHolder.itemView.setTag(position);
        } else if (getItemViewType(position) == TYPE_FOOTVIEW) {
            return;
        } else {
            return;
        }
    }


    /**
     * 重写这个方法，很重要，是加入Header和Footer的关键，我们通过判断item的类型，从而绑定不同的view    *
     */
    @Override
    public int getItemViewType(int position) {
        if (mfootView == null) {
            return TYPE_NORMAL;
        }
        if (position == getItemCount() - 1) {
            return TYPE_FOOTVIEW;
        }
        return TYPE_NORMAL;
    }


    @Override
    public int getItemCount() {

        if (mfootView == null) {
            return list == null ? 0 : list.size();
        } else if (mfootView != null) {
            return list == null ? 0 : list.size() + 1;
        }
        return list == null ? 0 : list.size();
    }

    class TrialListHolder extends RecyclerView.ViewHolder {
        private TextView tx_data;
        private TextView pass_state;
        private TextView da_pass_state;
        private TextView unpass_state;
        private ImageView img;
        private TextView tx_product_name;
        private TextView info;
        private TextView bt_click;

        public TrialListHolder(View itemView) {
            super(itemView);
            //如果是headerview或者是footerview,直接返回
            if (itemView == mfootView) {
                return;
            }
            tx_data = (TextView) itemView.findViewById(R.id.tx_data);
            da_pass_state = (TextView) itemView.findViewById(R.id.da_pass_state);
            pass_state = (TextView) itemView.findViewById(R.id.pass_state);
            unpass_state = (TextView) itemView.findViewById(R.id.unpass_state);
            img = (ImageView) itemView.findViewById(R.id.img);
            tx_product_name = (TextView) itemView.findViewById(R.id.tx_product_name);
            info = (TextView) itemView.findViewById(R.id.info);
            bt_click = (TextView) itemView.findViewById(R.id.bt_click);
            bt_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onTrialItemButtonClick(list.get(getLayoutPosition()), getLayoutPosition());
                    }
                }
            });
        }
    }

    public void setOnItemButtonClickListener(OnItemButtonClickListener listener) {
        this.listener = listener;
    }
}
