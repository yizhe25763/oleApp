package com.crv.ole.personalcenter.model;

import com.crv.ole.net.BaseResponseData;

import java.io.Serializable;
import java.util.List;

/**
 * 商品收藏中商品列表实体类
 */
public class CollectionGoodsListData extends BaseResponseData implements Serializable {
    //接口响应具体数据
    private CollectionGoodsData RETURN_DATA;

    public CollectionGoodsData getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(CollectionGoodsData RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }

    public static class CollectionGoodsData implements Serializable {
        private int totalCount, pageTotal;
        private List<GoodsData> favorList;

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getPageTotal() {
            return pageTotal;
        }

        public void setPageTotal(int pageTotal) {
            this.pageTotal = pageTotal;
        }

        public List<GoodsData> getFavorList() {
            return favorList;
        }

        public void setFavorList(List<GoodsData> favorList) {
            this.favorList = favorList;
        }
    }

    public static class GoodsData implements Serializable {
        private String name, productId, imgSrc, differencePrice, vipLevel, isSpecialProuct;
        private int price, realPrice, canBuyCount;
        private boolean canBuy;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getImgSrc() {
            return imgSrc;
        }

        public void setImgSrc(String imgSrc) {
            this.imgSrc = imgSrc;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getRealPrice() {
            return realPrice;
        }

        public void setRealPrice(int realPrice) {
            this.realPrice = realPrice;
        }

        public String getDifferencePrice() {
            return differencePrice;
        }

        public void setDifferencePrice(String differencePrice) {
            this.differencePrice = differencePrice;
        }

        public String getVipLevel() {
            return vipLevel;
        }

        public void setVipLevel(String vipLevel) {
            this.vipLevel = vipLevel;
        }

        public String getIsSpecialProuct() {
            return isSpecialProuct;
        }

        public void setIsSpecialProuct(String isSpecialProuct) {
            this.isSpecialProuct = isSpecialProuct;
        }

        public int getCanBuyCount() {
            return canBuyCount;
        }

        public void setCanBuyCount(int canBuyCount) {
            this.canBuyCount = canBuyCount;
        }

        public boolean isCanBuy() {
            return canBuy;
        }

        public void setCanBuy(boolean canBuy) {
            this.canBuy = canBuy;
        }
    }
}
