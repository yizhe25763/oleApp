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
import com.crv.ole.home.model.DataBean;
import com.crv.ole.home.model.HWBean;
import com.crv.ole.utils.LoadImageUtil;
import com.crv.sdk.utils.StringUtils;
import com.sina.weibo.sdk.call.Position;

import java.util.List;


public class SYAdapter extends BaseAdapter {
    private Context context;
    private List<DataBean> dataList;

    public SYAdapter(Context context, List<DataBean> messageDataList) {
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
            v = LayoutInflater.from(context).inflate(R.layout.item_recyclerview, null);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }
        DataBean bean = dataList.get(position);
        if (null != bean) {

            if (position == 0) {
                viewHolder.img.setVisibility(View.GONE);
                viewHolder.title.setVisibility(View.GONE);
                viewHolder.Time.setVisibility(View.GONE);
                viewHolder.tvUnitName.setVisibility(View.GONE);
                viewHolder.titleLayout.setVisibility(View.GONE);
                viewHolder.contentLayout.setVisibility(View.GONE);
            } else {
                if (bean.getUnitName().equals("")) {
                    viewHolder.img.setVisibility(View.VISIBLE);
                    viewHolder.title.setVisibility(View.VISIBLE);
                    viewHolder.Time.setVisibility(View.VISIBLE);
                    viewHolder.tvUnitName.setVisibility(View.GONE);
                    viewHolder.titleLayout.setVisibility(View.GONE);
                    viewHolder.contentLayout.setVisibility(View.VISIBLE);
                } else {
                    if (bean.getName().equals("")) {
                        viewHolder.img.setVisibility(View.GONE);
                        viewHolder.title.setVisibility(View.GONE);
                        viewHolder.Time.setVisibility(View.GONE);
                        viewHolder.tvUnitName.setVisibility(View.GONE);
                        viewHolder.titleLayout.setVisibility(View.GONE);
                        viewHolder.contentLayout.setVisibility(View.GONE);
                    } else {
                        if (dataList.get(position - 1).getUnitName().equals(dataList.get(position - 2).getUnitName())) {
                            viewHolder.tvUnitName.setVisibility(View.GONE);
                            viewHolder.titleLayout.setVisibility(View.GONE);
                        } else {
                            viewHolder.tvUnitName.setVisibility(View.VISIBLE);
                            viewHolder.titleLayout.setVisibility(View.VISIBLE);
                        }
                        viewHolder.img.setVisibility(View.VISIBLE);
                        viewHolder.title.setVisibility(View.VISIBLE);
                        viewHolder.Time.setVisibility(View.VISIBLE);
                        viewHolder.contentLayout.setVisibility(View.VISIBLE);
                    }
                }
            }
            LoadImageUtil.getInstance().loadImage(viewHolder.img, dataList.get(position).getImageUrl(), R.drawable.capture01, null);
            viewHolder.title.setText(!StringUtils.isNullOrEmpty(dataList.get(position).getName()) ? dataList.get(position).getName() : "暂无数据");
            viewHolder.Time.setText(!StringUtils.isNullOrEmpty(dataList.get(position).getParp()) ? dataList.get(position).getParp() : "暂无数据");
            viewHolder.tvUnitName.setText(!StringUtils.isNullOrEmpty(dataList.get(position).getUnitName()) ? dataList.get(position).getUnitName() : "暂无数据");

        }
        return v;
    }

    private static class ViewHolder {
        private ImageView img;
        private TextView title;
        private TextView Time;
        private TextView tvUnitName;
        private LinearLayout titleLayout;
        private LinearLayout contentLayout;


        public ViewHolder(View v) {
            img = (ImageView) v.findViewById(R.id.image_sy);
            title = (TextView) v.findViewById(R.id.title);
            Time = (TextView) v.findViewById(R.id.tv_title);
            tvUnitName = (TextView) v.findViewById(R.id.tvUnitName);
            titleLayout = (LinearLayout) v.findViewById(R.id.titles);
            contentLayout = (LinearLayout) v.findViewById(R.id.content_layout);

        }

    }


}
