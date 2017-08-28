package com.crv.ole.home.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.home.model.SpecialListData;
import com.crv.ole.information.model.ListResult;
import com.crv.ole.utils.LoadImageUtil;
import com.crv.sdk.utils.StringUtils;

import java.util.List;

/**
 * Created by wj_wsf on 2017/6/28 15:07.
 */

public class SpecialListAdapter extends BaseAdapter {
    private Context context;
    private List<ListResult.RETURNDATABean.InformationBean> datas;
    private int type = 0;

    public SpecialListAdapter(Context context, List<ListResult.RETURNDATABean.InformationBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas == null ? null : datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.special_list_item_layout, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        LoadImageUtil.getInstance().loadImage(viewHolder.special_list_item_img, datas.get(i).getCoverImg(), R.drawable.capture01, ImageView.ScaleType.CENTER_CROP);
        LoadImageUtil.getInstance().loadImage(viewHolder.special_list_item_img_icon, datas.get(i).getIconUrl(), R.drawable.capture01, ImageView.ScaleType.MATRIX);

        if (StringUtils.isNullOrEmpty(datas.get(i).getAuthor())) {

        } else {
            viewHolder.special_list_item_desc_txt.setText(Html.fromHtml(datas.get(i).getAuthor()));

        }
        if (StringUtils.isNullOrEmpty(datas.get(i).getTitle())) {

        } else {
            viewHolder.special_list_item_title_txt.setText(datas.get(i).getTitle());

        }
        viewHolder.special_list_item_read_txt.setText(datas.get(i).getLikeCount() + "");
        viewHolder.special_list_item_collection_txt.setText(datas.get(i).getFavoriteCount() + "");
        return view;
    }

    private class ViewHolder {
        private ImageView special_list_item_img, special_list_item_img_icon;
        private TextView special_list_item_title_txt, special_list_item_desc_txt;
        private TextView special_list_item_collection_txt, special_list_item_read_txt;

        public ViewHolder(View v) {
            special_list_item_img = (ImageView) v.findViewById(R.id.special_list_item_img);
            special_list_item_img_icon = (ImageView) v.findViewById(R.id.special_list_item_img_icon);
            special_list_item_title_txt = (TextView) v.findViewById(R.id.special_list_item_title_txt);
            special_list_item_desc_txt = (TextView) v.findViewById(R.id.special_list_item_desc_txt);
            special_list_item_collection_txt = (TextView) v.findViewById(R.id.special_list_item_collection_txt);
            special_list_item_read_txt = (TextView) v.findViewById(R.id.special_list_item_read_txt);
        }
    }
}
