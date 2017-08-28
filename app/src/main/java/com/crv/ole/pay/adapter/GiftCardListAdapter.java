package com.crv.ole.pay.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.net.RequestData;
import com.crv.ole.net.ServerApi;
import com.crv.ole.pay.model.CheckGiftCardPasswordData;
import com.crv.ole.pay.model.GiftCardItemData;
import com.crv.ole.pay.requestmodel.RequestCheckGiftCardPasswordModel;
import com.crv.ole.pay.zfb.Base64;
import com.crv.ole.utils.LoadImageUtil;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.crv.sdk.dialog.FragmentDialog;
import com.crv.sdk.dialog.IDialog;
import com.crv.sdk.utils.PreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 作用描述：礼品卡适配器
 * 创建者： wj_wsf
 * 创建时间： 2017/8/3
 */
public class GiftCardListAdapter extends BaseAdapter {
    private Context context;
    private IDialog mDialog;
    private List<GiftCardItemData> dataList;
    private OnConfirmAmountListener onConfirmAmountListener;

    public GiftCardListAdapter(Context context,
                               List<GiftCardItemData> messageDataList) {
        this.context = context;
        mDialog = new FragmentDialog(context);
        this.dataList = messageDataList == null ? new ArrayList<>() : messageDataList;
    }

    public void setOnConfirmAmountListener(OnConfirmAmountListener listener) {
        this.onConfirmAmountListener = listener;
    }

    @Override
    public int getCount() {
        //  return 4;
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gift_card_item_layout, null);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }

        LoadImageUtil.getInstance().loadBackground(viewHolder.gift_card_item_img, dataList.get(position).getLogo());
        viewHolder.gift_card_item_ye.setText("余额：" + dataList.get(position).getRemainAmount());
        if (dataList.get(position).isCheckPwd()) {
            viewHolder.gift_card_item_edit_layout.setVisibility(View.GONE);
            viewHolder.gift_card_item_edit_layout2.setVisibility(View.VISIBLE);
        } else {
            viewHolder.gift_card_item_edit_layout.setVisibility(View.VISIBLE);
            viewHolder.gift_card_item_edit_layout2.setVisibility(View.GONE);
        }

        if (TextUtils.equals(dataList.get(position).getCardNo(), "2336960060000000032"))
            viewHolder.gift_card_item_et.setText("411095");
        else if (TextUtils.equals(dataList.get(position).getCardNo(), "2336960060000000181"))
            viewHolder.gift_card_item_et.setText("043773");
        else
            viewHolder.gift_card_item_et.setText("");

        viewHolder.gift_card_item_et2.setText(dataList.get(position).getSelectedAmount() + "");

        viewHolder.gift_card_item_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(viewHolder.gift_card_item_et.getText().toString())) {
                    RequestData requestData = new RequestData();
                    requestData.getRequestAttrsInstance().setApi_ID(OleConstants.CHECK_GIFT_CARD_PASSWORD_ID);
                    requestData.setREQUEST_DATA(
                            new RequestCheckGiftCardPasswordModel(
                                    Base64.encode(dataList.get(position).getCardNo().getBytes()),
                                    Base64.encode(viewHolder.gift_card_item_et.getText().toString().getBytes())));

                    ServerApi.request(false, requestData, CheckGiftCardPasswordData.class,
                            new PreferencesHelper(context).getString(OleConstants.KEY.REQUEST_SIGN_KEY))
                            .subscribeOn(Schedulers.io())
                            .doOnSubscribe(new Consumer<Disposable>() {
                                @Override
                                public void accept(@NonNull Disposable disposable) throws Exception {
                                    mDialog.showProgressDialog("加载中……", null);
                                }
                            })
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<CheckGiftCardPasswordData>() {
                                @Override
                                public void onSubscribe(@NonNull Disposable d) {

                                }

                                @Override
                                public void onNext(CheckGiftCardPasswordData response) {
                                    mDialog.dissmissDialog();
                                    if (response.getRETURN_CODE().equals(OleConstants.REQUES_SUCCESS)) {
                                        dataList.get(position).setCheckPwd(true);
                                        viewHolder.gift_card_item_edit_layout.setVisibility(View.GONE);
                                        viewHolder.gift_card_item_edit_layout2.setVisibility(View.VISIBLE);
                                    } else {
                                        ToastUtil.showToast(response.getRETURN_DESC());
                                    }
                                }

                                @Override
                                public void onError(@NonNull Throwable e) {
                                    mDialog.dissmissDialog();
                                    ToastUtil.showToast("验证礼品卡密码失败");
                                }

                                @Override
                                public void onComplete() {
                                    Log.i("验证礼品卡密码完成");
                                    mDialog.dissmissDialog();
                                }
                            });
                }
            }
        });

        viewHolder.gift_card_item_confirm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    float amount = Float.valueOf(viewHolder.gift_card_item_et2.getText().toString());
                    if (amount >= 0 && amount <= Float.valueOf(dataList.get(position).getRemainAmount())) {
                        if (onConfirmAmountListener != null) {
                            dataList.get(position).setSelectedAmount(amount);
                            onConfirmAmountListener.onConfirmAmount(position, dataList.get(position));
                        }
                    } else
                        ToastUtil.showToast("输入的金额有误");
                } catch (Exception e) {
                    ToastUtil.showToast("输入的金额有误");
                    e.printStackTrace();
                }
            }
        });

        return v;
    }

    private static class ViewHolder {
        private TextView gift_card_item_ye, gift_card_item_confirm, gift_card_item_confirm2;
        private EditText gift_card_item_et, gift_card_item_et2;
        private ImageView gift_card_item_img;
        private RelativeLayout gift_card_item_edit_layout, gift_card_item_edit_layout2;

        public ViewHolder(View v) {
            gift_card_item_img = (ImageView) v.findViewById(R.id.gift_card_item_img);
            gift_card_item_ye = (TextView) v.findViewById(R.id.gift_card_item_ye);
            gift_card_item_confirm = (TextView) v.findViewById(R.id.gift_card_item_confirm);
            gift_card_item_confirm2 = (TextView) v.findViewById(R.id.gift_card_item_confirm2);
            gift_card_item_et = (EditText) v.findViewById(R.id.gift_card_item_et);
            gift_card_item_et2 = (EditText) v.findViewById(R.id.gift_card_item_et2);
            gift_card_item_edit_layout = (RelativeLayout) v.findViewById(R.id.gift_card_item_edit_layout);
            gift_card_item_edit_layout2 = (RelativeLayout) v.findViewById(R.id.gift_card_item_edit_layout2);
        }
    }

    public interface OnConfirmAmountListener {
        void onConfirmAmount(int position, GiftCardItemData amount);
    }
}
