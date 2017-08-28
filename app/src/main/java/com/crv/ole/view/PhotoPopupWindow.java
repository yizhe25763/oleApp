package com.crv.ole.view;


import com.crv.ole.R;
import com.crv.ole.utils.ToastUtil;
import com.crv.ole.utils.fileupload.CapturePhotoHelper;
import com.crv.ole.utils.fileupload.UploadUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

import razerdp.basepopup.BasePopupWindow;

/**
 * 上传头像popupwindow
 */
public class PhotoPopupWindow extends BasePopupWindow implements View.OnClickListener {
    private Activity mContext;
    private TextView tvCancel;
    private LinearLayout tvPhotograph, tvAlbum;
    private CapturePhotoHelper mCapturePhotoHelper;

    public PhotoPopupWindow(Activity context, CapturePhotoHelper capturePhotoHelper) {
        super(context);
        this.mContext = context;
        this.mCapturePhotoHelper = capturePhotoHelper;
        init();
        setViewClickListener(this, tvPhotograph, tvAlbum, tvCancel);
    }


    private void init() {
        tvPhotograph = (LinearLayout) findViewById(R.id.tvPhotograph);
        tvCancel = (TextView) findViewById(R.id.tvCancel);
        tvAlbum = (LinearLayout) findViewById(R.id.tvAlbum);
    }

    @Override
    protected Animation initShowAnimation() {
        return null;
    }

    @Override
    public View getClickToDismissView() {
        return getPopupWindowView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvPhotograph:
                turnOnCamera();
                dismiss();
                break;
            case R.id.tvAlbum:
                choosePhoto();
                dismiss();
                break;
            case R.id.tvCancel:
                dismiss();
                break;
        }
    }

    @Override
    public View onCreatePopupView() {
        return createPopupById(R.layout.popupwindow_photo);
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.popup_anima);
    }

    /**
     * 开启相机
     */
    private void turnOnCamera() {

        new RxPermissions(mContext)
                .request(Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        mCapturePhotoHelper.capture();
                    } else {
                        ToastUtil.showToast("请开启相机权限");
                    }
                });
    }


    /**
     * 相册选图
     */
    private void choosePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        mContext.startActivityForResult(intent, UploadUtils.CHOOSE_PICTURE);
    }
}
