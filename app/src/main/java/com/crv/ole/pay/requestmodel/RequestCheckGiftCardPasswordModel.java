package com.crv.ole.pay.requestmodel;

/**
 * 作用描述：
 * 创建者： wj_wsf
 * 创建时间： 2017/8/3 16:01.
 */

public class RequestCheckGiftCardPasswordModel {
    public RequestCheckGiftCardPasswordModel(String cardNo, String pwd) {
        this.cardNo = cardNo;
        this.password = pwd;
    }

    private String cardNo, password;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
