package com.crv.ole.utils.fileupload;


import com.crv.ole.view.PhotoPopupWindow;
import com.crv.ole.utils.ToastUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import android.Manifest;
import android.app.Activity;
import android.net.Uri;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 拍照和上传，支持android N
 * Created by ghqin on 2016/11/30.
 */

public class UploadPhotoHelper {
    private final static String TIMESTAMP_FORMAT = "yyyy_MM_dd_HH_mm_ss";
    private Activity mActivity;
    /**
     * 存放图片的目录
     */
    private File mPhotoFolder = FolderManager.getPhotoFolder();
    /**
     * 拍照生成的图片文件
     */
    private File mPhotoFile;
    private CapturePhotoHelper mCapturePhotoHelper;

    /**
     * @param activity
     */
    public UploadPhotoHelper(Activity activity) {
        this.mActivity = activity;
        //初始化网络请求对象
    }

    public void getPermission() {
        new RxPermissions(mActivity)
                .request(Manifest.permission.CAMERA)
                .subscribe(granted -> {
                    if (granted) {
                        if (mCapturePhotoHelper == null) {
                            mCapturePhotoHelper = new CapturePhotoHelper(mActivity, mPhotoFolder);
                        }
                        PhotoPopupWindow photoPopupWindow = new PhotoPopupWindow(mActivity, mCapturePhotoHelper);
                        photoPopupWindow.showPopupWindow();
                    } else {

                        ToastUtil.showToast("请打开相应权限");
                    }
                });
    }

    public CapturePhotoHelper getCapturePhotoHelper() {
        return mCapturePhotoHelper;
    }

    /**
     * 获取当前拍到的图片文件
     */
    public File getPhoto() {
        return mPhotoFile;
    }

    public void startPhotoZoom(Uri uri, float aspectX, float aspectY, int outputX, int outputY) {
//        createPhotoFile(String.valueOf(BaseApplication.getInstance().getUserInfo().getId()));
        createPhotoFile(String.valueOf("1"));

        if (mPhotoFile == null) {
            ToastUtil.showToast("error", Toast.LENGTH_SHORT);
            return;
        }

        UCrop uCrop = UCrop.of(uri, Uri.fromFile(mPhotoFile));

        UCrop.Options options = new UCrop.Options();
        //开始设置
        //一共三个参数，分别对应裁剪功能页面的“缩放”，“旋转”，“裁剪”界面，对应的传入NONE，就表示关闭了其手势操作，比如这里我关闭了缩放和旋转界面的手势，只留了裁剪页面的手势操作
        options.setAllowedGestures(UCropActivity.ALL, UCropActivity.ALL, UCropActivity.ALL);
        //结束设置
        uCrop.withOptions(options);

        uCrop.withAspectRatio(aspectX, aspectY)
                .withMaxResultSize(outputX, outputY)
                .start(mActivity);
    }

    /**
     * 创建照片文件
     */
    public void createPhotoFile(String tep) {
        if (mPhotoFolder != null) {
            if (!mPhotoFolder.exists()) {//检查保存图片的目录存不存在
                mPhotoFolder.mkdirs();
            }

            String fileName = new SimpleDateFormat(TIMESTAMP_FORMAT).format(new Date());
            mPhotoFile = new File(mPhotoFolder, fileName + tep + ".jpg");
            if (mPhotoFile.exists()) {
                mPhotoFile.delete();
            }
            try {
                mPhotoFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                mPhotoFile = null;
            }
        } else {
            mPhotoFile = null;
            Toast.makeText(mActivity, "error", Toast.LENGTH_SHORT).show();
        }
    }


}
