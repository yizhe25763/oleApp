package com.crv.ole.shopping;

import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crv.ole.BaseApplication;
import com.crv.ole.R;
import com.crv.ole.base.BaseItemTouchListener;
import com.crv.ole.shopping.model.Product;
import com.crv.ole.utils.LoadImageUtil;
import com.vondear.rxtools.RxDataUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lihongshi on 2017/7/31.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {

    private List<Product> mList;
    private OnItemTouchListener mListener;

    public ProductListAdapter(List list) {
        this.mList = list == null ? new ArrayList<>() : list;
    }

    public void setListener(OnItemTouchListener listener) {
        this.mListener = listener;
    }

    public void clearAllItem() {
        this.mList.clear();
        notifyDataSetChanged();
    }

    public void addAllItem(List list) {
        this.mList.addAll(list);
        notifyDataSetChanged();
    }

    public void setAllItem(List list) {
        this.mList.clear();
        addAllItem(list);
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        holder.textView02.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);// TextView文字中间加横线:

        Product product = mList.get(position);
        LoadImageUtil.getInstance().loadImage(holder.product_logo, product.getProductImage());

        int sellAbleCount = RxDataUtils.stringToInt(product.getSellAbleCount());
        holder.product_state.setVisibility(sellAbleCount > 0 ? View.GONE : View.VISIBLE);
        holder.product_title.setText(product.getName());
        holder.textView01.setText(product.getMarketPrice());

        if (BaseApplication.getInstance().getUserCenter() != null) {
            holder.ic_level.setVisibility(View.VISIBLE);
            String type = BaseApplication.getInstance().getUserCenter().getMemberlevel();
            if ("common".equals(type)) {

            } else if ("ole".equals(type)) {

            } else if ("hrtv1".equals(type)) {
                holder.ic_level.setBackgroundResource(R.drawable.ic_v1);
            } else if ("hrtv2".equals(type)) {
                holder.ic_level.setBackgroundResource(R.drawable.ic_v2);
            } else if ("hrtv3".equals(type)) {
                holder.ic_level.setBackgroundResource(R.drawable.ic_v3);
            }
        } else {
            holder.ic_level.setVisibility(View.INVISIBLE);
        }
        holder.textView02.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);// TextView文字中间加横线:
        holder.textView02.setText(product.getMemberPrice());
        holder.itemView.setTag(product);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public interface OnItemTouchListener<T> extends BaseItemTouchListener<T> {
        void onAddShoppingCart(T item, int position);
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        private ImageView product_logo;
        private ImageView product_state;
        private TextView product_title;
        private TextView textView01;
        private ImageView ic_level;
        private TextView textView02;
        private ImageView btn_gwc;

        public ProductViewHolder(View itemView) {
            super(itemView);
            product_logo = (ImageView) itemView.findViewById(R.id.product_logo);
            product_state = (ImageView) itemView.findViewById(R.id.product_state);
            product_title = (TextView) itemView.findViewById(R.id.product_title);
            textView01 = (TextView) itemView.findViewById(R.id.textView01);
            ic_level = (ImageView) itemView.findViewById(R.id.ic_v1);
            textView02 = (TextView) itemView.findViewById(R.id.textView02);
            btn_gwc = (ImageView) itemView.findViewById(R.id.btn_gwc);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.onItemClick(itemView.getTag(), getLayoutPosition());
                    }
                }
            });
            btn_gwc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.onAddShoppingCart(itemView.getTag(), getLayoutPosition());
                    }
                }
            });
        }
    }
}
