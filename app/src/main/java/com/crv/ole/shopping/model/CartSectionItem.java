package com.crv.ole.shopping.model;

import java.io.Serializable;

/**
 * Created by lihongshi on 2017/8/1.
 */

public class CartSectionItem implements Serializable {
    private String cartId;
    private String cartType;
    private String text;
    private boolean check;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getCartType() {
        return cartType;
    }

    public void setCartType(String cartType) {
        this.cartType = cartType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
