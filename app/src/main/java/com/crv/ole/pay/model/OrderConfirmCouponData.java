package com.crv.ole.pay.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 作用描述：所有可使用券的规则列表
 * 创建者： wj_wsf
 * 创建时间： 2017/8/7 09:34.
 */

public class OrderConfirmCouponData implements Parcelable {
    private String amount, ruleId, ruleName, type;
    private ArrayList<OrderConfirmCouponBatchesData> availableBatches;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<OrderConfirmCouponBatchesData> getAvailableBatches() {
        return availableBatches;
    }

    public void setAvailableBatches(ArrayList<OrderConfirmCouponBatchesData> availableBatches) {
        this.availableBatches = availableBatches;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.amount);
        dest.writeString(this.ruleId);
        dest.writeString(this.ruleName);
        dest.writeString(this.type);
        dest.writeList(this.availableBatches);
    }

    public OrderConfirmCouponData() {
    }

    protected OrderConfirmCouponData(Parcel in) {
        this.amount = in.readString();
        this.ruleId = in.readString();
        this.ruleName = in.readString();
        this.type = in.readString();
        this.availableBatches = new ArrayList<OrderConfirmCouponBatchesData>();
        in.readList(this.availableBatches, OrderConfirmCouponBatchesData.class.getClassLoader());
    }

    public static final Parcelable.Creator<OrderConfirmCouponData> CREATOR = new Parcelable.Creator<OrderConfirmCouponData>() {
        @Override
        public OrderConfirmCouponData createFromParcel(Parcel source) {
            return new OrderConfirmCouponData(source);
        }

        @Override
        public OrderConfirmCouponData[] newArray(int size) {
            return new OrderConfirmCouponData[size];
        }
    };
}
