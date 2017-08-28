package com.crv.ole.home.model;

import com.crv.ole.net.BaseResponseData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lihongshi on 2017/8/15.
 * 条形码详情对话框
 */

public class BarCodeResponseData extends BaseResponseData {

    private RETURN_DATA RETURN_DATA;

    public BarCodeResponseData.RETURN_DATA getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(BarCodeResponseData.RETURN_DATA RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }

    public class RETURN_DATA implements Serializable {

        private GoodObj goodObj;
        private ActivityObj activityObj;

        public void setGoodObj(GoodObj goodObj) {
            this.goodObj = goodObj;
        }

        public GoodObj getGoodObj() {
            return goodObj;
        }

        public void setActivityObj(ActivityObj activityObj) {
            this.activityObj = activityObj;
        }

        public ActivityObj getActivityObj() {
            return activityObj;
        }

    }

    public class ActivityObj implements Serializable {
        private CardBatchShowObj cardBatchShowObj;
        private String electricActivityIds;
        private String cardBatchIds;
        private int cardNum;

        public void setCardBatchShowObj(CardBatchShowObj cardBatchShowObj) {
            this.cardBatchShowObj = cardBatchShowObj;
        }

        public CardBatchShowObj getCardBatchShowObj() {
            return cardBatchShowObj;
        }

        public void setElectricActivityIds(String electricActivityIds) {
            this.electricActivityIds = electricActivityIds;
        }

        public String getElectricActivityIds() {
            return electricActivityIds;
        }

        public void setCardBatchIds(String cardBatchIds) {
            this.cardBatchIds = cardBatchIds;
        }

        public String getCardBatchIds() {
            return cardBatchIds;
        }

        public void setCardNum(int cardNum) {
            this.cardNum = cardNum;
        }

        public int getCardNum() {
            return cardNum;
        }

    }


    public class GoodObj implements Serializable {

        private List<String> wordColumns;
        private List<ImageContents> imageContents;
        private List<String> wordContents;
        private int contentType;
        private List<String> headImageUrls;
        private String productName;

        public void setWordColumns(List<String> wordColumns) {
            this.wordColumns = wordColumns;
        }

        public List<String> getWordColumns() {
            return wordColumns;
        }

        public void setImageContents(List<ImageContents> imageContents) {
            this.imageContents = imageContents;
        }

        public List<ImageContents> getImageContents() {
            return imageContents;
        }

        public void setWordContents(List<String> wordContents) {
            this.wordContents = wordContents;
        }

        public List<String> getWordContents() {
            return wordContents;
        }

        public void setContentType(int contentType) {
            this.contentType = contentType;
        }

        public int getContentType() {
            return contentType;
        }

        public void setHeadImageUrls(List<String> headImageUrls) {
            this.headImageUrls = headImageUrls;
        }

        public List<String> getHeadImageUrls() {
            return headImageUrls;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductName() {
            return productName;
        }

    }

    public class CardBatchShowObj implements Serializable {

        private String canceled;
        private String createTime;
        private String encryptFile;
        private String cardGenerated;
        private String cardTypeId;
        private String keyFileId;
        private String frozen;
        private String cardNumCode;
        private String keyFilePath;
        private String id;
        private String amount;
        private String activatedTime;
        private String activated;
        private String isThirdCard;
        private String quantity;
        private String merchantId;
        private String createBatchUser;
        private String createBatchUserId;
        private String isRandomPwd;
        private String isVirtual;
        private String outerName;
        private String pwdLength;
        private String effectedBegin;
        private String activatedEnd;
        private String effectiveType;
        private String isMultipleUse;
        private String effectedEnd;
        private String encryptFileId;
        private String activatedUser;
        private String activatedBegin;

        public void setCanceled(String canceled) {
            this.canceled = canceled;
        }

