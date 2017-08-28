package com.crv.ole.personalcenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.personalcenter.activity.EditGoodsAddressActivity;
import com.crv.ole.personalcenter.activity.GoodsAddressActivity;
import com.crv.ole.personalcenter.model.AddressListData;
import com.crv.ole.utils.ToastUtil;
import com.crv.sdk.slidelistview.SlideBaseAdapter;
import com.crv.sdk.slidelistview.SlideListView.SlideMode;

import java.util.List;

/**
 * Created by yanghongjun on 17/7/11.
 */

public class SlideAdapter extends SlideBaseAdapter {
    private List<AddressListData.Address> mData;

    public SlideAdapter(Context context, List<AddressListData.Address> data) {
        super(context);
        mData = data;
    }


    @Override
    public SlideMode getSlideModeInPosition(int position) {
        return SlideMode.RIGHT;
    }

    @Override
    public int getFrontViewId(int position) {
        return R.layout.row_front_view;
    }

    @Override
    public int getLeftBackViewId(int position) {
        return R.layout.row_left_back_view;
    }

    @Override
    public int getRightBackViewId(int position) {
        return R.layout.row_right_back_view;
    }


    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = createConvertView(position);
            holder = new ViewHolder();
            holder.positon_iv = (ImageView) convertView.findViewById(R.id.positon_iv);
            holder.name_tv = (TextView) convertView.findViewById(R.id.name_tv);
            holder.mobile_tv = (TextView) convertView.findViewById(R.id.mobile_tv);
            holder.edit_bt = (Button) convertView.findViewById(R.id.edit_bt);
            holder.delete_bt = (Button) convertView.findViewById(R.id.delete_bt);
            holder.address_tv = (TextView) convertView.findViewById(R.id.address_tv);
            holder.main_edit_bt = (ImageButton) convertView.findViewById(R.id.main_edit_bt);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        AddressListData.Address address = mData.get(position);
        holder.positon_iv.setImageResource(address.isDefault() ? R.drawable.position_selected : R.drawable.positon_normal);
        holder.name_tv.setText(address.getUserName());
        holder.mobile_tv.setText(address.getPhone());
        holder.address_tv.setText(address.getAddress());

        if (holder.edit_bt != null) {
            holder.edit_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyDataSetChanged();
                    Intent intent = new Intent(mContext, EditGoodsAddressActivity.class);
                    intent.putExtra("address", address);
                    intent.putExtra("add", false);
                    BaseActivity bs = (BaseActivity) mContext;
                    bs.startActivityForResultWithAnim(intent, GoodsAddressActivity.REQUESETCODE_ADD);
                }
            });
        }

        if (holder.main_edit_bt != null) {
            holder.main_edit_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyDataSetChanged();
                    Intent intent = new Intent(mContext, EditGoodsAddressActivity.class);
                    intent.putExtra("address", address);
                    intent.putExtra("add", false);
                    BaseActivity bs = (BaseActivity) mContext;
                    bs.startActivityForResultWithAnim(intent, GoodsAddressActivity.REQUESETCODE_ADD);
                }
            });

        }

        if (holder.delete_bt != null) {
            holder.delete_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GoodsAddressActivity goodsAddressActivity = (GoodsAddressActivity) mContext;
                    goodsAddressActivity.deleteAddress(address);
                }
            });
        }


        return convertView;
    }

    class ViewHolder {
        ImageView positon_iv;
        TextView name_tv;
        TextView mobile_tv;
        TextView address_tv;
        Button edit_bt;
        Button delete_bt;
        ImageButton main_edit_bt;
    }

}
