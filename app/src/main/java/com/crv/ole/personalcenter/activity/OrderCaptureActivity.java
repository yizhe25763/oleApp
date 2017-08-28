package com.crv.ole.personalcenter.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.crv.ole.R;
import com.crv.ole.base.BaseAppCompatActivity;
import com.crv.ole.home.activity.BarCodeDetailActivity;
import com.crv.ole.utils.Log;
import com.crv.ole.utils.OleConstants;
import com.crv.sdk.utils.TextUtil;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.util.List;

/**
 * create by lihongshi
 * 二维码/条形码
 */
public class OrderCaptureActivity extends BaseAppCompatActivity implements DecoratedBarcodeView.TorchListener {
    private DecoratedBarcodeView mDecoratedBarcodeView;
    private BeepManager beepManager;
    private String lastText;
    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if (result.getText() == null || result.getText().equals(lastText)) {
                // Prevent duplicate scans
                return;
            }
            if (result.getText().startsWith("http") || result.getText().startsWith("www.")) {
                Toast.makeText(mContext, "请使用正确的条形码!", Toast.LENGTH_SHORT).show();
                return;
            }

            lastText = result.getText();
            beepManager.playBeepSoundAndVibrate();
            Log.i("", "扫码结果:" + lastText);
            goBarCodeDetail(lastText);
            finish();
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDecoratedBarcodeView = (DecoratedBarcodeView) this.findViewById(R.id.decoratedBarcodeView);
        mDecoratedBarcodeView.setTorchListener(this);
        mDecoratedBarcodeView.decodeContinuous(callback);
        beepManager = new BeepManager(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ole_capture;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDecoratedBarcodeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDecoratedBarcodeView.pause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mDecoratedBarcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    /**
     * 闪光灯
     */
    @Override
    public void onTorchOn() {

    }

    @Override
    public void onTorchOff() {

    }

    // 判断是否有闪光灯功能
    private boolean hasFlash() {
        return getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    private void goBarCodeDetail(String text) {
        if (TextUtil.isEmpty(text)) {
            return;
        }
        Intent i = new Intent();
        i.putExtra("content", text.toString());
        setResult(1, i);

    }
}
