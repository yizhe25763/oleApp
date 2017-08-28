package com.crv.ole.home.model;

import com.crv.ole.net.BaseResponseData;

/**
 * Created by yanghongjun on 17/7/22.
 */

public class UserCenterData extends BaseResponseData {

    private UserCenter RETURN_DATA;

    public UserCenter getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(UserCenter RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }


    public class UserCenter {

        private String userimage;//用户头像
        private String nickname;//用户昵称
        private String realName;//名字
        private String loginId;//帐号id
        private String accountName;
        private String userId;//用户id
        private String mobile;//手机号码
        private String icard;//身份证
        private String memberid;//会员编码
        private String memberlevel;// 会员等级common:普通用户，ole:ole会员，hrtv1:华润通v1会员,hrtv2,hrtv3
        private String qqOpenId;//绑定的qq的id
        private String weixinOpenId;//绑定的微信
        private String sinaOpenId;//绑定的新浪微博

        private String cartNum; //购物车数量

        public String getMessageNum() {
            return messageNum;
        }

        public void setMessageNum(String messageNum) {
            this.messageNum = messageNum;
        }

        private String messageNum;//未读消息数

        public String getCartNum() {
            return cartNum;
        }

        public void setCartNum(String cartNum) {
            this.cartNum = cartNum;
        }

        private long total; //订单总数
        private long totalIntegralValue;//个人积分
        private long  totalCouponCount;//优惠劵
        private long totalFavorCount;//收藏

        private long totalNeedPay;//待付款
        private long stocking;//待发货
        private long delivering;//待收货
        private long cancel;//取消的订单数
        private long success;//已经完成的订单数


        public long getTotal() {
            return total;
        }

        public void setTotal(long total) {
            this.total = total;
        }

        public String getLoginId() {
            return loginId;
        }

        public void setLoginId(String loginId) {
            this.loginId = loginId;
        }
        public long getTotalIntegralValue() {
            return totalIntegralValue;
        }

        public void setTotalIntegralValue(long totalIntegralValue) {
            this.totalIntegralValue = totalIntegralValue;
        }

        public long getTotalCouponCount() {
            return totalCouponCount;
        }

        public void setTotalCouponCount(long totalCouponCount) {
            this.totalCouponCount = totalCouponCount;
        }

        public long getTotalFavorCount() {
            return totalFavorCount;
        }

        public void setTotalFavorCount(long totalFavorCount) {
            this.totalFavorCount = totalFavorCount;
        }

        public long getTotalNeedPay() {
            return totalNeedPay;
        }

        public void setTotalNeedPay(long totalNeedPay) {
            this.totalNeedPay = totalNeedPay;
        }

        public long getStocking() {
            return stocking;
        }

        public void setStocking(long stocking) {
            this.stocking = stocking;
        }

        public long getDelivering() {
            return delivering;
        }

        public void setDelivering(long delivering) {
            this.delivering = delivering;
        }

        public long getCancel() {
            return cancel;
        }

        public void setCancel(long cancel) {
            this.cancel = cancel;
        }

        public long getSuccess() {
            return success;
        }

        public void setSuccess(long success) {
            this.success = success;
        }

        public String getMemberid() {
            return memberid;
        }

        public void setMemberid(String memberid) {
            this.memberid = memberid;
        }

        public String getWeixinOpenId() {
            return weixinOpenId;
        }

        public void setWeixinOpenId(String weixinOpenId) {
            this.weixinOpenId = weixinOpenId;
        }

        public String getSinaOpenId() {
            return sinaOpenId;
        }

        public void setSinaOpenId(String sinaOpenId) {
            this.sinaOpenId = sinaOpenId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserimage() {
            return userimage;
        }

        public void setUserimage(String userimage) {
            this.userimage = userimage;
        }

        public String getMemberlevel() {
            return memberlevel;
        }

        public void setMemberlevel(String memberlevel) {
            this.memberlevel = memberlevel;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getQqOpenId() {
            return qqOpenId;
        }

        public void setQqOpenId(String qqOpenId) {
            this.qqOpenId = qqOpenId;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getIcard() {
            return icard;
        }

        public void setIcard(String icard) {
            this.icard = icard;
        }

    }
}
