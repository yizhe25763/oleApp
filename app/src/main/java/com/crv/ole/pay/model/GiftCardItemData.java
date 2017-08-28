package com.crv.ole.pay.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作用描述：预付卡实体类
 * 创建者： wj_wsf
 * 创建时间： 2017/8/4 17:02.
 */

public class GiftCardItemData implements Parcelable {
    private String remainAmount;
    private String cardNo, logo, loadState;
    private boolean isCheckPwd = false;
    private float selectedAmount = 0f;

    public String getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(String remainAmount) {
        this.remainAmount = remainAmount;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLoadState() {
        return loadState;
    }

    public void setLoadState(String loadState) {
        this.loadState = loadState;
    }

    public boolean isCheckPwd() {
        return isCheckPwd;
    }

    public void setCheckPwd(boolean checkPwd) {
        isCheckPwd = checkPwd;
    }

    public float getSelectedAmount() {
        return selectedAmount;
    }

    public void setSelectedAmount(float selectedAmount) {
        this.selectedAmount = selectedAmount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.remainAmount);
        dest.writeString(this.cardNo);
        dest.writeString(this.logo);
        dest.writeString(this.loadState);
        dest.writeByte(this.isCheckPwd ? (byte) 1 : (byte) 0);
        dest.writeFloat(this.selectedAmount);
    }

    public GiftCardItemData() {
    }

    protected GiftCardItemData(Parcel in) {
        this.remainAmount = in.readString();
        this.cardNo = in.readString();
        this.logo = in.readString();
        this.loadState = in.readString();
        this.isCheckPwd = in.readByte() != 0;
        this.selectedAmount = in.readFloat();
    }

    public static final Parcelable.Creator<GiftCardItemData> CREATOR = new Parcelable.Creator<GiftCardItemData>() {
        @Override
        public GiftCardItemData createFromParcel(Parcel source) {
            return new GiftCardItemData(source);
        }

        @Override
        public GiftCardItemData[] newArray(int size) {
            return new GiftCardItemData[size];
        }
    };
}
