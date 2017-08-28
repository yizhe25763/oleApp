package com.crv.ole.utils.glide;

import android.content.Context;

/**
 * Created by zhangbo
 */
public interface BaseImageLoaderStrategy<T extends ImgViewConfig> {
    void loadImage(Context ctx, T config);

    void loadImageWithTarget(Context ctx, T config);
}
