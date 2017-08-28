package com.crv.ole.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lihongshi on 2017/8/8.
 * 底部弹窗
 */

public abstract class BaseBottomSheetDialogFragment extends BottomSheetDialogFragment {
    public static final String TAG = BaseBottomSheetDialogFragment.class.getName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), getLayoutResId(), container);
        return view;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (!this.isAdded()) {
            super.show(manager, tag);
        } else {
            Log.d(TAG, " has add to FragmentManager");
        }
    }

    //    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        Dialog dialog = getBottomDialog();
//        return dialog;
//    }
//
//    public Dialog getBottomDialog() {
//        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialogStyle);
//        dialog.setContentView(getDialogView());
//        dialog.setCanceledOnTouchOutside(true);
//        Window window = dialog.getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
//        window.setAttributes(lp);
//        return dialog;
//    }
//
//

    public abstract int getLayoutResId();

//    public View getDialogView() {
//        TextView textView = new TextView(getActivity());
//        textView.setText("Fragment must Override getDialogView()");
//        return textView;
//    }
}
