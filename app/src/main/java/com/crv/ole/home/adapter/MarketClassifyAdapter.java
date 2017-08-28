package com.crv.ole.home.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.home.model.ClassifyInfoResult;
import com.crv.ole.utils.LoadImageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fairy on 2017/7/28.
 * 超市 - 搜索及分类页适配
 */

public class MarketClassifyAdapter extends BaseAdapter {
    private Context context;
    private List<ClassifyInfoResult.RETURNDATABean> dataList;
    private int screenWidth;

    public MarketClassifyAdapter(Context context, List<ClassifyInfoResult.RETURNDATABean>
            messageDataList, int screenWidth) {
        this.context = context;
        this.dataList = messageDataList;
        this.screenWidth = screenWidth;
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        ViewHolder viewHolder;
        if (v == null) {
            v = LayoutInflater.from(context).inflate(R.layout.market_classify_item_layout, parent, false);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }

        ClassifyInfoResult.RETURNDATABean bean = dataList.get(position);
        LoadImageUtil.getInstance().loadImage(viewHolder.img, bean.getSort_i().getImgUrl(),
                R.drawable.home_banner_1, ImageView.ScaleType.CENTER_CROP);
        viewHolder.text.setText(bean.getSort_t());

        //  设置底部文字
        if (bean.getSort_list() != null && bean.getSort_list().size() > 0) {
            viewHolder.layout.removeAllViews();

            switch (bean.getSort_list().size() % 4) {
                case 1:
                    bean.getSort_list().add(new ClassifyInfoResult.RETURNDATABean.SortListBean());
                case 2:
                    bean.getSort_list().add(new ClassifyInfoResult.RETURNDATABean.SortListBean());
                case 3:
                    bean.getSort_list().add(new ClassifyInfoResult.RETURNDATABean.SortListBean());
            }

            List<TextView> items = new ArrayList<>();
            for (int i = 0; i < bean.getSort_list().size(); i++) {
                ClassifyInfoResult.RETURNDATABean.SortListBean childBean = bean.getSort_list().get(i);
                TextView item = new TextView(context);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.setMargins(0, 0, 0, 30);
                lp.weight = 1;
                item.setLayoutParams(lp);
                item.setTextSize(15);
                item.setTextColor(context.getResources().getColor(R.color.c_666666));
                item.setGravity(Gravity.CENTER);
                item.setText(TextUtils.isEmpty(childBean.getContent()) ? "" : childBean.getContent());
                item.setMaxLines(1);
                items.add(item);
                if (items.size() >= 4) {
                    LinearLayout layout = new LinearLayout(context);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layout.setLayoutParams(layoutParams);
                    layout.setOrientation(LinearLayout.HORIZONTAL);
                    for (TextView tv : items) {
                        layout.addView(tv);
                    }
                    viewHolder.layout.addView(layout);
                    items.clear();
                }
            }
        }
        return v;
    }


    private static class ViewHolder {
        private ImageView img;
        private TextView text;
        private LinearLayout layout;

        public ViewHolder(View v) {
            img = (ImageView) v.findViewById(R.id.classify_img);
            text = (TextView) v.findViewById(R.id.classify_tv);
            layout = (LinearLayout) v.findViewById(R.id.classify_title_layout);
        }

    }


}
