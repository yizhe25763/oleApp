package com.crv.ole.config;


import android.content.Context;
import android.graphics.Bitmap;

import com.crv.ole.R;
import com.qiyukf.unicorn.api.ImageLoaderListener;
import com.qiyukf.unicorn.api.UnicornImageLoader;
import com.vondear.rxtools.RxImageUtils;

/**
 * Created by lihonghsi on 2017/7/24.
 * 在线客服图片加载库实现类,注意本应用加载图片不使用该类
 */

public class OleUnicornImageLoader implements UnicornImageLoader {
    private Context context;

    public OleUnicornImageLoader(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public Bitmap loadImageSync(String uri, int width, int height) {
        Bitmap resultBitmap = RxImageUtils.getBitmap(this.context, R.drawable.logo_ole);
        return resultBitmap;
    }

    @Override
    public void loadImage(String uri, int width, int height, final ImageLoaderListener listener) {
        Bitmap resultBitmap = RxImageUtils.getBitmap(this.context, R.drawable.logo_ole);
        if (listener != null) {
            if (resultBitmap != null) {
                listener.onLoadComplete(resultBitmap);
            } else {
                listener.onLoadFailed(null);
            }
        }
    }
}
