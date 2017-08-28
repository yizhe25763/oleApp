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
import com.crv.ole.trial.callback.OnProductItemClickListener;
import com.crv.ole.trial.model.TrialProduct;
import com.crv.ole.trial.model.TrialReportGoodsItemData;
import com.crv.ole.utils.LoadImageUtil;
import com.crv.ole.utils.Log;
import com.crv.sdk.utils.TextUtil;

import java.util.List;

/**
 * 有试用报告的商品列表适配器
 */
public class TrialReportGoodsListAdapter extends BaseAdapter {

    private Context context;

    private List<TrialReportGoodsItemData> list;

    public TrialReportGoodsListAdapter(Context context, List<TrialReportGoodsItemData> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.trial_report_product_list_item_layout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (!TextUtil.isEmpty(list.get(position).getProductImage())) {
            LoadImageUtil.getInstance().loadImage(viewHolder.im_product, list.get(position).getProductImage());
        }
        viewHolder.tx_name.setText(list.get(position).getName());
        return convertView;
    }


    private class ViewHolder {
        private ImageView im_product;
        private TextView tx_name;

        public ViewHolder(View view) {
            im_product = (ImageView) view.findViewById(R.id.im_product);
            tx_name = (TextView) view.findViewById(R.id.tx_name);
        }

    }
}
