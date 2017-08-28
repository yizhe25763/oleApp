package com.crv.ole.personalcenter.model;

import com.crv.ole.net.BaseResponseData;

import java.io.Serializable;

/**
 * Created by Fairy on 2017/7/19.
 */

public class ActiveHrtMemberResultData extends BaseResponseData implements Serializable {
    private ActiveHrtMemberResult result;

    public ActiveHrtMemberResult getResult() {
        return result;
    }

    public void setResult(ActiveHrtMemberResult result) {
        this.result = result;
    }


    public class ActiveHrtMemberResult{
        private String cardno;

        public String getCardno() {
            return cardno;
        }

        public void setCardno(String cardno) {
            this.cardno = cardno;
        }
    }
}
