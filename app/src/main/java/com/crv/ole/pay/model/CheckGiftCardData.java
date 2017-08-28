package com.crv.ole.pay.model;

import java.io.Serializable;

/**
 * 作用描述：
 * 创建者： wj_wsf
 * 创建时间： 2017/8/4 17:01.
 */

public class CheckGiftCardData implements Serializable {
    private String remainAmount;
    private String cardNo;

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
}
