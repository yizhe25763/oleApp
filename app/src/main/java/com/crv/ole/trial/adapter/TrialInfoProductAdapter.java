package com.crv.ole.trial.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.base.BaseItemTouchListener;
import com.crv.ole.trial.callback.OnProductItemClickListener;
import com.crv.ole.trial.model.TrialProduct;
import com.crv.ole.utils.LoadImageUtil;
import com.crv.sdk.utils.TextUtil;

import java.util.List;

/**
 * Created by zhangbo on 2017/8/17.
 */

public class TrialInfoProductAdapter extends BaseAdapter {

    private Context context;

    private List<TrialProduct> list;

    private OnProductItemClickListener listener;

    public TrialInfoProductAdapter(Context context, List<TrialProduct> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list == null ? null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.trial_product_item_layout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (!TextUtil.isEmpty(list.get(position).getProductImage())) {
            LoadImageUtil.getInstance().loadImage(viewHolder.im_product, list.get(position).getProductImage());
        }
        viewHolder.tx_count.setText(String.format(context.getString(R.string.num), list.get(position).getSellNum()));
        viewHolder.tx_name.setText(list.get(position).getName());
        viewHolder.tx_price.setText(String.format(context.getString(R.string.price), TextUtil.isEmpty(list.get(position).getMemberPrice()) ? "0" : list.get(position).getMemberPrice()));
        viewHolder.tx_desc.setText(list.get(position).getProductDescription());
        viewHolder.bt_applay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.OnButtonItemClick(list.get(position), position);
                }
            }
        });
        return convertView;
    }


    private class ViewHolder {
        private ImageView im_product;
        private TextView tx_count;
        private TextView tx_name;
        private TextView tx_price;
        private TextView tx_desc;
        private Button bt_applay;

        public ViewHolder(View view) {
            im_product = (ImageView) view.findViewById(R.id.im_product);
            tx_count = (TextView) view.findViewById(R.id.tx_count);
            tx_name = (TextView) view.findViewById(R.id.tx_name);
            tx_price = (TextView) view.findViewById(R.id.tx_price);
            tx_desc = (TextView) view.findViewById(R.id.tx_desc);
            bt_applay = (Button) view.findViewById(R.id.bt_applay);
        }

    }

    public void setOnProductItemClickListener(OnProductItemClickListener listener) {
        this.listener = listener;
    }
}
