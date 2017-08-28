package com.crv.ole.shopping.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.shopping.model.CartCommonPromoRule;
import com.crv.ole.shopping.model.CartResponseData;
import com.crv.ole.shopping.model.CartSectionItem;
import com.crv.ole.utils.LoadImageUtil;
import com.crv.sdk.utils.DateUtil;
import com.crv.sdk.utils.TextUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lihongshi on 2017/8/1.
 * 购物车列表适配器
 */

public class ShoppingCartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = ShoppingCartAdapter.class.getSimpleName();
    public static final int VIEW_TYPE_SECTION = 0;
    public static final int VIEW_TYPE_NORMAL = 1;
    public static final int VIEW_TYPE_RULE = 2;
    public static final int VIEW_TYPE_DIVIDER = 3;

    private boolean isEditMode = false;

    private List mList;
    private OnCartListener mListener;

    public interface OnCartListener {
        void onUpdateView();//item相关信息变更

        void onItemCheck(Object object);

        void onSectionCheck(String cartType, boolean check);

        void onRuleClick(Object object);
    }

    public void setListener(OnCartListener listener) {
        this.mListener = listener;
    }

    private void callBackUpdateView() {
        if (mListener != null) {
            mListener.onUpdateView();
        }
    }

    private void callBackItemCheck(Object object) {
        if (mListener != null) {
            mListener.onItemCheck(object);
        }
    }

    private void callBackSectionCheck(String cartType, boolean check) {
        if (mListener != null) {
            mListener.onSectionCheck(cartType, check);
        }
    }

    private void callBackRuleClick(Object object) {
        if (mListener != null) {
            mListener.onRuleClick(object);
        }
    }

    public ShoppingCartAdapter(List list) {
        this.mList = list == null ? new ArrayList() : list;
    }

    public void setAllItem(List list) {
        this.mList.clear();
        this.mList.addAll(list == null ? new ArrayList() : list);
        notifyDataSetChanged();
    }

    public void delItems(List<CartResponseData.BuyItems> productids) {
        int length = mList.size();
        for (CartResponseData.BuyItems item : productids) {
            for (int i = length - 1; i >= 0; i--) {
                if (i < mList.size()) {
                    Object object = mList.get(i);
                    if (CartResponseData.BuyItems.class.equals(object.getClass())) {
                        CartResponseData.BuyItems buyItems = (CartResponseData.BuyItems) object;
                        if (buyItems.getProductId().equals(item.getProductId())) {
                            mList.remove(i);
                        }
                    }
                }

            }
        }
        notifyDataSetChanged();
    }

    public List getAllItem() {
        return mList;
    }

    /**
     * 全选
     */
    public void setAllCheck(boolean check) {
        for (Object object : mList) {
            if (CartResponseData.BuyItems.class.equals(object.getClass())) {
                CartResponseData.BuyItems buyItems = (CartResponseData.BuyItems) object;
                buyItems.setChecked(check);
            }

            if (CartSectionItem.class.equals(object.getClass())) {
                CartSectionItem cartSectionItem = (CartSectionItem) object;
                cartSectionItem.setCheck(check);
            }
        }
        notifyDataSetChanged();
    }

    public void updateEditMode(boolean flag) {
        isEditMode = flag;
    }


    @Override
    public int getItemViewType(int position) {
        if (CartSectionItem.class.equals(mList.get(position).getClass())) {
            return VIEW_TYPE_SECTION;
        }
        if (CartResponseData.BuyItems.class.equals(mList.get(position).getClass())) {
            return VIEW_TYPE_NORMAL;
        }
        if (CartCommonPromoRule.class.equals(mList.get(position).getClass())) {
            return VIEW_TYPE_RULE;
        }
        return VIEW_TYPE_DIVIDER;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SECTION) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_section, parent, false);
            return new CartSectionViewHolder(view);
        } else if (viewType == VIEW_TYPE_NORMAL) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_normal, parent, false);
            return new CartViewHolder(view);
        } else if (viewType == VIEW_TYPE_RULE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_rule, parent, false);
            return new CartRuleViewHolder(view);
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_divider, parent, false);
        return new CartDividerViewHolder(view);

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CartSectionViewHolder) {
            CartSectionItem cartSectionItem = (CartSectionItem) mList.get(position);
            CartSectionViewHolder sectionViewHolder = (CartSectionViewHolder) holder;
            sectionViewHolder.sectionCheckbox.setImageResource(cartSectionItem.isCheck() ?
                    R.drawable.checkbox_check : R.drawable.checkbox_nor);
            sectionViewHolder.sectionTitle.setText(cartSectionItem.getText());
            sectionViewHolder.itemView.setTag(cartSectionItem);

            sectionViewHolder.sectionCheckbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean check = !cartSectionItem.isCheck();
                    Log.e(TAG, "position:" + position + " check:" + check);
                    cartSectionItem.setCheck(check);
                    int pos = position;
                    while (true) {
                        pos++;
                        if (pos < mList.size()
                                && CartResponseData.BuyItems.class.equals(mList.get(pos).getClass())
                                && isBuyItemCanOp((CartResponseData.BuyItems) mList.get(pos))) {
                            CartResponseData.BuyItems buyItems = (CartResponseData.BuyItems) mList.get(pos);
                            buyItems.setChecked(check);
                        }
                        if (pos >= mList.size() || CartSectionItem.class.equals(mList.get(pos).getClass())) {
                            break;
                        }
                    }
                    notifyDataSetChanged();
                    callBackUpdateView();
                    callBackSectionCheck(cartSectionItem.getCartType(), check);
                }
            });

            return;
        }
        if (holder instanceof CartViewHolder) {
            CartResponseData.BuyItems buyItems = (CartResponseData.BuyItems) mList.get(position);
            boolean check = buyItems.getChecked();
            String icon = buyItems.getIcon();
            String productName = buyItems.getProductName();
            String unitPrice = buyItems.getUnitPrice();
            String number = buyItems.getNumber();
            String diffPrice = buyItems.getDiffPrice();

            CartViewHolder cartViewHolder = (CartViewHolder) holder;
            cartViewHolder.checkBox.setImageResource(check ? R.drawable.checkbox_check : R.drawable.checkbox_nor);
            LoadImageUtil.getInstance().loadImage(cartViewHolder.ivProduceImage, icon, true);
            cartViewHolder.tvTitle.setText(productName);
            cartViewHolder.tvPrice.setText("¥" + unitPrice);
            cartViewHolder.tvCount.setText(number);
            cartViewHolder.tvYiJiang.setText("0".equals(diffPrice) ? "" : String.format("已降￥%S", diffPrice));
            cartViewHolder.itemView.setTag(buyItems);
            if (!isBuyItemCanOp(buyItems)) {
                cartViewHolder.rootView.setEnabled(false);
            } else {
                cartViewHolder.rootView.setEnabled(true);
            }
            cartViewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!cartViewHolder.rootView.isEnabled()) {
                        return;
                    }
                    buyItems.setChecked(!buyItems.getChecked());
                    notifyDataSetChanged();
                    callBackUpdateView();
                    callBackItemCheck(buyItems);
                }
            });

            cartViewHolder.ivAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!cartViewHolder.rootView.isEnabled()) {
                        return;
                    }
                    int num = Integer.parseInt(buyItems.getNumber());
                    num++;
                    buyItems.setNumber(String.valueOf(num));
                    notifyDataSetChanged();
                    callBackUpdateView();
                }
            });

            cartViewHolder.ivDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!cartViewHolder.rootView.isEnabled()) {
                        return;
                    }
                    int num = Integer.parseInt(buyItems.getNumber());
                    num--;
                    if (num > 0) {
                        buyItems.setNumber(String.valueOf(num));
                        notifyDataSetChanged();
                        callBackUpdateView();
                    }
                }
            });
            return;
        }

        if (holder instanceof CartRuleViewHolder) {
            CartRuleViewHolder cartRuleViewHolder = (CartRuleViewHolder) holder;
            CartCommonPromoRule cartCommonPromoRule = (CartCommonPromoRule) mList.get(position);
            String rule = cartCommonPromoRule.getText();
            cartRuleViewHolder.tvCartRule.setText(rule);
            cartRuleViewHolder.itemView.setTag(cartCommonPromoRule);
            cartRuleViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callBackRuleClick(cartCommonPromoRule);
                }
            });
            return;
        }

    }

    /**
     * 商品是否可操作
     *
     * @return
     */
    private boolean isBuyItemCanOp(CartResponseData.BuyItems buyItems) {
        return !(!TextUtil.isEmpty(buyItems.getDepositBeginTime())
                && !TextUtil.isEmpty(buyItems.getDepositEndTime())
                && !DateUtil.whetherScope(DateUtil.getNow(DateUtil.FORMAT_LONG), buyItems.getDepositBeginTime(), buyItems.getDepositEndTime()));
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rootView;
        private ImageView checkBox;
        private ImageView ivProduceImage;
        private TextView tvTitle;
        private TextView tvPrice;
        private ImageView ivAdd;
        private TextView tvCount;
        private ImageView ivDel;
        private TextView tvYiJiang;

        CartViewHolder(View itemView) {
            super(itemView);
            rootView = (RelativeLayout) itemView.findViewById(R.id.cart_root);
            checkBox = (ImageView) itemView.findViewById(R.id.checkBox);
            ivProduceImage = (ImageView) itemView.findViewById(R.id.ivProduceImage);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            ivAdd = (ImageView) itemView.findViewById(R.id.ivAdd);
            tvCount = (TextView) itemView.findViewById(R.id.tvCount);
            ivDel = (ImageView) itemView.findViewById(R.id.ivDel);
            tvYiJiang = (TextView) itemView.findViewById(R.id.tvYiJiang);
        }
    }

    public static class CartRuleViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCartRule;

        CartRuleViewHolder(View itemView) {
            super(itemView);
            tvCartRule = (TextView) itemView.findViewById(R.id.tvCartRule);
        }
    }

    public static class CartDividerViewHolder extends RecyclerView.ViewHolder {
        CartDividerViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class CartSectionViewHolder extends RecyclerView.ViewHolder {
        private ImageView sectionCheckbox;
        private TextView sectionTitle;

        CartSectionViewHolder(View itemView) {
            super(itemView);
            sectionCheckbox = (ImageView) itemView.findViewById(R.id.section_checkbox);
            sectionTitle = (TextView) itemView.findViewById(R.id.section_title);
        }
    }

}
