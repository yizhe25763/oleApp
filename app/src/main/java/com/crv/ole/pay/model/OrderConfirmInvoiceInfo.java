package com.crv.ole.pay.model;

import java.io.Serializable;

/**
 * 作用描述：确认订单发票信息
 * 创建者： wj_wsf
 * 创建时间： 2017/8/7 09:29.
 */

public class OrderConfirmInvoiceInfo implements Serializable {
    private String id, invoiceContent, invoiceTitle, invoiceTypeKey, taxCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getInvoiceTypeKey() {
        return invoiceTypeKey;
    }

    public void setInvoiceTypeKey(String invoiceTypeKey) {
        this.invoiceTypeKey = invoiceTypeKey;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }
}
