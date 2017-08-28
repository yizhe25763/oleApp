package com.crv.ole.utils;

import android.content.Context;
import android.content.DialogInterface;

import com.crv.sdk.dialog.AlertDialog;
import com.crv.sdk.utils.TextUtil;

/**
 * Created by lihongshi on 2017/8/2.
 * 对话框工具类
 */

public class DialogUtil {
    /**
     * @param title         标题
     * @param desc          描述
     * @param positive      第一个按钮标题
     * @param negative      第二个按钮标题
     * @param positiveClick 第一个按钮点击回调
     * @param negativeClick 第二哥按钮点击回调
     */
    public static void showAlertDialog(Context context, String title, String desc, String positive, String negative,
                                       DialogInterface.OnClickListener positiveClick, DialogInterface.OnClickListener negativeClick) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if (!TextUtil.isEmpty(title)) {
            builder.setTitle(title);
        }
        if (TextUtil.isEmpty(desc)) {
            desc = "";
        }
        if (TextUtil.isEmpty(positive)) {
            positive = "确定";
        }
        if (TextUtil.isEmpty(negative)) {
            negative = "取消";
        }

        builder.setMessage(desc);
        builder.setPositiveButton(positive, positiveClick);
        builder.setNegativeButton(negative, negativeClick);
        builder.create().show();
    }
}
