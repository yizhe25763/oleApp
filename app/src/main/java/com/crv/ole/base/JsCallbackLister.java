package com.crv.ole.base;

/**
 * Created by yanghongjun on 17/8/9.
 */

public abstract class JsCallbackLister {
    //收藏
    public void handleCollect(String json) {}

    //立即购买
    public void goTo(String json) {}

    public void showToast(String json){}

    public void toogleLoading(String json){}

    //获取原生数据
    public void fetch(String json) {}

    //显示图片集
    public void showPictureCollection(String json){}
}
