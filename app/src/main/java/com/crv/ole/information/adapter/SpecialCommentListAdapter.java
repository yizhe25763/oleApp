package com.crv.ole.information.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.information.activity.SpecialCommentActivity;
import com.crv.ole.information.model.SpecialCommentInfoResultBean;
import com.crv.ole.utils.LoadImageUtil;
import com.crv.sdk.utils.StringUtils;

import java.util.List;

/**
 * Created by Fairy on 2017/7/21.
 * 专题评论适配器
 */

public class SpecialCommentListAdapter extends BaseAdapter {
    private Context context;
    private List<SpecialCommentInfoResultBean.SpecialCommentListInfo> dataList;
    private SpecialCommentActivity.ZanInterf listener;

    public SpecialCommentListAdapter(Context context, List<SpecialCommentInfoResultBean.SpecialCommentListInfo> messageDataList) {
        this.context = context;
        this.dataList = messageDataList;
    }

    public void setListener(SpecialCommentActivity.ZanInterf listener) {
        this.listener = listener;
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
            v = LayoutInflater.from(context).inflate(R.layout.special_comment_item_layout, null);
            holder = new ViewHolder(v);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.name.setText(StringUtils.isMobile(dataList.get(position).getNickName()) ? StringUtils.replaceMobile(dataList.get(position).getNickName()) : dataList.get(position).getNickName());
        holder.time.setText(dataList.get(position).getCreateTime());
        holder.likes.setText(dataList.get(position).getLikes());
        holder.data.setText(dataList.get(position).getCommentData());
        //  圆形头像
        LoadImageUtil.getInstance().loadIconImage(holder.img,
                dataList.get(position).getLogo(), true);
//            LoadImageUtil.getInstance().loadImage(holder.img, dataList.get(position)
//                    .getCommentImage()[0], R.drawable.capture01, null);
        if (dataList.get(position).getIsLike().equals("1")) {
            holder.zanImg.setBackgroundResource(R.drawable.btn_ztxq_dz_selected);
        } else {
            holder.zanImg.setBackgroundResource(R.drawable.btn_ztxq_dz_normal);
        }
        holder.zanLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int type = 5;
                if (TextUtils.equals(dataList.get(position).getIsLike(), "1")) {
                    type = 6;
                }
                if (listener != null) {
                    listener.requestZannet(type, position);
                }
            }
        });
        return v;
    }

    private class ViewHolder {
        private TextView name;
        private TextView time;
        private TextView likes;
        private TextView data;
        private ImageView img;
        private ImageView zanImg;
        private LinearLayout zanLayout;

        public ViewHolder(View v) {
            name = (TextView) v.findViewById(R.id.comment_name);
            time = (TextView) v.findViewById(R.id.comment_time);
            likes = (TextView) v.findViewById(R.id.comment_likes);
            data = (TextView) v.findViewById(R.id.comment_data);
            img = (ImageView) v.findViewById(R.id.comment_img);
            zanImg = (ImageView) v.findViewById(R.id.comment_zanImg);
            zanLayout = (LinearLayout) v.findViewById(R.id.comment_zanLayout);
        }

    }

}
