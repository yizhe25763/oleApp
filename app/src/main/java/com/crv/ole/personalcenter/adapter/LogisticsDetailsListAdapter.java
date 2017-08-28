package com.crv.ole.personalcenter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.personalcenter.model.TrackBeanResponseData;

import java.util.List;

/**
 * Created by lihongshi on 2017/7/21.
 */

public class LogisticsDetailsListAdapter extends RecyclerView.Adapter<LogisticsDetailsListAdapter.LogisticsDetailsViewHolder> {

    private List<TrackBeanResponseData.Traces> tracesList;

    public LogisticsDetailsListAdapter(List<TrackBeanResponseData.Traces> list) {
        this.tracesList = list;
    }

    public void setAllItem(List list) {
        if (list == null) {
            return;
        }
        tracesList.clear();
        tracesList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public LogisticsDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_logistics_details, parent, false);
        return new LogisticsDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LogisticsDetailsViewHolder holder, int position) {
        TrackBeanResponseData.Traces logisticsData = tracesList.get(position);
        holder.station.setText(logisticsData.getContext());
        holder.dataTime.setText(logisticsData.getTime());
    }

    @Override
    public int getItemCount() {
        return tracesList == null ? 0 : tracesList.size();
    }


    public static class LogisticsDetailsViewHolder extends RecyclerView.ViewHolder {
        private TextView station;
        private TextView dataTime;

        public LogisticsDetailsViewHolder(View itemView) {
            super(itemView);
            station = (TextView) itemView.findViewById(R.id.item_logistics_station);
            dataTime = (TextView) itemView.findViewById(R.id.item_logistics_datetime);
        }
    }
}


