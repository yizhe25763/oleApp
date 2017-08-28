package com.crv.ole.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.crv.ole.R;
import com.crv.ole.BaseApplication;
import com.crv.ole.home.model.ServiceBean;
import com.crv.ole.utils.ImageUtils;
import com.crv.ole.utils.glide.GlideCircleTransform;
import com.crv.sdk.utils.DisplayUtil;
import com.xdandroid.simplerecyclerview.SingleViewTypeAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * 首页
 * Created by Administrator on 2016/12/21.
 */

public abstract class HomeAdapter extends SingleViewTypeAdapter<ServiceBean>
{

    private List<ServiceBean> data;
    Context mContext;
    int width;
    int height;
    int header_with_height;

    public HomeAdapter(Context context)
    {
        mContext = context;
        width = BaseApplication.getDeviceWidth();
        height = DisplayUtil.dip2px(mContext, 185f);
        header_with_height = DisplayUtil.dip2px(mContext, 36f);
    }


    @Override
    protected RecyclerView.ViewHolder onViewHolderCreate(List list, ViewGroup parent)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.itemView.setTag(holder);
        return holder;
    }

    @Override
    protected void onViewHolderBind(List<ServiceBean> list, RecyclerView.ViewHolder vh, int position)
    {
        ViewHolder holder = (ViewHolder) vh;
        ServiceBean serviceBean = list.get(position);
        data = new ArrayList<>(list);

        holder.tvCategory.setText(serviceBean.getCategoryName());
        holder.tvName.setText(serviceBean.getNickName());
        holder.tvZmxy.setText(serviceBean.getSesamePoint() + "分");
        holder.tvDistance.setText(serviceBean.getDistance() + "Km");
        holder.tvGrade.setText(serviceBean.getGradeName());
        holder.tvTitle.setText(serviceBean.getTitle());
        holder.tvPrice.setText(serviceBean.getOfflinePrice() + "元/" + serviceBean.getUnit());
        holder.tvDesc.setText(serviceBean.getIntroductions());
        holder.tvOrderCount.setText(serviceBean.getTurnover() + "约单");
        holder.tvVistors.setText(serviceBean.getVisitor() + "访客");
        holder.tvSupport.setText(serviceBean.getAccept() + "点赞");

        String imgPath = serviceBean.getImage();
        if(TextUtils.isEmpty(imgPath))
        {
            holder.imgHeader.setImageResource(R.drawable.add_photo);
        }
        else
        {
            Glide.with(mContext).load(ImageUtils.
                    getImageUrl(imgPath))
                    .placeholder(R.drawable.demo_user_icon)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .skipMemoryCache(true)
                    .override(header_with_height, header_with_height)
                    .transform(new GlideCircleTransform(mContext)).into(holder.imgHeader);
        }
        holder.imgSmrz.setVisibility(View.GONE);
        holder.imgQyrz.setVisibility(View.GONE);

        holder.tvAge.setText(serviceBean.getAge() + "岁");
        if (serviceBean.getServiceImagesList() != null && !serviceBean.getServiceImagesList().isEmpty())
        {
            String image = serviceBean.getServiceImagesList().get(0).getImageUrl();
            if (!TextUtils.isEmpty(image))
            {
                Glide
                        .with(mContext)
                        .load(ImageUtils.getImageUrl(image))
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .skipMemoryCache(true)
                        .placeholder(R.drawable.demo_theme_default)
                        .override(width, height)
                        .crossFade()
                        .into(holder.imgBanner);
            }
            else{
                holder.imgBanner.setImageResource(R.drawable.demo_theme_default);
            }
        }

        holder.tvAttention.setText(serviceBean.getRelation() == 0 ? "+关注" : "取消关注");
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private RelativeLayout layoutContainer;
        private ImageView imgHeader;
        private TextView tvName;
        private TextView tvCategory;
        private ImageView imgSex;
        private ImageView imgSmrz;
        private ImageView imgQyrz;
        private TextView tvDistance;
        private TextView tvGrade;
        private ImageView imgZmxy;
        private TextView tvZmxy;
        private TextView tvOrderCount;
        private TextView tvVistors;
        private TextView tvAttention;
        private ImageView imgBanner;
        private TextView tvTitle;
        private TextView tvPrice;
        private TextView tvDesc;
        private TextView tvAge;
        private TextView tvSupport;


        public ViewHolder(View itemView)
        {
            super(itemView);
            layoutContainer = (RelativeLayout) itemView.findViewById(R.id.layoutContainer);
            imgHeader = (ImageView) itemView.findViewById(R.id.imgHeader);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            imgSmrz = (ImageView) itemView.findViewById(R.id.imgSmrz);
            imgQyrz = (ImageView) itemView.findViewById(R.id.imgQyrz);
            imgSex = (ImageView) itemView.findViewById(R.id.imgSex);
            tvDistance = (TextView) itemView.findViewById(R.id.tvDistance);
            tvGrade = (TextView) itemView.findViewById(R.id.tvGrade);
            imgZmxy = (ImageView) itemView.findViewById(R.id.imgZmxy);
            tvZmxy = (TextView) itemView.findViewById(R.id.tvZmxy);
            tvOrderCount = (TextView) itemView.findViewById(R.id.tvOrderCount);
            tvVistors = (TextView) itemView.findViewById(R.id.tvVistors);
            tvAttention = (TextView) itemView.findViewById(R.id.tvAttention);
            imgBanner = (ImageView) itemView.findViewById(R.id.imgBanner);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            tvDesc = (TextView) itemView.findViewById(R.id.tvDesc);
            tvAge = (TextView) itemView.findViewById(R.id.tvAge);
            tvSupport = (TextView) itemView.findViewById(R.id.tvSupport);
            tvCategory = (TextView) itemView.findViewById(R.id.tvCategory);

        }
    }
}