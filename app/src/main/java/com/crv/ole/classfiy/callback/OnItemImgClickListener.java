package com.crv.ole.classfiy.callback;

import com.crv.ole.base.BaseItemTouchListener;

/**
 *
 * Created by zhangbo on 2017/8/5.
 */

public interface OnItemImgClickListener<T> extends BaseItemTouchListener<T> {

    //添加到购物车
    void OnAddToShoppingCar(T item, int position);

    //从购物车中删除
    //void OnRMToShoppingCar(T item, int position);
}
