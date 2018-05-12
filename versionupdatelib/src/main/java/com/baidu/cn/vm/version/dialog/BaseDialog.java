package com.baidu.cn.vm.version.dialog;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;

import com.baidu.cn.vm.util.CommonUtil;
import com.baidu.cn.vm.version.DialogInterface;

/**
 *
 */
public abstract class BaseDialog implements DialogInf {
    protected Context mContext;
    protected DialogInterface mDialogInterface;
    protected Dialog mDialog;
    android.content.DialogInterface.OnShowListener showListener;

    public BaseDialog(Context context, DialogInterface dialogInterface) {
        this(context, dialogInterface, null);
    }

    public BaseDialog(Context context, DialogInterface dialogInterface, android.content.DialogInterface.OnShowListener showListener) {
        mContext = context;
        mDialogInterface = dialogInterface;
        this.showListener = showListener;
    }

    public void setDialog(Dialog mDialog) {
        this.mDialog = mDialog;
    }

    public Dialog getDialog() {
        return mDialog;
    }

    @TargetApi(Build.VERSION_CODES.FROYO)
    @Override
    public void show() {
        if (mDialog == null) {
            return;
        }
        if (CommonUtil.isForeground(mContext, mContext.getClass().getName())) {
            try {
                mDialog.setOnShowListener(showListener);
                mDialog.show();
            } catch (Exception e) {
            }
        }
    }
}
