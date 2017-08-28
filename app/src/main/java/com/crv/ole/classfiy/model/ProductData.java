package com.crv.ole.classfiy.model;


import com.crv.ole.shopping.model.Product;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangbo on 2017/8/5.
 */

public class ProductData implements Serializable {
    private int total;
    private List<Product> products;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
