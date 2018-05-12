package com.baidu.cn.vm.version.dialog;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;

import com.baidu.cn.vm.version.DialogInterface;
import com.baidu.cn.vm.version.VersionInfo;

/**
 *
 */
public class Style5Dialog extends BaseDialog {

    public Style5Dialog(Context context, DialogInterface dialogInterface) {
        super(context, dialogInterface);
    }

    public Style5Dialog(Context context, DialogInterface dialogInterface, android.content.DialogInterface.OnShowListener showListener) {
        super(context, dialogInterface, showListener);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public DialogInf createDialog(VersionInfo info, int theme) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, theme);
        builder.setTitle("新版本安装提示");
        builder.setMessage("您确定要取消安装吗?");
        builder.setPositiveButton("取消", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(android.content.DialogInterface dialog, int which) {
                if (mDialogInterface != null) {
                    mDialogInterface.cancel(DialogInterface.Type.DEF_CANCEL);
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("继续安装", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(android.content.DialogInterface dialog, int which) {
                if (mDialogInterface != null) {
                    mDialogInterface.install();
                }
                dialog.dismiss();
            }
        });
        setDialog(builder.create());
        return this;
    }

}