        public String getCanceled() {
            return canceled;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setEncryptFile(String encryptFile) {
            this.encryptFile = encryptFile;
        }

        public String getEncryptFile() {
            return encryptFile;
        }

        public void setCardGenerated(String cardGenerated) {
            this.cardGenerated = cardGenerated;
        }

        public String getCardGenerated() {
            return cardGenerated;
        }

        public void setCardTypeId(String cardTypeId) {
            this.cardTypeId = cardTypeId;
        }

        public String getCardTypeId() {
            return cardTypeId;
        }

        public void setKeyFileId(String keyFileId) {
            this.keyFileId = keyFileId;
        }

        public String getKeyFileId() {
            return keyFileId;
        }

        public void setFrozen(String frozen) {
            this.frozen = frozen;
        }

        public String getFrozen() {
            return frozen;
        }

        public void setCardNumCode(String cardNumCode) {
            this.cardNumCode = cardNumCode;
        }

        public String getCardNumCode() {
            return cardNumCode;
        }

        public void setKeyFilePath(String keyFilePath) {
            this.keyFilePath = keyFilePath;
        }

        public String getKeyFilePath() {
            return keyFilePath;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getAmount() {
            return amount;
        }

        public void setActivatedTime(String activatedTime) {
            this.activatedTime = activatedTime;
        }

        public String getActivatedTime() {
            return activatedTime;
        }

        public void setActivated(String activated) {
            this.activated = activated;
        }

        public String getActivated() {
            return activated;
        }

        public void setIsThirdCard(String isThirdCard) {
            this.isThirdCard = isThirdCard;
        }

        public String getIsThirdCard() {
            return isThirdCard;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

        public String getMerchantId() {
            return merchantId;
        }

        public void setCreateBatchUser(String createBatchUser) {
            this.createBatchUser = createBatchUser;
        }

        public String getCreateBatchUser() {
            return createBatchUser;
        }

        public void setCreateBatchUserId(String createBatchUserId) {
            this.createBatchUserId = createBatchUserId;
        }

        public String getCreateBatchUserId() {
            return createBatchUserId;
        }

        public void setIsRandomPwd(String isRandomPwd) {
            this.isRandomPwd = isRandomPwd;
        }

        public String getIsRandomPwd() {
            return isRandomPwd;
        }

        public void setIsVirtual(String isVirtual) {
            this.isVirtual = isVirtual;
        }

        public String getIsVirtual() {
            return isVirtual;
        }

        public void setOuterName(String outerName) {
            this.outerName = outerName;
        }

        public String getOuterName() {
            return outerName;
        }

        public void setPwdLength(String pwdLength) {
            this.pwdLength = pwdLength;
        }

        public String getPwdLength() {
            return pwdLength;
        }

        public void setEffectedBegin(String effectedBegin) {
            this.effectedBegin = effectedBegin;
        }

        public String getEffectedBegin() {
            return effectedBegin;
        }

        public void setActivatedEnd(String activatedEnd) {
            this.activatedEnd = activatedEnd;
        }

        public String getActivatedEnd() {
            return activatedEnd;
        }

        public void setEffectiveType(String effectiveType) {
            this.effectiveType = effectiveType;
        }

        public String getEffectiveType() {
            return effectiveType;
        }

        public void setIsMultipleUse(String isMultipleUse) {
            this.isMultipleUse = isMultipleUse;
        }

        public String getIsMultipleUse() {
            return isMultipleUse;
        }

        public void setEffectedEnd(String effectedEnd) {
            this.effectedEnd = effectedEnd;
        }

        public String getEffectedEnd() {
            return effectedEnd;
        }

        public void setEncryptFileId(String encryptFileId) {
            this.encryptFileId = encryptFileId;
        }

        public String getEncryptFileId() {
            return encryptFileId;
        }

        public void setActivatedUser(String activatedUser) {
            this.activatedUser = activatedUser;
        }

        public String getActivatedUser() {
            return activatedUser;
        }

        public void setActivatedBegin(String activatedBegin) {
            this.activatedBegin = activatedBegin;
        }

        public String getActivatedBegin() {
            return activatedBegin;
        }

    }

    public class ImageContents implements Serializable {

        private String fileId;
        private String fileName;
        private String url;

        public void setFileId(String fileId) {
            this.fileId = fileId;
        }

        public String getFileId() {
            return fileId;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFileName() {
            return fileName;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

    }
}
