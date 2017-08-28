package com.crv.ole.trial.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.base.PicPreviewActivity;
import com.crv.ole.shopping.model.TrialReportInfoData;
import com.crv.ole.trial.callback.OnReportItemClickListener;
import com.crv.ole.utils.LoadImageUtil;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImagePreviewActivity;

import java.util.ArrayList;
import java.util.List;

import me.codeboy.android.aligntextview.AlignTextView;

/**
 * Created by zhangbo on 2017/8/17.
 */

public class TrialInfoReportAdapter extends BaseAdapter {

    private Context context;

    private List<TrialReportInfoData.RETURNDATABean.ListBean> list;

    private OnReportItemClickListener listener;

    public TrialInfoReportAdapter(Context context, List<TrialReportInfoData.RETURNDATABean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list == null ? null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.traial_report_ietm_layout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        LoadImageUtil.getInstance().loadImage(viewHolder.im_head, list.get(position).getUserInfo().getUserLogoUrl());
        viewHolder.tx_zan_count.setText(list.get(position).getLikesInfo().getLikesCount() + "");
        viewHolder.tx_name.setText(list.get(position).getUserInfo().getNickName());
        //viewHolder.tx_city.setText( list.get(position).getCity());
        viewHolder.tx_desc.setText(list.get(position).getOneSentence());
        viewHolder.tx_content.setText(list.get(position).getWordContent());

        LoadImageUtil.getInstance().loadImage(viewHolder.im_one, list.get(position).getFileIdList().get(0));
        LoadImageUtil.getInstance().loadImage(viewHolder.im_two, list.get(position).getFileIdList().get(1));
        LoadImageUtil.getInstance().loadImage(viewHolder.im_three, list.get(position).getFileIdList().get(2));
        if (list.get(position).getFileIdList().size() - 3 == 0) {
            viewHolder.tx_more_img.setVisibility(View.GONE);
        } else {
            viewHolder.tx_more_img.setVisibility(View.VISIBLE);
            viewHolder.tx_more_img.setText(list.get(position).getFileIdList().size() - 3);
        }
        if (position == 0) {
            viewHolder.line.setVisibility(View.VISIBLE);
        } else {
            viewHolder.line.setVisibility(View.GONE);
        }

        if (list.get(position).getLikesInfo().getStatus() == 0) {
            viewHolder.im_zan.setBackgroundResource(R.drawable.btn_ztxq_dz_normal);
        } else {
            viewHolder.im_zan.setBackgroundResource(R.drawable.btn_ztxq_dz_selected);
        }

        viewHolder.im_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.OnReportZanItemClick(list.get(position), position);
                }
            }
        });
        viewHolder.im_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLookActivity(position, 0);
            }
        });
        viewHolder.im_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLookActivity(position, 1);
            }
        });
        viewHolder.im_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLookActivity(position, 2);
            }
        });
        return convertView;
    }


    private class ViewHolder {
        private ImageView im_head;
        private ImageView im_zan;
        private TextView tx_zan_count;
        private TextView tx_name;
        private TextView tx_city;
        private TextView tx_desc;
        private AlignTextView tx_content;
        private ImageView im_one;
        private ImageView im_two;
        private ImageView im_three;
        private TextView tx_more_img;
        private View line;

        public ViewHolder(View view) {
            im_head = (ImageView) view.findViewById(R.id.im_head);
            im_zan = (ImageView) view.findViewById(R.id.im_zan);
            tx_zan_count = (TextView) view.findViewById(R.id.tx_zan_count);
            tx_name = (TextView) view.findViewById(R.id.tx_name);
            tx_city = (TextView) view.findViewById(R.id.tx_city);
            tx_desc = (TextView) view.findViewById(R.id.tx_desc);
            tx_content = (AlignTextView) view.findViewById(R.id.tx_content);
            im_one = (ImageView) view.findViewById(R.id.im_one);
            im_two = (ImageView) view.findViewById(R.id.im_two);
            im_three = (ImageView) view.findViewById(R.id.im_three);
            tx_more_img = (TextView) view.findViewById(R.id.tx_more_img);
            line = view.findViewById(R.id.line);
        }

    }

    public void setOnReportItemClickListener(OnReportItemClickListener listener) {
        this.listener = listener;
    }


    private void toLookActivity(int position, int index) {
        List<String> imgs = list.get(position).getFileIdList();
        ArrayList<ImageItem> photoInfos = new ArrayList<>();
        for (String img : imgs) {
            ImageItem item = new ImageItem();
            item.path = img;
            photoInfos.add(item);
        }
        Intent intent = new Intent(context, PicPreviewActivity.class);
        intent.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, photoInfos);
        intent.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, index);
        intent.putExtra(ImagePreviewActivity.IS_EDIT_MODE, false);
        intent.putExtra(PicPreviewActivity.IS_FROM_NET, true);
        context.startActivity(intent);
    }
}
