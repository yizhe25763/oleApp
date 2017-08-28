package com.crv.ole.pay.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 作用描述：这个购物车可用的卡批次
 * 创建者： wj_wsf
 * 创建时间： 2017/8/7 09:32.
 */

public class OrderConfirmAllCardsData implements Parcelable {
    private int cardCount, faceValue, maxUseAmount;
    private String id, name,ruleRemarkDes,effectedBegin,effectedEnd;

    public int getCardCount() {
        return cardCount;
    }

    public void setCardCount(int cardCount) {
        this.cardCount = cardCount;
    }

    public int getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(int faceValue) {
        this.faceValue = faceValue;
    }

    public int getMaxUseAmount() {
        return maxUseAmount;
    }

    public void setMaxUseAmount(int maxUseAmount) {
        this.maxUseAmount = maxUseAmount;
    }

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

    public String getRuleRemarkDes() {
        return ruleRemarkDes;
    }

    public void setRuleRemarkDes(String ruleRemarkDes) {
        this.ruleRemarkDes = ruleRemarkDes;
    }

    public String getEffectedBegin() {
        return effectedBegin;
    }

    public void setEffectedBegin(String effectedBegin) {
        this.effectedBegin = effectedBegin;
    }

    public String getEffectedEnd() {
        return effectedEnd;
    }

    public void setEffectedEnd(String effectedEnd) {
        this.effectedEnd = effectedEnd;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.cardCount);
        dest.writeInt(this.faceValue);
        dest.writeInt(this.maxUseAmount);
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.ruleRemarkDes);
        dest.writeString(this.effectedBegin);
        dest.writeString(this.effectedEnd);
    }

    public OrderConfirmAllCardsData() {
    }

    protected OrderConfirmAllCardsData(Parcel in) {
        this.cardCount = in.readInt();
        this.faceValue = in.readInt();
        this.maxUseAmount = in.readInt();
        this.id = in.readString();
        this.name = in.readString();
        this.ruleRemarkDes = in.readString();
        this.effectedBegin = in.readString();
        this.effectedEnd = in.readString();
    }

    public static final Parcelable.Creator<OrderConfirmAllCardsData> CREATOR = new Parcelable.Creator<OrderConfirmAllCardsData>() {
        @Override
        public OrderConfirmAllCardsData createFromParcel(Parcel source) {
            return new OrderConfirmAllCardsData(source);
        }

        @Override
        public OrderConfirmAllCardsData[] newArray(int size) {
            return new OrderConfirmAllCardsData[size];
        }
    };
}
