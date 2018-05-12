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
public class Style1Dialog extends BaseDialog {

    public Style1Dialog(Context context, DialogInterface dialogInterface) {
        super(context, dialogInterface);
    }

    public Style1Dialog(Context context, DialogInterface dialogInterface, android.content.DialogInterface.OnShowListener showListener) {
        super(context, dialogInterface, showListener);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public DialogInf createDialog(VersionInfo info, int theme) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext,theme);
        builder.setTitle("发现新版本:" + info.getVersionName());
        builder.setMessage(info.getDescribe());
        builder.setPositiveButton("立即更新", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(android.content.DialogInterface dialog, int which) {
                if (mDialogInterface != null) {
                    mDialogInterface.download();
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("下次再说", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(android.content.DialogInterface dialog, int which) {
                if (mDialogInterface != null) {
                    mDialogInterface.cancel(DialogInterface.Type.UPDATE_CANCEL);
                }
                dialog.dismiss();
            }
        });
        setDialog(builder.create());
        return this;
    }

}
