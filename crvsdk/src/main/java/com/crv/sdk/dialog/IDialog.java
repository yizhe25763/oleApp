package com.crv.sdk.dialog;

import android.content.DialogInterface.OnDismissListener;
import com.crv.sdk.dialog.DialogFrag.DialogCallBackListerner;


/**
 * @author ljf
 * @version 2014年5月20日 下午5:45:36
 */
public interface IDialog {

	void showDialog(final int id);

	void showDialog(final String msg);

	void showDialog(final int id, int resid);

	void showDialog(final String msg, DialogCallBackListerner callback);

	void dissmissDialog();

	void showProgressDialog(final int id);
	void showProgressDialog(final String msg, DialogCallBackListerner callback);

	void showRetryDialog(final int id, DialogCallBackListerner callback);

	boolean isShow();

	void showDialog(String title, String msg, DialogCallBackListerner callback);

	void  showDialog(String title, String msg, int layoutId, DialogCallBackListerner callback);
	
	void  showDialog(String title, String msg, int layoutId, boolean dismiss, DialogCallBackListerner callback, OnDismissListener onDismiss);
}
