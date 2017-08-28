package com.crv.ole.classfiy.adapter;

import android.graphics.Paint;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crv.ole.BaseApplication;
import com.crv.ole.R;
import com.crv.ole.classfiy.callback.OnItemImgClickListener;
import com.crv.ole.shopping.model.Product;
import com.crv.ole.utils.LoadImageUtil;
import com.crv.sdk.utils.TextUtil;
import com.vondear.rxtools.RxDataUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品分类详情Adapter
 * Created by zhangbo on 2017/8/5.
 */

public class ProductClassfiyDetilAdapter extends RecyclerView.Adapter<ProductClassfiyDetilAdapter.ProductViewHolder> {


    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_NORMAL = 1;  //正常

    private List<Product> mList = new ArrayList<>();
    private OnItemImgClickListener mListener;

    private View mHeaderView;


    private Map<Integer, Boolean> map = new HashMap<>();

    public ProductClassfiyDetilAdapter() {

    }

    private void initMap() {
        for (int i = 0; i < mList.size(); i++) {
            map.put(i, false);
        }
    }


    public void setDate(List<Product> mList) {
        this.mList = mList;
        notifyDataSetChanged();

    }

    public void addDate(List<Product> mList) {
        this.mList.addAll(mList);
        notifyDataSetChanged();
    }

    public void setListener(OnItemImgClickListener listener) {
        this.mListener = listener;
    }


    //HeaderView的get和set函数
    public View getHeaderView() {
        return mHeaderView;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }


