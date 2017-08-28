package com.crv.ole.pay.model;

import java.io.Serializable;
import java.util.List;

/**
 * 作用描述：
 * 创建者： wj_wsf
 * 创建时间： 2017/8/4 17:01.
 */

public class GiftCardData implements Serializable {
    private int total;
    private List<GiftCardItemData> cards;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<GiftCardItemData> getCards() {
        return cards;
    }

    public void setCards(List<GiftCardItemData> cards) {
        this.cards = cards;
    }
}
