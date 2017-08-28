package com.crv.ole.shopping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.crv.ole.R;
import com.crv.ole.personalcenter.model.CollectionGoodsFolderListData;
import com.crv.ole.utils.LoadImageUtil;

import java.util.List;

/**
 * Created by Fairy on 2017/7/31.
 * 商品详情 - 收藏框 - 列表
 */

public class ProductCollectAdapter extends BaseAdapter {
    private Context context;
    private List<CollectionGoodsFolderListData.GoodsFolderData> dataList;

    public ProductCollectAdapter(Context context,
                                 List<CollectionGoodsFolderListData.GoodsFolderData> messageDataList) {
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
            v = LayoutInflater.from(context).inflate(R.layout.add_collection_item_layout, null);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) v.getTag();
        LoadImageUtil.getInstance().loadImage(viewHolder.add_collect_img,
                dataList.get(position).getImgUrl(), R.drawable.collection_add_face_bg_sel, ImageView.ScaleType.FIT_XY);

        return v;
    }

    private class ViewHolder {
        private ImageView add_collect_img;

        public ViewHolder(View v) {
            add_collect_img = (ImageView) v.findViewById(R.id.add_collect_img);
        }
    }
}
