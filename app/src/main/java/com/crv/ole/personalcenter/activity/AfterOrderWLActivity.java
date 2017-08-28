package com.crv.ole.personalcenter.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.ole.base.BaseActivity;
import com.crv.ole.personalcenter.fragment.WLBottomDialogFragment;
import com.crv.ole.utils.OleConstants;
import com.crv.ole.utils.ToastUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fanhaoyi on 2017/8/24.
 */

public class AfterOrderWLActivity extends BaseActivity {


    @BindView(R.id.order_scan)
    ImageView orderScan;
    @BindView(R.id.order_id_edt)
    EditText orderIdEdt;
    @BindView(R.id.order_wls)
    TextView orderWls;
    @BindView(R.id.order_wl)
    ImageView orderWl;
    @BindView(R.id.order_wl_layout)
    RelativeLayout orderWlLayout;
    @BindView(R.id.order_comment)
    TextView orderComment;
    @BindView(R.id.title_back_btn)
    TextView titleBackBtn;
    @BindView(R.id.title_back_layout)
    RelativeLayout titleBackLayout;
    @BindView(R.id.title_name_tv)
    TextView titleNameTv;
    @BindView(R.id.title_iv_1)
    TextView titleIv1;
    @BindView(R.id.title_iv_2)
    TextView titleIv2;
    @BindView(R.id.navigationbar_rl)
    LinearLayout navigationbarRl;
    @BindView(R.id.order_id)
    TextView orderId;
    @BindView(R.id.wl_contents)
    TextView wlContents;


    private int mRequestCode = 2;
    private String mOrderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_sale_wl);
        ButterKnife.bind(this);
        mOrderId = this.getIntent().getStringExtra(OleConstants.BundleConstant.ARG_PARAMS_0);
        initTitleViews();
        initBackButton();
        setBarTitle("发起售后");
    }

    @OnClick({R.id.order_scan, R.id.order_id_edt, R.id.order_wl_layout, R.id.order_comment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.order_scan:
                new RxPermissions(this)
                        .request(Manifest.permission.CAMERA)
                        .subscribe(granted -> {
                            if (granted) { // 在android 6.0之前会默认返回true
                                // 已经获取权限
                                startActivityForResult(new Intent(mContext, OrderCaptureActivity.class), mRequestCode);
                            } else {
                                // 未获取权限
                            }
                        });


                break;
            case R.id.order_id_edt:
                break;
            case R.id.order_wl_layout:
                showRegionDialog();
                break;
            case R.id.order_comment:
                break;
        }
    }

    private void showRegionDialog() {
        WLBottomDialogFragment.showDialog(this, "", new WLBottomDialogFragment.OnConfirmClickListener() {
            @Override
            public void onClick(String content) {
                wlContents.setText(content);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == mRequestCode && resultCode == 1) {
            String s = data.getStringExtra("content");
            orderIdEdt.setText(s);
            orderIdEdt.setSelection(s.length());
        }
    }


}
