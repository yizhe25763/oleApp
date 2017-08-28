package com.crv.ole.shopping.model;

import com.crv.ole.net.BaseResponseData;

import java.util.List;

/**
 * Created by lihongshi on 2017/7/31.
 */

public class ProductResponseData extends BaseResponseData {
    private ReturnData RETURN_DATA;

    public ReturnData getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(ReturnData RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }

    public class ReturnData {
        private String total;
        private List<Product> products;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
        }
    }
}
