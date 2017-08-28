package com.crv.ole.utils;

import android.app.Activity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.crv.ole.R;
import com.crv.sdk.utils.StringUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMWeb;
import com.vondear.rxtools.view.dialog.RxDialog;

import java.util.Map;

/**
 * Created by yanghongjun on 17/7/3.
 */

/**
 * 注意,使用友盟登录及分享需要在依赖的activity(不能在fragment)重载如下方法
 *
 * @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
 * super.onActivityResult(requestCode, resultCode, data);
 * UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
 * }
 */

public class UmengUtils {

    public static UMShareAPI umShareAPI;


    /**
     * 友盟三方登录
     *
     * @param context  依赖的activity
     * @param type     登录类型如:微信,qq,微博
     * @param callBack 登录回调
     */
    public static void login(Activity context, LogInType type, LogInCallBack callBack) {
        umShareAPI = UMShareAPI.get(context);

        SHARE_MEDIA share_type = SHARE_MEDIA.WEIXIN;
        if (type.equals(LogInType.QQ)) {
            share_type = SHARE_MEDIA.QQ;
        } else if (type.equals(LogInType.WECHAT)) {
            share_type = SHARE_MEDIA.WEIXIN;
        } else if (type.equals(LogInType.SINA)) {
            share_type = SHARE_MEDIA.SINA;
        }

        umShareAPI.getPlatformInfo(context, share_type, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                callBack.onStart(type);
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                callBack.onComplete(type, i, map);
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                callBack.onError(type, i, throwable);
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                callBack.onCancel(type, i);
            }
        });

    }

    /**
     * 分享文本
     *
     * @param context  依赖的activity
     * @param text     分享的文本
     * @param callBack 分享回调
     */
    public static void shareText(Activity context, String text, ShareCallBack callBack) {
        shareContext(context, text, null, null, null, null, R.drawable.ic_channel_normal, ShareContextType.SHARE_TEXT, callBack);
    }

    /**
     * 注意新浪微博这个缩略图必填,没有网络图片可以使用本地图片icon
     *
     * @param context      依赖的activity
     * @param title        分享的标题
     * @param decription   分享的描述
     * @param url          分享的url
     * @param thumbUrl     分享的缩略图url
     * @param defaultThumb 默认分享的锁略图
     * @param callBack     回调
     */

    public static void shareUrl(Activity context, String title, String decription, String url, String thumbUrl, int defaultThumb, ShareCallBack callBack) {
        shareContext(context, null, title, decription, url, thumbUrl, defaultThumb, ShareContextType.SHARE_URL, callBack);
    }

    /**
     * @param context           依赖的activity
     * @param text              分享的文本
     * @param desciption        分享的描述
     * @param videoUrl          视频url
     * @param imageUrl          分享视频的image
     * @param defaultVideoImage 默认的分享视频的image
     * @param callBack          回调
     */
    public static void shareVideo(Activity context, String text, String desciption, String videoUrl, String imageUrl, int defaultVideoImage, ShareCallBack callBack) {
        shareContext(context, text, null, desciption, videoUrl, imageUrl, defaultVideoImage, ShareContextType.SHARE_VIDEO, callBack);
    }

    private static void shareContext(Activity context, final String text, final String title, final String descrip, String url, String imageUrl, int defaultThumb, ShareContextType type, ShareCallBack callBack) {

        UMShareListener listener = new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA platform) {
                callBack.onStart(invertMediaToStype(platform));
            }

            @Override
            public void onResult(SHARE_MEDIA platform) {
                callBack.onResult(invertMediaToStype(platform));
            }

            @Override
            public void onError(SHARE_MEDIA platform, Throwable throwable) {
                callBack.onError(invertMediaToStype(platform), throwable);
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
                callBack.onCancel(invertMediaToStype(platform));
            }
        };

        RxDialog dialog = new RxDialog(context, 0, Gravity.BOTTOM);
        View view = LayoutInflater.from(context).inflate(R.layout.share_dialog_layout, null);
        View.OnClickListener dialoglistener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                if (dialog != null) {
                    dialog.dismiss();
                }

                SHARE_MEDIA media = SHARE_MEDIA.WEIXIN;
                if (id == R.id.wechat_bt) {
                    media = SHARE_MEDIA.WEIXIN;
                } else if (id == R.id.wechatcicle_bt) {
                    media = SHARE_MEDIA.WEIXIN_CIRCLE;
                } else if (id == R.id.sina_bt) {
                    media = SHARE_MEDIA.SINA;
                } else if (id == R.id.qq_bt) {
                    media = SHARE_MEDIA.QQ;
                }

                if (id == R.id.cancle_bt) {
                    callBack.onCancel(invertMediaToStype(media));
                    return;
                }

                if (type == ShareContextType.SHARE_URL) {
                    UMImage image = null;
                    if (!TextUtils.isEmpty(imageUrl) && StringUtils.isUrl(imageUrl)) {
                        image = new UMImage(context, imageUrl);//网络图片
                    }
                    if (image==null){
                        image=new UMImage(context, R.drawable.ic_channel_normal);
                    }

                    UMWeb web = new UMWeb(url);
                    web.setTitle(StringUtils.isNullOrEmpty(title) ? "" : title);//标题
                    web.setThumb(image);  //缩略图

                    String des = descrip;
//                    if (des != null && des.length() > 10) {
//                        des=des.substring(0, 5);
//                    }
                    web.setDescription(StringUtils.isNullOrEmpty(des) ? "" : des);//描述
                    new ShareAction(context).withMedia(web).setPlatform(media).setCallback(listener).share();
                } else if (type == ShareContextType.SHARE_TEXT) {
                    new ShareAction(context).withText(StringUtils.isNullOrEmpty(text) ? "" : text).setPlatform(media).setCallback(listener).share();
                } else if (type == ShareContextType.SHARE_VIDEO) {
                    if (TextUtils.isEmpty(url) || !StringUtils.isUrl(url)) {
                        callBack.onError(ShareType.NONE, new Throwable("分享的视频url格式不正常"));
                        return;
                    }
                    UMVideo video = new UMVideo(url);
                    video.setTitle(title);//视频的标题
                    UMImage image = new UMImage(context, defaultThumb);
                    if (!TextUtils.isEmpty(imageUrl) && StringUtils.isUrl(imageUrl)) {
                        image = new UMImage(context, imageUrl);//网络图片
                    }
                    video.setThumb(image);//视频的缩略图
                    video.setDescription(descrip);//视频的描述
                    new ShareAction(context).withText(StringUtils.isNullOrEmpty(text) ? "" : text).setPlatform(media).setCallback(listener).share();
                }
            }
        };

        view.findViewById(R.id.wechat_bt).setOnClickListener(dialoglistener);
        view.findViewById(R.id.wechatcicle_bt).setOnClickListener(dialoglistener);
        view.findViewById(R.id.sina_bt).setOnClickListener(dialoglistener);
        view.findViewById(R.id.qq_bt).setOnClickListener(dialoglistener);
        view.findViewById(R.id.cancle_bt).setOnClickListener(dialoglistener);

        dialog.setFullScreenWidth();
        dialog.setContentView(view);
        dialog.getWindow().setWindowAnimations(R.style.dialogBottomStyle);
        dialog.show();


    }

    private static ShareType invertMediaToStype(SHARE_MEDIA media) {
        if (media.equals(SHARE_MEDIA.WEIXIN)) {
            return ShareType.WECHAT;
        } else if (media.equals(SHARE_MEDIA.SINA)) {
            return ShareType.SINA;
        } else if (media.equals(SHARE_MEDIA.WEIXIN_CIRCLE)) {
            return ShareType.WEIXIN_CIRCLE;
        } else if (media.equals(SHARE_MEDIA.QQ)) {
            return ShareType.QQ;
        } else if (media.equals(SHARE_MEDIA.QZONE)) {
            return ShareType.QQ_ZONE;
        }
        return ShareType.NONE;
    }

    public interface LogInCallBack {
        void onStart(LogInType type);

        void onComplete(LogInType type, int i, Map<String, String> map);

        void onError(LogInType type, int i, Throwable throwable);

        void onCancel(LogInType type, int i);
    }

    public interface ShareCallBack {
        void onStart(ShareType type);

        void onResult(ShareType type);

        void onError(ShareType type, Throwable throwable);

        void onCancel(ShareType type);
    }

    public enum LogInType {
        ORIGIN, WECHAT, QQ, SINA
    }

    public enum ShareType {
        WECHAT, WEIXIN_CIRCLE, QQ, QQ_ZONE, SINA, NONE
    }

    private enum ShareContextType {
        SHARE_TEXT, SHARE_URL, SHARE_VIDEO
    }


}
