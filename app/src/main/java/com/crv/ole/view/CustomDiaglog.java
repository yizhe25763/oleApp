package com.crv.ole.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.crv.ole.R;
import com.crv.sdk.utils.ScreenUtil;
import com.crv.sdk.utils.TextUtil;

import butterknife.BindView;

/**
 * 黑色背景自定义dialog
 * Created by zhangbo on 2017/6/19.
 */

public class CustomDiaglog extends Dialog implements View.OnClickListener {

    @BindView(R.id.dialog_title)
    TextView dialog_title;

    @BindView(R.id.cancle)
    TextView cancle;

    @BindView(R.id.confim)
    TextView confim;

    private Context context;

    private LayoutInflater inflater;

    private OnConfimClickListerner onConfimClickListerner;

    private String tx_title;
    private String tx_cancle;
    private String tx_confim;


    public CustomDiaglog(Context context, String tx_title) {
        super(context, R.style.Dialog);
        this.context = context;
        this.tx_title = tx_title;
        inflater = LayoutInflater.from(context);
        initView();
    }

    public CustomDiaglog(Context context, String tx_title, String tx_cancle, String tx_confim) {
        super(context, R.style.Dialog);
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.tx_title = tx_title;
        this.tx_cancle = tx_cancle;
        this.tx_confim = tx_confim;
        initView();
    }


    private void initView() {
        View view = inflater.inflate(R.layout.black_bg_dialog_layout, null);
        setContentView(view);
        dialog_title = (TextView) view.findViewById(R.id.dialog_title);
        cancle = (TextView) view.findViewById(R.id.cancle);
        confim = (TextView) view.findViewById(R.id.confim);
        cancle.setOnClickListener(this);
        confim.setOnClickListener(this);

        if (!TextUtil.isEmpty(tx_title)) {
            dialog_title.setText(tx_title);
        }
        if (!TextUtil.isEmpty(tx_cancle)) {
            cancle.setText(tx_cancle);
        }
        if (!TextUtil.isEmpty(tx_confim)) {
            confim.setText(tx_confim);
        }
        Window window = getWindow(); //得到对话框
        WindowManager.LayoutParams wl = window.getAttributes(); //根据x，y坐标设置窗口需要显示的位置
        wl.gravity = Gravity.CENTER;
        wl.width = ScreenUtil.getInstance(context).getScreenWidth() * 4 / 5;
        wl.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wl.alpha = 1.0f;
        window.setAttributes(wl); //设置触摸对话框以外的地方取消对话框
        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }

    public CustomDiaglog setOnConfimClickListerner(OnConfimClickListerner onConfimClickListerner) {
        this.onConfimClickListerner = onConfimClickListerner;
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancle:
                if (onConfimClickListerner!=null){
                    onConfimClickListerner.onCanleClick();
                    dismiss();
                }
                break;
            case R.id.confim:
                if (onConfimClickListerner!=null){
                    onConfimClickListerner.OnConfimClick();
                    dismiss();
                }
                break;
        }
    }

    public interface OnConfimClickListerner {
        void onCanleClick();

        void OnConfimClick();
    }
}
