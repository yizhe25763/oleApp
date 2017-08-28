package com.crv.ole.utils;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.crv.ole.BaseApplication;
import com.crv.ole.R;
import com.crv.ole.utils.glide.BaseImageLoaderStrategy;
import com.crv.ole.utils.glide.GlideCircleTransform;
import com.crv.ole.utils.glide.GlideImageConfig;
import com.crv.ole.utils.glide.ImageLoaderStrategy;
import com.crv.sdk.utils.TextUtil;

import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.Serializable;

/**
 * 加载网络图片工具类，根据个人需求，可以自行扩展
 * Created by wj_wsf on 2017/6/27 10:19.
 */

public class LoadImageUtil implements Serializable {

    private BaseImageLoaderStrategy<GlideImageConfig> imageLoaderStrategy;

    private static class LoadImageUtilHolder {
        /**
         * 单例对象实例
         */
        static final LoadImageUtil INSTANCE = new LoadImageUtil();
    }

    private LoadImageUtil() {
        imageLoaderStrategy = new ImageLoaderStrategy();
    }


    /**
     * readResolve方法应对单例对象被序列化时候
     */
    private Object readResolve() {
        return getInstance();
    }

    public static LoadImageUtil getInstance() {
        return LoadImageUtilHolder.INSTANCE;
    }

    public void loadImage(ImageView image, String url) {
        loadImage(image, url, false);
    }


    public void loadIconImage(ImageView image, String url, boolean circle) {

     /*   ImageOptions imageOptions = new ImageOptions.Builder()
                .setCrop(true)
                .setCircular(circle)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.drawable.bg_mrtx)//加载中默认显示图片
                .setFailureDrawableId(R.drawable.bg_mrtx)//加载失败后默认显示图片
                .build();
        x.image().bind(image, url, imageOptions);
*/

        if (TextUtil.isEmpty(url)) {
            image.setBackgroundResource(R.drawable.bg_mrtx);
            return;
        }
        GlideImageConfig config = GlideImageConfig.builder()
                .url(url)
                .imageView(image)
                .errorPic(R.drawable.bg_mrtx)
                .placeholder(R.drawable.bg_mrtx)
                .transformation(circle ? new GlideCircleTransform(BaseApplication.getInstance()) : null)
                .build();

        imageLoaderStrategy.loadImage(BaseApplication.getInstance(), config);


    }


    public void loadImage(ImageView image, String url, boolean circle) {

/*        ImageOptions imageOptions = new ImageOptions.Builder()
//                .setSize(DensityUtil.dip2px(120), DensityUtil.dip2px(120))//图片大小
//                .setRadius(DensityUtil.dip2px(5))//ImageView圆角半径
                .setCrop(true)
                //是否显示圆形
                .setCircular(circle)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                // 如果使用本地文件url, 添加这个设置可以在本地文件更新后刷新立即生效.
                //设置使用缓存
                //.setUseMemCache(false)
                .build();
        x.image().bind(image, url, imageOptions);*/
        if (TextUtil.isEmpty(url)) {
            image.setBackgroundResource(R.drawable.bg_mrtx);
            return;
        }
        GlideImageConfig config = GlideImageConfig.builder()
                .url(url)
                .imageView(image)
                .transformation(circle ? new GlideCircleTransform(BaseApplication.getInstance()) : null)
                .build();

        imageLoaderStrategy.loadImage(BaseApplication.getInstance(), config);
    }

    /**
     * 加载网络图片，返回一个Drawable，可以设置为view的背景图片
     *
     * @param view
     * @param url
     */
    public void loadBackground(final View view, String url) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setCrop(true)// 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
//                .setLoadingDrawableId(R.mipmap.ic_launcher)//加载中默认显示图片
                .setFailureDrawableId(R.mipmap.ic_launcher)//加载失败后默认显示图片
                //设置使用缓存
                .setUseMemCache(true)

                .build();
        x.image().loadDrawable(url, imageOptions, new Callback.CommonCallback<Drawable>() {
            @Override
            public void onSuccess(Drawable drawable) {
                view.setBackground(drawable);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    /**
     * 自定义默认显示图片
     *
     * @param view
     * @param url
     * @param imageId
     */
    public void loadImage(ImageView view, String url, int imageId, ImageView.ScaleType scaleType) {
     /*   ImageOptions options;
        options = new ImageOptions.Builder()
                .setLoadingDrawableId(imageId)      //设置加载中的图片
                .setFailureDrawableId(imageId)      //设置加载失败的图片
                .setUseMemCache(true)               //使用缓存
                .setImageScaleType(scaleType != null ? scaleType : ImageView.ScaleType.CENTER_CROP)     //设置图片大小显示
                .build();
        x.image().bind(view, url, options);*/
        if (TextUtil.isEmpty(url)) {
            view.setBackgroundResource(imageId);
            return;
        }
        GlideImageConfig config = GlideImageConfig.builder()
                .url(url)
                .imageView(view)
                .errorPic(imageId)
                .placeholder(imageId)
                .build();
        imageLoaderStrategy.loadImage(BaseApplication.getInstance(), config);


    }


}
