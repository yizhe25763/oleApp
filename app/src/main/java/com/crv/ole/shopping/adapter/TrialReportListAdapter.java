package com.crv.ole.shopping.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.information.activity.SpecialCommentActivity;
import com.crv.ole.shopping.activity.LookPicActivity;
import com.crv.ole.shopping.model.PhotoInfo;
import com.crv.ole.shopping.model.TrialReportInfoData;
import com.crv.ole.utils.LoadImageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fairy on 2017/8/1.
 * 商品详情页 - 详情页 - 试用报告适配
 */

public class TrialReportListAdapter extends BaseAdapter {
    private Context context;
    private List<TrialReportInfoData.RETURNDATABean.ListBean> dataList;
    private SpecialCommentActivity.ZanInterf listener;

    public TrialReportListAdapter(Context context, List<TrialReportInfoData.RETURNDATABean.ListBean> messageDataList) {
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
        holder.name.setText(dataList.get(position).getUserInfo().getNickName());
        holder.time.setText(dataList.get(position).getActivityName());
        holder.likes.setText(dataList.get(position).getLikesInfo().getLikesCount()+"");
        holder.data.setText(dataList.get(position).getWordContent());
        LoadImageUtil.getInstance().loadIconImage(holder.img, dataList.get(position).
                getUserInfo().getUserLogoUrl(), true);
        if(dataList.get(position).getFileIdList() != null && dataList.get(position).getFileIdList().size() > 0){
            holder.imgsLayout.setVisibility(View.VISIBLE);
            List<String> imgList = dataList.get(position).getFileIdList();
            switch (imgList.size()){
                default:
                    holder.imgs_3_num.setText("+"+(imgList.size()-3));
                case 3:
                    LoadImageUtil.getInstance().loadImage(holder.imgs_3, imgList.get(2));
                case 2:
                    LoadImageUtil.getInstance().loadImage(holder.imgs_2, imgList.get(1));
                case 1:
                    LoadImageUtil.getInstance().loadImage(holder.imgs_1, imgList.get(0));
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
        if(dataList.get(position).getLikesInfo().getStatus() == 0){
            holder.zanImg.setBackgroundResource(R.drawable.btn_ztxq_dz_normal);
        }else{
            holder.zanImg.setBackgroundResource(R.drawable.btn_ztxq_dz_selected);
        }
        holder.zanLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int type = 0;
                if(dataList.get(position).getLikesInfo().getStatus() == 1){
                    type = 1;
                }
                if(listener != null){
                    listener.requestZannet(type, position);
                }
            }
        });
        return v;
    }

    private void toLookActivity(int position, int index){
        List<String> imgs = dataList.get(position).getFileIdList();
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
        private TextView name;
        private TextView time;
        private TextView likes;
        private TextView data;
        private ImageView img;
        private ImageView zanImg;
        private LinearLayout zanLayout;
        private LinearLayout imgsLayout;
        private ImageView imgs_1;
        private ImageView imgs_2;
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
            imgsLayout = (LinearLayout) v.findViewById(R.id.report_imgs_layout);
            imgs_1 = (ImageView) v.findViewById(R.id.report_imgs_1);
            imgs_2 = (ImageView) v.findViewById(R.id.report_imgs_2);
            imgs_3 = (ImageView) v.findViewById(R.id.report_imgs_3);
            imgs_3_num = (TextView) v.findViewById(R.id.report_imgs_3_Num);
        }

    }
}
