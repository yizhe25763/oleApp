package com.crv.ole.personalcenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.information.model.InformationListData;
import com.crv.ole.personalcenter.model.CollectionFolderListData;
import com.crv.ole.utils.LoadImageUtil;

import java.util.List;

/**
 * 个人中心 - 我的收藏 - 咨询列表适配器
 * Created by wj_wsf on 2017/7/17.
 */

public class CollectionInformationListAdapter extends BaseAdapter {
    private Context context;
    private List<CollectionFolderListData.FolderData> dataList;

    public CollectionInformationListAdapter(Context context, List<CollectionFolderListData.FolderData> messageDataList) {
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
        ViewHolder viewHolder;
        if (v == null) {
            v = LayoutInflater.from(context).inflate(R.layout.collection_information_list_item_layout, null);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) v.getTag();

        viewHolder.name.setText(dataList.get(position).getTitle());
        LoadImageUtil.getInstance().loadImage(viewHolder.collection_information_item_img, dataList.get(position).getBannerImageFileId());

        return v;
    }

    private class ViewHolder {
        public ViewHolder(View v) {
            name = (TextView) v.findViewById(R.id.collection_information_item_title);
            desc = (TextView) v.findViewById(R.id.collection_information_item_desc);
            collection_information_item_img = (ImageView) v.findViewById(R.id.collection_information_item_img);

        }

        private TextView name, desc;
        private ImageView collection_information_item_img;
    }
}
