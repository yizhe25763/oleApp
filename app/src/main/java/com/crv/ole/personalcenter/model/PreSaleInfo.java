package com.crv.ole.personalcenter.model;

import java.io.Serializable;

/**
 * 商品预售信息
 * Created by zhangbo on 2017/8/20.
 */

public class PreSaleInfo implements Serializable {

    private String balanceEndTime;
    private String balanceBeginTime;
    private String balance;//尾款
    private String preSaleType; ///【 定金预售：1】,【定金预售 ，尾款不确定：2】，【全款预售：3】
    private String stockingTime;//
    private String deposit;//定金
    private String depositEndTime;
    private String depositBeginTime;
    private String preSalePayState;//预售支付状态 预售支付状态:0,定金未支付,1:定金已支付,尾款未支付,2:定金与尾款都已支付

    public String getBalanceEndTime() {
        return balanceEndTime;
    }

    public void setBalanceEndTime(String balanceEndTime) {
        this.balanceEndTime = balanceEndTime;
    }

    public String getBalanceBeginTime() {
        return balanceBeginTime;
    }

    public void setBalanceBeginTime(String balanceBeginTime) {
        this.balanceBeginTime = balanceBeginTime;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getPreSaleType() {
        return preSaleType;
    }

    public void setPreSaleType(String preSaleType) {
        this.preSaleType = preSaleType;
    }

    public String getStockingTime() {
        return stockingTime;
    }

    public void setStockingTime(String stockingTime) {
        this.stockingTime = stockingTime;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getDepositEndTime() {
        return depositEndTime;
    }

    public void setDepositEndTime(String depositEndTime) {
        this.depositEndTime = depositEndTime;
    }

    public String getDepositBeginTime() {
        return depositBeginTime;
    }

    public void setDepositBeginTime(String depositBeginTime) {
        this.depositBeginTime = depositBeginTime;
    }

    public String getPreSalePayState() {
        return preSalePayState;
    }

    public void setPreSalePayState(String preSalePayState) {
        this.preSalePayState = preSalePayState;
    }
}