    /**
     * 重写这个方法，很重要，是加入Header和Footer的关键，我们通过判断item的类型，从而绑定不同的view    *
     */
    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) {
            return TYPE_NORMAL;
        }
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_NORMAL;
    }


    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new ProductViewHolder(mHeaderView);
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_classfiy_detil, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {

        if (getItemViewType(position) == TYPE_NORMAL) {
            holder.tx_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);// TextView文字中间加横线:
            Product product = mList.get(position - 1);
            LoadImageUtil.getInstance().loadImage(holder.product_logo, product.getProductImage());
            int sellAbleCount = RxDataUtils.stringToInt(product.getSellAbleCount());
            holder.product_state.setVisibility(sellAbleCount > 0 ? View.GONE : View.VISIBLE);
            holder.product_title.setText(product.getName());

            //TODO 有待逻辑确认
            if (BaseApplication.getInstance().getUserCenter() != null) {//登录状态
                String type = BaseApplication.getInstance().getUserCenter().getMemberlevel();
                if ("common".equals(type)) {//普通会员
                    holder.tx_v_price.setText("暂无价格".equals(product.getMarketPrice()) ? product.getMarketPrice() : "¥" + product.getMarketPrice());//直接显示市场价
                    holder.tx_old_price.setVisibility(View.GONE);
                    holder.ic_v1.setVisibility(View.GONE);

                } else if ("ole".equals(type)) {//ole会员
                    if (TextUtil.isEmpty(product.getMemberPrice())) {//没有会员价
                        holder.tx_v_price.setText("暂无价格".equals(product.getMarketPrice()) ? product.getMarketPrice() : "¥" + product.getMarketPrice());//直接显示市场价
                        holder.tx_old_price.setVisibility(View.GONE);
                    } else {//有会员价
                        holder.tx_v_price.setText("暂无价格".equals(product.getMemberPrice()) ? product.getMemberPrice() : "¥" + product.getMemberPrice());//显示会员价
                        holder.tx_old_price.setVisibility(View.VISIBLE);
                        holder.tx_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);// TextView文字中间加横线:
                        holder.tx_old_price.setText("暂无价格".equals(product.getMarketPrice()) ? product.getMarketPrice() : "¥" + product.getMarketPrice());//市场价
                    }
                    holder.ic_v1.setVisibility(View.GONE);
                } else if ("hrtv1".equals(type)) {//华润通V1
                    if (TextUtil.isEmpty(product.getMemberPrice())) {//没有会员价
                        holder.tx_v_price.setText("暂无价格".equals(product.getMarketPrice()) ? product.getMarketPrice() : "¥" + product.getMarketPrice());//直接显示市场价
                        holder.tx_old_price.setVisibility(View.GONE);
                    } else {//有会员价
                        holder.tx_v_price.setText("暂无价格".equals(product.getMemberPrice()) ? product.getMemberPrice() : "¥" + product.getMemberPrice());//显示会员价
                        holder.tx_old_price.setVisibility(View.VISIBLE);
                        holder.tx_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);// TextView文字中间加横线:
                        holder.tx_old_price.setText("暂无价格".equals(product.getMarketPrice()) ? product.getMarketPrice() : "¥" + product.getMarketPrice());//市场价
                    }
                    holder.ic_v1.setVisibility(View.VISIBLE);
                    holder.ic_v1.setBackgroundResource(R.drawable.ic_v1);
                } else if ("hrtv2".equals(type)) {//华润通V2
                    if (TextUtil.isEmpty(product.getMemberPrice())) {//没有会员价
                        holder.tx_v_price.setText("暂无价格".equals(product.getMarketPrice()) ? product.getMarketPrice() : "¥" + product.getMarketPrice());//直接显示市场价
                        holder.tx_old_price.setVisibility(View.GONE);
                    } else {//有会员价
                        holder.tx_v_price.setText("暂无价格".equals(product.getMemberPrice()) ? product.getMemberPrice() : "¥" + product.getMemberPrice());//显示会员价
                        holder.tx_old_price.setVisibility(View.VISIBLE);
                        holder.tx_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);// TextView文字中间加横线:
                        holder.tx_old_price.setText("暂无价格".equals(product.getMarketPrice()) ? product.getMarketPrice() : "¥" + product.getMarketPrice());//市场价
                    }
                    holder.ic_v1.setVisibility(View.VISIBLE);
                    holder.ic_v1.setBackgroundResource(R.drawable.ic_v2);
                } else if ("hrtv3".equals(type)) {//华润通V3
                    if (TextUtil.isEmpty(product.getMemberPrice())) {//没有会员价
                        holder.tx_v_price.setText("暂无价格".equals(product.getMarketPrice()) ? product.getMarketPrice() : "¥" + product.getMarketPrice());//直接显示市场价
                        holder.tx_old_price.setVisibility(View.GONE);
                    } else {//有会员价
                        holder.tx_v_price.setText("暂无价格".equals(product.getMemberPrice()) ? product.getMemberPrice() : "¥" + product.getMemberPrice());//显示会员价
                        holder.tx_old_price.setVisibility(View.VISIBLE);
                        holder.tx_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);// TextView文字中间加横线:
                        holder.tx_old_price.setText("暂无价格".equals(product.getMarketPrice()) ? product.getMarketPrice() : "¥" + product.getMarketPrice());//市场价
                    }
                    holder.ic_v1.setVisibility(View.VISIBLE);
                    holder.ic_v1.setBackgroundResource(R.drawable.ic_v3);
                }
            } else {//没有登录
                holder.tx_v_price.setText("暂无价格".equals(product.getMarketPrice()) ? product.getMarketPrice() : "¥" + product.getMarketPrice());//直接显示市场价
                holder.tx_old_price.setVisibility(View.GONE);
                holder.ic_v1.setVisibility(View.GONE);

            }
            //holder.btn_shopp_car.setBackgroundResource(map.get(position) ? R.drawable.ic_shoppingcart_pressed : R.drawable.ic_shoppingcart_normal);
            holder.btn_shopp_car.setBackgroundResource(R.drawable.ic_shoppingcart_normal);

            holder.itemView.setTag(position - 1);
        } else if (getItemViewType(position) == TYPE_HEADER) {
            return;
        } else {
            return;
        }

    }

    @Override
    public int getItemCount() {
        if (mHeaderView == null) {
            return mList == null ? 0 : mList.size();
        } else if (mHeaderView != null) {
            return mList == null ? 0 : mList.size() + 1;
        }

        return mList == null ? 0 : mList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        private ImageView product_logo;

        private ImageView product_state;

        private TextView product_title;

        private TextView tx_v_price;

        private ImageView ic_v1;

        private TextView tx_old_price;

        private ImageView btn_shopp_car;

        public ProductViewHolder(View itemView) {
            super(itemView);
            //如果是headerview或者是footerview,直接返回
            if (itemView == mHeaderView) {
                return;
            }
            product_logo = (ImageView) itemView.findViewById(R.id.product_logo);
            product_state = (ImageView) itemView.findViewById(R.id.product_state);
            product_title = (TextView) itemView.findViewById(R.id.product_title);
            tx_v_price = (TextView) itemView.findViewById(R.id.tx_v_price);
            ic_v1 = (ImageView) itemView.findViewById(R.id.ic_v1);
            tx_old_price = (TextView) itemView.findViewById(R.id.tx_old_price);
            btn_shopp_car = (ImageView) itemView.findViewById(R.id.btn_shopp_car);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.onItemClick(mList.get(getLayoutPosition() - 1), getLayoutPosition() - 1);
                    }
                }
            });
            btn_shopp_car.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.OnAddToShoppingCar(mList.get(getLayoutPosition() - 1), getLayoutPosition() - 1);
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_HEADER
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }
}
