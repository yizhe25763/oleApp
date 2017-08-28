package com.crv.ole.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.home.model.HWBean;
import com.crv.ole.utils.LoadImageUtil;

import java.sql.Time;
import java.util.List;


public class HWAdapter extends BaseAdapter {
    private Context context;
    private List<HWBean.RETURNDATABean> dataList;

    public HWAdapter(Context context, List<HWBean.RETURNDATABean> messageDataList) {
        this.context = context;
        this.dataList = messageDataList;
    }

    @Override
    public int getCount() {
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
            v = LayoutInflater.from(context).inflate(R.layout.adapter_market_hw, null);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }
        LoadImageUtil.getInstance().loadImage(viewHolder.img, dataList.get(position).getImgUrl(), R.drawable.capture01, null);
        viewHolder.title.setText(dataList.get(position).getPara());
        viewHolder.Time.setText( dataList.get(position).getTime());
        viewHolder.zk.setText( dataList.get(position).getDiscount());

        return v;
    }

    private static class ViewHolder {
        private ImageView img;
        private TextView title;
        private TextView Time;
        private TextView zk;


        public ViewHolder(View v) {
            img = (ImageView) v.findViewById(R.id.image);
            title = (TextView) v.findViewById(R.id.title);
            Time = (TextView) v.findViewById(R.id.content);
            zk = (TextView) v.findViewById(R.id.zk);
        }

    }


}
