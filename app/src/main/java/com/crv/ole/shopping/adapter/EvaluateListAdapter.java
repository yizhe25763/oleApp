package com.crv.ole.shopping.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.information.activity.SpecialCommentActivity;
import com.crv.ole.shopping.activity.LookPicActivity;
import com.crv.ole.shopping.model.EvaluateInfoResult;
import com.crv.ole.shopping.model.PhotoInfo;
import com.crv.ole.utils.LoadImageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fairy on 2017/8/1.
 * 商品详情页 - 详情页 - 用户评价适配
 */

public class EvaluateListAdapter extends BaseAdapter {
    private Context context;
    private List<EvaluateInfoResult.RETURNDATABean.RecordListBean> dataList;
    private SpecialCommentActivity.ZanInterf listener;

    public EvaluateListAdapter(Context context, List<EvaluateInfoResult.RETURNDATABean.RecordListBean> messageDataList) {
        this.context = context;
        this.dataList = messageDataList;
    }
    public void setListener(SpecialCommentActivity.ZanInterf listener){
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

        EvaluateInfoResult.RETURNDATABean.RecordListBean recordBean = dataList.get(position);
        LoadImageUtil.getInstance().loadImage(holder.img, recordBean.getCreateUserLogo(),
                R.drawable.capture01, null);
        holder.name.setText(recordBean.getCreateUserName());
        holder.time.setText(recordBean.getCreateTime());
        holder.likes.setText(recordBean.getLikes().getLikesCount()+"");
        holder.data.setText(recordBean.getComment());
        if(recordBean.getImages() != null){
            List<String> imgs = recordBean.getImages();
            switch (recordBean.getImages().size()){
                default:
                    holder.imgs_3_num.setText("+"+(imgs.size()-3));
                    holder.imgs_3_num.setVisibility(View.VISIBLE);
                case 3:
                    LoadImageUtil.getInstance().loadImage(holder.imgs_3, imgs.get(2),
                            R.drawable.capture03, null);
                    holder.imgs_3_layout.setVisibility(View.VISIBLE);
                case 2:
                    LoadImageUtil.getInstance().loadImage(holder.imgs_2, imgs.get(1),
                            R.drawable.capture02, null);
                    holder.imgs_2.setVisibility(View.VISIBLE);
                case 1:
                    LoadImageUtil.getInstance().loadImage(holder.imgs_1, imgs.get(0),
                            R.drawable.capture01, null);
                    holder.imgs_1.setVisibility(View.VISIBLE);
                    holder.imgsLayout.setVisibility(View.VISIBLE);
                    break;
                case 0:
                    holder.imgsLayout.setVisibility(View.GONE);
                    break;
            }
        }else{
            holder.imgsLayout.setVisibility(View.GONE);
        }
        holder.imgs_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLookActivity(position, 0);
            }
        });
        holder.imgs_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLookActivity(position, 1);
            }
        });
        holder.imgs_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLookActivity(position, 2);
            }
        });

        int type = recordBean.getLikes().getStatus();
        if(type == 0){
            holder.zanImg.setBackgroundResource(R.drawable.btn_ztxq_dz_normal);
        }else if(type == 1){
            holder.zanImg.setBackgroundResource(R.drawable.btn_ztxq_dz_selected);
        }
        holder.zanLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.requestZannet(type, position);
                }
            }
        });
        return v;
    }

    private void toLookActivity(int position, int index){
        List<String> imgs = dataList.get(position).getImages();
        ArrayList<PhotoInfo> photoInfos = new ArrayList<>();
        for (String img : imgs){
            PhotoInfo photoInfo = new PhotoInfo();
            photoInfo.setSourcePath(img);
            photoInfo.setNetResource(true);
            photoInfos.add(photoInfo);
        }
        Intent intent = new Intent(context, LookPicActivity.class);
        intent.putExtra("EXTRA_IMAGE_LIST", photoInfos);
        intent.putExtra("EXTRA_CURRENT_IMG_POSITION", index);
        context.startActivity(intent);
    }

    private class ViewHolder {
        private ImageView img;
        private TextView name;
        private TextView time;
        private TextView likes;
        private TextView data;
        private ImageView zanImg;
        private LinearLayout zanLayout;
        private LinearLayout imgsLayout;
        private ImageView imgs_1;
        private ImageView imgs_2;
        private RelativeLayout imgs_3_layout;
        private ImageView imgs_3;
        private TextView imgs_3_num;

        public ViewHolder(View v) {
            name = (TextView) v.findViewById(R.id.comment_name);
            time = (TextView) v.findViewById(R.id.comment_time);
            likes = (TextView) v.findViewById(R.id.comment_likes);
            data = (TextView) v.findViewById(R.id.comment_data);
            img = (ImageView) v.findViewById(R.id.comment_img);
            zanImg = (ImageView) v.findViewById(R.id.comment_zanImg);
            zanLayout = (LinearLayout) v.findViewById(R.id.comment_zanLayout);
            imgsLayout = (LinearLayout) v.findViewById(R.id.comment_imgs_layout);
            imgs_1 = (ImageView) v.findViewById(R.id.comment_imgs_1);
            imgs_2 = (ImageView) v.findViewById(R.id.comment_imgs_2);
            imgs_3_layout = (RelativeLayout) v.findViewById(R.id.comment_imgs_3_layout);
            imgs_3 = (ImageView) v.findViewById(R.id.comment_imgs_3);
            imgs_3_num = (TextView) v.findViewById(R.id.comment_imgs_3_Num);
        }

    }
}
