package com.crv.ole.pay.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 作用描述：
 * 创建者： wj_wsf
 * 创建时间： 2017/8/7 09:36.
 */

public class OrderConfirmCouponBatchesData implements Parcelable {
    private String id, name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
    }

    public OrderConfirmCouponBatchesData() {
    }

    protected OrderConfirmCouponBatchesData(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<OrderConfirmCouponBatchesData> CREATOR = new Parcelable.Creator<OrderConfirmCouponBatchesData>() {
        @Override
        public OrderConfirmCouponBatchesData createFromParcel(Parcel source) {
            return new OrderConfirmCouponBatchesData(source);
        }

        @Override
        public OrderConfirmCouponBatchesData[] newArray(int size) {
            return new OrderConfirmCouponBatchesData[size];
        }
    };
}
