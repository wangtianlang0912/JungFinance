package com.baidu.cn.vm.version.dialog;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.view.KeyEvent;

import com.baidu.cn.vm.version.DialogInterface;
import com.baidu.cn.vm.version.VersionInfo;

/**
 *
 */
public class Style3Dialog extends BaseDialog {

    public Style3Dialog(Context context, DialogInterface dialogInterface) {
        super(context, dialogInterface);
    }

    public Style3Dialog(Context context, DialogInterface dialogInterface, android.content.DialogInterface.OnShowListener showListener) {
        super(context, dialogInterface, showListener);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public DialogInf createDialog(VersionInfo info, int theme) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, theme);
        builder.setTitle("发现新版本:" + info.getVersionName());
        builder.setMessage(info.getDescribe());
        builder.setPositiveButton("更新", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(android.content.DialogInterface dialog, int which) {
                if (mDialogInterface != null) {
                    mDialogInterface.download();
                }
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        //解决部分手机点击search按钮返回的问题
        dialog.setOnKeyListener(new android.content.DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(android.content.DialogInterface dialog, int keyCode, KeyEvent event) {
                //默认返回 false
                return keyCode == KeyEvent.KEYCODE_SEARCH;
            }
        });
        setDialog(dialog);
        return this;
    }

}
