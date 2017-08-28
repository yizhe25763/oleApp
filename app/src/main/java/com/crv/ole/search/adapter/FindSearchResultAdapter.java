package com.crv.ole.search.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.information.model.ListResult;
import com.crv.ole.utils.LoadImageUtil;

import java.util.List;

/**
 * Created by Fairy on 2017/7/22.
 * 态度 - 搜索结果适配
 */

public class FindSearchResultAdapter extends BaseAdapter {
    private Context context;
    private List<ListResult.RETURNDATABean.InformationBean> dataList;

    public FindSearchResultAdapter(Context context, List<ListResult.RETURNDATABean.InformationBean> messageDataList) {
        this.context = context;
        this.dataList = messageDataList;
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList == null ? null : dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        ViewHolder holder;
        if (v == null) {
            v = LayoutInflater.from(context).inflate(R.layout.find_search_result_item_layout, null);
            holder = new ViewHolder(v);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.title.setText(dataList.get(position).getTitle());
        holder.content.setText(dataList.get(position).getDescriptions());
        LoadImageUtil.getInstance().loadImage(holder.img, dataList.get(position).getCoverImg(),
                R.drawable.special_item_bg, ImageView.ScaleType.CENTER_CROP);
        return v;
    }

    private class ViewHolder {
        private TextView title;
        private TextView content;
        private ImageView img;

        public ViewHolder(View v) {
            title = (TextView) v.findViewById(R.id.searchItem_title);
            content = (TextView) v.findViewById(R.id.searchItem_content);
            img = (ImageView) v.findViewById(R.id.searchItem_img);
        }

    }
}
