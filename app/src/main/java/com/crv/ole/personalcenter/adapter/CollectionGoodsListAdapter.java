package com.crv.ole.personalcenter.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.personalcenter.model.CollectionFolderListData;
import com.crv.ole.personalcenter.model.CollectionGoodsListData;
import com.crv.ole.utils.LoadImageUtil;
import com.crv.ole.utils.OleConstants;

import java.util.List;

/**
 * 个人中心 - 我的收藏 - 商品列表适配器
 * Created by wj_wsf on 2017/7/17.
 */

public class CollectionGoodsListAdapter extends BaseAdapter {
    private Context context;
    private List<CollectionGoodsListData.GoodsData> dataList;

    public CollectionGoodsListAdapter(Context context, List<CollectionGoodsListData.GoodsData> messageDataList) {
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
            v = LayoutInflater.from(context).inflate(R.layout.collection_goods_list_item_layout, null);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) v.getTag();

        CollectionGoodsListData.GoodsData data = dataList.get(position);

        viewHolder.collection_goods_item_title.setText(data.getName());
        if (Float.valueOf(data.getDifferencePrice()) > 0) {
            viewHolder.collection_goods_item_warn.setVisibility(View.VISIBLE);
            viewHolder.collection_goods_item_warn.setText("已降价" + data.getDifferencePrice() + "元");
        } else
            viewHolder.collection_goods_item_warn.setVisibility(View.GONE);

        if (data.getPrice() > 0 && data.getRealPrice() > 0) {
            viewHolder.collection_goods_item_price.setText("¥" + data.getRealPrice());
            viewHolder.collection_goods_item_old_price.setText("¥" + data.getPrice());
            viewHolder.collection_goods_item_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.collection_goods_item_old_price.setVisibility(View.VISIBLE);

        } else {
            if (data.getPrice() > 0 && data.getRealPrice() <= 0) {
                viewHolder.collection_goods_item_price.setText("¥" + data.getPrice());
                viewHolder.collection_goods_item_old_price.setText("");
                viewHolder.collection_goods_item_old_price.setVisibility(View.GONE);
            } else if (data.getPrice() <= 0 && data.getRealPrice() > 0) {
                viewHolder.collection_goods_item_price.setText("¥" + data.getRealPrice());
                viewHolder.collection_goods_item_old_price.setText("");
                viewHolder.collection_goods_item_old_price.setVisibility(View.GONE);
            }
        }

        if ("1".equals(data.getVipLevel())) {
            viewHolder.collection_goods_item_level_img.setVisibility(View.VISIBLE);
            viewHolder.collection_goods_item_level_img.setImageResource(R.drawable.ic_v1);
        } else if ("2".equals(data.getVipLevel())) {
            viewHolder.collection_goods_item_level_img.setVisibility(View.VISIBLE);
            viewHolder.collection_goods_item_level_img.setImageResource(R.drawable.ic_v2);
        } else if ("3".equals(data.getVipLevel())) {
            viewHolder.collection_goods_item_level_img.setVisibility(View.VISIBLE);
            viewHolder.collection_goods_item_level_img.setImageResource(R.drawable.ic_v3);
        } else {
            viewHolder.collection_goods_item_level_img.setVisibility(View.GONE);
        }

        if (data.isCanBuy() && data.getCanBuyCount() > 0) {
            viewHolder.collection_goods_item_mgl_img.setVisibility(View.GONE);
        } else {
            viewHolder.collection_goods_item_mgl_img.setVisibility(View.VISIBLE);
        }

        viewHolder.collection_goods_item_gwc_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //购物车
                if (data.isCanBuy() && data.getCanBuyCount() > 0) {
                }
            }
        });

        LoadImageUtil.getInstance().loadImage(viewHolder.collection_goods_item_img, OleConstants.BASE_HOST + data.getImgSrc());

        return v;
    }

    private class ViewHolder {
        public ViewHolder(View v) {
            collection_goods_item_warn = (TextView) v.findViewById(R.id.collection_goods_item_warn);
            collection_goods_item_title = (TextView) v.findViewById(R.id.collection_goods_item_title);
            collection_goods_item_price = (TextView) v.findViewById(R.id.collection_goods_item_price);
            collection_goods_item_old_price = (TextView) v.findViewById(R.id.collection_goods_item_old_price);
            collection_goods_item_img = (ImageView) v.findViewById(R.id.collection_goods_item_img);
            collection_goods_item_mgl_img = (ImageView) v.findViewById(R.id.collection_goods_item_mgl_img);
            collection_goods_item_level_img = (ImageView) v.findViewById(R.id.collection_goods_item_level_img);
            collection_goods_item_gwc_img = (ImageView) v.findViewById(R.id.collection_goods_item_gwc_img);

        }

        private TextView collection_goods_item_warn, collection_goods_item_title,
                collection_goods_item_price, collection_goods_item_old_price;
        private ImageView collection_goods_item_img, collection_goods_item_mgl_img,
                collection_goods_item_level_img, collection_goods_item_gwc_img;
    }
}
