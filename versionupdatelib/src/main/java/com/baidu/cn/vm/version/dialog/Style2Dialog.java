package com.baidu.cn.vm.version.dialog;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;

import com.baidu.cn.vm.version.VersionInfo;
import com.baidu.cn.vm.version.DialogInterface;

/**
 *
 */
public class Style2Dialog extends BaseDialog {

    public Style2Dialog(Context context, DialogInterface dialogInterface) {
        super(context, dialogInterface);
    }

    public Style2Dialog(Context context, DialogInterface dialogInterface, android.content.DialogInterface.OnShowListener showListener) {
        super(context, dialogInterface, showListener);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public DialogInf createDialog(VersionInfo info, int theme) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext,theme);
        builder.setTitle("新版本已下载完成是否安装");
        builder.setMessage(info.getDescribe());
        builder.setPositiveButton("安装", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(android.content.DialogInterface dialog, int which) {
                if (mDialogInterface != null) {
                    mDialogInterface.install();
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(android.content.DialogInterface dialog, int which) {
                        if (mDialogInterface != null) {
                            mDialogInterface.cancel(DialogInterface.Type.INSTALL_CANCEL);
                        }
                        dialog.dismiss();
                    }
                });
        setDialog(builder.create());
        return this;
    }

}
