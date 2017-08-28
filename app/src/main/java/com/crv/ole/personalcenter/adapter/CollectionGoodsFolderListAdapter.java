package com.crv.ole.personalcenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.crv.ole.BaseApplication;
import com.crv.ole.R;
import com.crv.ole.personalcenter.activity.CollectionAddFolderActivity;
import com.crv.ole.personalcenter.model.CollectionFolderListData;
import com.crv.ole.personalcenter.model.CollectionGoodsFolderListData;
import com.crv.ole.utils.LoadImageUtil;
import com.crv.ole.utils.ToolUtils;

import java.util.List;

/**
 * 个人中心 - 我的收藏 - 商品文件夹列表适配器
 * Created by wj_wsf on 2017/7/17.
 */

public class CollectionGoodsFolderListAdapter extends BaseAdapter {
    private Context context;
    private List<CollectionGoodsFolderListData.GoodsFolderData> dataList;
    private int tag = 0;//是否为编辑状态？0：未编辑，1：编辑

    public CollectionGoodsFolderListAdapter(Context context,
                                            List<CollectionGoodsFolderListData.GoodsFolderData> messageDataList) {
        this.context = context;
        this.dataList = messageDataList;
    }

    public void setEditStatus(int tag) {
        this.tag = tag;
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
            v = LayoutInflater.from(context).inflate(R.layout.collection_item_layout, null);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) v.getTag();

        if (tag == 0)
            viewHolder.collection_item_edit_img.setVisibility(View.GONE);
        else {
            if (position != dataList.size() - 1)
                viewHolder.collection_item_edit_img.setVisibility(View.VISIBLE);
            else
                viewHolder.collection_item_edit_img.setVisibility(View.GONE);
        }

        viewHolder.collection_item_edit_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(context, CollectionAddFolderActivity.class);
                intent.putExtra("fromPage", "goods");
                intent.putExtra("edit_tag", "edit");
                intent.putExtra("folder_data", dataList.get(position));
                context.startActivity(intent);
            }
        });

        if (position != dataList.size() - 1)
            LoadImageUtil.getInstance().loadBackground(viewHolder.collection_item_layout, dataList.get(position).getImgUrl());
        else
            viewHolder.collection_item_layout.setBackgroundResource(Integer.valueOf(dataList.get(position).getImgUrl()));

        ViewGroup.LayoutParams layoutParams = viewHolder.collection_item_layout.getLayoutParams();
        switch (ToolUtils.getCropMaxType(BaseApplication.getDeviceWidth())) {
            case 1:
                layoutParams.height = 286;
                layoutParams.width = 218;
                break;
            case 2:
                layoutParams.height = 429;
                layoutParams.width = 327;
                break;
            case 3:
                layoutParams.height = 572;
                layoutParams.width = 436;
                break;
        }
        viewHolder.collection_item_layout.setLayoutParams(layoutParams);

        viewHolder.name.setText(dataList.get(position).getFavoriteClassName());

        return v;
    }

    private class ViewHolder {
        public ViewHolder(View v) {
            collection_item_layout = (FrameLayout) v.findViewById(R.id.collection_item_layout);
            name = (TextView) v.findViewById(R.id.collection_item_name_txt);
            collection_item_edit_img = (ImageView) v.findViewById(R.id.collection_item_edit_img);

        }

        private FrameLayout collection_item_layout;
        private TextView name;
        private ImageView collection_item_edit_img;
    }
}
