package com.crv.ole.personalcenter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.personalcenter.model.PointInfoResultBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 积分适配器
 * Created by Fairy on 2017/7/12.
 */

public class PointListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TYPE_HEADER = 0;
    private final int TYPE_NORMAL = 1;
    private View mHeaderView;

    private List<PointInfoResultBean.PointInfo> mDatas;

    public PointListAdapter(List<PointInfoResultBean.PointInfo> messageDataList) {
        this.mDatas = messageDataList == null ? new ArrayList<>() : messageDataList;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && mHeaderView != null) {
            return TYPE_HEADER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return new HeaderViewHolder(mHeaderView);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.point_item_layout, parent, false);
        return new PointViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) viewHolder;
            return;
        }
        final int pos = getRealPosition(viewHolder);
        PointViewHolder pointViewHolder = (PointViewHolder) viewHolder;
        pointViewHolder.name.setText(mDatas.get(pos).getName());
        pointViewHolder.time.setText(mDatas.get(pos).getRecorddate());

        StringBuilder jifen = new StringBuilder()
                .append(mDatas.get(pos).getDirectflag().equals("1") ? "+" : "-")
                .append(mDatas.get(pos).getPoint());
        pointViewHolder.jifen.setText(jifen);
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? mDatas.size() : mDatas.size() + 1;
    }

    public void setAllItem(List list) {
        this.mDatas.clear();
        this.mDatas.addAll(list);
        notifyDataSetChanged();
    }

    public void addAllItem(List list) {
        if (list == null) {
            return;
        }
        this.mDatas.addAll(list);
        notifyDataSetChanged();
    }

    public void clearAllItem() {
        this.mDatas.clear();
        notifyDataSetChanged();
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyDataSetChanged();
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    private static class PointViewHolder extends RecyclerView.ViewHolder {
        private TextView name, time, jifen;

        public PointViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            time = (TextView) itemView.findViewById(R.id.time);
            jifen = (TextView) itemView.findViewById(R.id.jifen);
        }
    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}
