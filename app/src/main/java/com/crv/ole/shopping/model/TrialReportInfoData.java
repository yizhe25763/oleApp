package com.crv.ole.shopping.model;

import java.util.List;

/**
 * Created by Fairy on 2017/8/15.
 */

public class TrialReportInfoData {

    /**
     * RETURN_CODE : S0A00000
     * RETURN_DESC : success
     * RETURN_STAMP : 2017-08-16 15:49:48
     * RETURN_DATA : {"NumOfPage":2,"allCount":12,"list":[{"examineInfo":{"examineStatus":0,"examineStatusName":"notExamined ","examineUserName":"","CreateTime":1502857986129,"examinePassReason":"","examineUserId":"","examineRejectReasn":""},"createTime":1502857986125,"productId":"p_2670631","id":"ole_productTrial_reports_50006","activityId":"test3","likesInfo":{"status":0,"likesCount":0},"wordContent":"这个味道很好。手感非常的刺激。这个价格便宜还好。但是就是觉得果仁还不够干。这个坚果很好吃，很受欢迎，拍照片当中只有剩下一半都不到了，挺不错的。下次还会购买，孩子吃不错，身上带二包，肚子饿的时候拿出来垫一点，又营养又抵饱不错。\n","fileIdList":["http://10.0.147.163/img/2017/8/12/96800586651882929378063.png","http://10.0.147.163/img/2017/8/11/96700102185470441189957.jpg","http://10.0.147.163/img/2017/8/11/96700102185470441189957.jpg"],"examinLog":[],"userId":"u_4410013","userInfo":{"userLogoUrl":"","nickName":"hhhh"},"productName":"【6.6折原价27.8元】旧街场经典白咖啡40g*10","activityName":"这是一个活动名称","orderId":"o_123456","oneSentence":"葫噜噜噜噜噜噜噜"}]}
     */

    private String RETURN_CODE;
    private String RETURN_DESC;
    private String RETURN_STAMP;
    private RETURNDATABean RETURN_DATA;

    public String getRETURN_CODE() {
        return RETURN_CODE;
    }

    public void setRETURN_CODE(String RETURN_CODE) {
        this.RETURN_CODE = RETURN_CODE;
    }

    public String getRETURN_DESC() {
        return RETURN_DESC;
    }

    public void setRETURN_DESC(String RETURN_DESC) {
        this.RETURN_DESC = RETURN_DESC;
    }

    public String getRETURN_STAMP() {
        return RETURN_STAMP;
    }

    public void setRETURN_STAMP(String RETURN_STAMP) {
        this.RETURN_STAMP = RETURN_STAMP;
    }

    public RETURNDATABean getRETURN_DATA() {
        return RETURN_DATA;
    }

    public void setRETURN_DATA(RETURNDATABean RETURN_DATA) {
        this.RETURN_DATA = RETURN_DATA;
    }

    public static class RETURNDATABean {
        /**
         * NumOfPage : 2
         * allCount : 12
         * list : [{"examineInfo":{"examineStatus":0,"examineStatusName":"notExamined ","examineUserName":"","CreateTime":1502857986129,"examinePassReason":"","examineUserId":"","examineRejectReasn":""},"createTime":1502857986125,"productId":"p_2670631","id":"ole_productTrial_reports_50006","activityId":"test3","likesInfo":{"status":0,"likesCount":0},"wordContent":"这个味道很好。手感非常的刺激。这个价格便宜还好。但是就是觉得果仁还不够干。这个坚果很好吃，很受欢迎，拍照片当中只有剩下一半都不到了，挺不错的。下次还会购买，孩子吃不错，身上带二包，肚子饿的时候拿出来垫一点，又营养又抵饱不错。\n","fileIdList":["http://10.0.147.163/img/2017/8/12/96800586651882929378063.png","http://10.0.147.163/img/2017/8/11/96700102185470441189957.jpg","http://10.0.147.163/img/2017/8/11/96700102185470441189957.jpg"],"examinLog":[],"userId":"u_4410013","userInfo":{"userLogoUrl":"","nickName":"hhhh"},"productName":"【6.6折原价27.8元】旧街场经典白咖啡40g*10","activityName":"这是一个活动名称","orderId":"o_123456","oneSentence":"葫噜噜噜噜噜噜噜"}]
         */

        private int NumOfPage;
        private int allCount;
        private List<ListBean> list;

        public int getNumOfPage() {
            return NumOfPage;
        }

        public void setNumOfPage(int NumOfPage) {
            this.NumOfPage = NumOfPage;
        }

        public int getAllCount() {
            return allCount;
        }

