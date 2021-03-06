package com.crv.ole.personalcenter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.crv.ole.R;
import com.crv.ole.base.BaseItemTouchListener;
import com.lzy.imagepicker.bean.ImageItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangbo on 2017/8/19.
 */

public class EvaluaPhotoAdapter extends RecyclerView.Adapter<EvaluaPhotoAdapter.TrialReportViewHolder> {
    private ArrayList<ImageItem> mList = new ArrayList<>();
    private BaseItemTouchListener mListener;
    private Context mContext;

    public EvaluaPhotoAdapter(Context context) {
        mContext = context;
    }

    public void setBaseItemTouchListener(BaseItemTouchListener listener) {
        this.mListener = listener;
    }

    public ArrayList<ImageItem> getAllItem() {
        return mList;
    }

    public void setAllItem(List list) {
        this.mList.clear();
        this.mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addItem(ImageItem string) {
        this.mList.add(string);
        //  notifyItemInserted(mList.size() + 1);
        notifyDataSetChanged();
    }

    public void addItems(List list) {
        this.mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public TrialReportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trial_report, parent, false);
        return new TrialReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrialReportViewHolder holder, int position) {
        if (position == mList.size()) {
            holder.imageView.setImageResource(R.drawable.img_evlua_add_selector);
        } else {
            File file = new File(mList.get(position).path);
            Glide.with(mContext).load(file)
                    .placeholder(R.mipmap.btn_ddplzj_normal).error(R.mipmap.btn_ddplzj_normal)
                    .into(holder.imageView);
        }


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClick(position == mList.size() ? null : mList.get(position), position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mList == null || mList.size() <= 0) ? 1 : (1 + mList.size());
    }


    public static class TrialReportViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public TrialReportViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