        public void setAllCount(int allCount) {
            this.allCount = allCount;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * examineInfo : {"examineStatus":0,"examineStatusName":"notExamined ","examineUserName":"","CreateTime":1502857986129,"examinePassReason":"","examineUserId":"","examineRejectReasn":""}
             * createTime : 1502857986125
             * productId : p_2670631
             * id : ole_productTrial_reports_50006
             * activityId : test3
             * likesInfo : {"status":0,"likesCount":0}
             * wordContent : 这个味道很好。手感非常的刺激。这个价格便宜还好。但是就是觉得果仁还不够干。这个坚果很好吃，很受欢迎，拍照片当中只有剩下一半都不到了，挺不错的。下次还会购买，孩子吃不错，身上带二包，肚子饿的时候拿出来垫一点，又营养又抵饱不错。
             * <p>
             * fileIdList : ["http://10.0.147.163/img/2017/8/12/96800586651882929378063.png","http://10.0.147.163/img/2017/8/11/96700102185470441189957.jpg","http://10.0.147.163/img/2017/8/11/96700102185470441189957.jpg"]
             * examinLog : []
             * userId : u_4410013
             * userInfo : {"userLogoUrl":"","nickName":"hhhh"}
             * productName : 【6.6折原价27.8元】旧街场经典白咖啡40g*10
             * activityName : 这是一个活动名称
             * orderId : o_123456
             * oneSentence : 葫噜噜噜噜噜噜噜
             */

            private ExamineInfoBean examineInfo;
            private long createTime;
            private String productId;
            private String id;
            private String activityId;
            private LikesInfoBean likesInfo;
            private String wordContent;
            private String userId;
            private UserInfoBean userInfo;
            private String productName;
            private String activityName;
            private String orderId;
            private String oneSentence;
            private List<String> fileIdList;
            private List<?> examinLog;
            private String moodWords;
            private String feelingWords;
            private String freeWords;
            private String compareWords;

            public String getCompareWords() {
                return compareWords;
            }

            public void setCompareWords(String compareWords) {
                this.compareWords = compareWords;
            }

            public String getMoodWords() {
                return moodWords;
            }

            public void setMoodWords(String moodWords) {
                this.moodWords = moodWords;
            }

            public String getFeelingWords() {
                return feelingWords;
            }

            public void setFeelingWords(String feelingWords) {
                this.feelingWords = feelingWords;
            }

            public String getFreeWords() {
                return freeWords;
            }

            public void setFreeWords(String freeWords) {
                this.freeWords = freeWords;
            }

            public ExamineInfoBean getExamineInfo() {
                return examineInfo;
            }

            public void setExamineInfo(ExamineInfoBean examineInfo) {
                this.examineInfo = examineInfo;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getActivityId() {
                return activityId;
            }

            public void setActivityId(String activityId) {
                this.activityId = activityId;
            }

            public LikesInfoBean getLikesInfo() {
                return likesInfo;
            }

            public void setLikesInfo(LikesInfoBean likesInfo) {
                this.likesInfo = likesInfo;
            }

            public String getWordContent() {
                return wordContent;
            }

            public void setWordContent(String wordContent) {
                this.wordContent = wordContent;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public UserInfoBean getUserInfo() {
                return userInfo;
            }

            public void setUserInfo(UserInfoBean userInfo) {
                this.userInfo = userInfo;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getActivityName() {
                return activityName;
            }

            public void setActivityName(String activityName) {
                this.activityName = activityName;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getOneSentence() {
                return oneSentence;
            }

            public void setOneSentence(String oneSentence) {
                this.oneSentence = oneSentence;
            }

            public List<String> getFileIdList() {
                return fileIdList;
            }

            public void setFileIdList(List<String> fileIdList) {
                this.fileIdList = fileIdList;
            }

            public List<?> getExaminLog() {
                return examinLog;
            }

            public void setExaminLog(List<?> examinLog) {
                this.examinLog = examinLog;
            }

            public static class ExamineInfoBean {
                /**
                 * examineStatus : 0
                 * examineStatusName : notExamined
                 * examineUserName :
                 * CreateTime : 1502857986129
                 * examinePassReason :
                 * examineUserId :
                 * examineRejectReasn :
                 */

                private int examineStatus;
                private String examineStatusName;
                private String examineUserName;
                private long CreateTime;
                private String examinePassReason;
                private String examineUserId;
                private String examineRejectReasn;

                public int getExamineStatus() {
                    return examineStatus;
                }

                public void setExamineStatus(int examineStatus) {
                    this.examineStatus = examineStatus;
                }

                public String getExamineStatusName() {
                    return examineStatusName;
                }

                public void setExamineStatusName(String examineStatusName) {
                    this.examineStatusName = examineStatusName;
                }

                public String getExamineUserName() {
                    return examineUserName;
                }

                public void setExamineUserName(String examineUserName) {
                    this.examineUserName = examineUserName;
                }

                public long getCreateTime() {
                    return CreateTime;
                }

                public void setCreateTime(long CreateTime) {
                    this.CreateTime = CreateTime;
                }

                public String getExaminePassReason() {
                    return examinePassReason;
                }

                public void setExaminePassReason(String examinePassReason) {
                    this.examinePassReason = examinePassReason;
                }

                public String getExamineUserId() {
                    return examineUserId;
                }

                public void setExamineUserId(String examineUserId) {
                    this.examineUserId = examineUserId;
                }

                public String getExamineRejectReasn() {
                    return examineRejectReasn;
                }

                public void setExamineRejectReasn(String examineRejectReasn) {
                    this.examineRejectReasn = examineRejectReasn;
                }
            }

            public static class LikesInfoBean {
                /**
                 * status : 0
                 * likesCount : 0
                 */

                private int status;
                private int likesCount;

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public int getLikesCount() {
                    return likesCount;
                }

                public void setLikesCount(int likesCount) {
                    this.likesCount = likesCount;
                }
            }

            public static class UserInfoBean {
                /**
                 * userLogoUrl :
                 * nickName : hhhh
                 */

                private String userLogoUrl;
                private String nickName;

                public String getUserLogoUrl() {
                    return userLogoUrl;
                }

                public void setUserLogoUrl(String userLogoUrl) {
                    this.userLogoUrl = userLogoUrl;
                }

                public String getNickName() {
                    return nickName;
                }

                public void setNickName(String nickName) {
                    this.nickName = nickName;
                }
            }
        }
    }
}
