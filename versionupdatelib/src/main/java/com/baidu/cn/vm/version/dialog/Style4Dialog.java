package com.baidu.cn.vm.version.dialog;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;

import com.baidu.cn.vm.util.CommonUtil;
import com.baidu.cn.vm.version.ProgressObserver;

/**
 * Created by zhangmeng on 16/5/12.
 */
public class Style4Dialog implements ProgressObserver {
    private boolean isForce;
    private boolean isClosed;
    ProgressDialog dialog = null;
    private Context mContext;

    @TargetApi(Build.VERSION_CODES.FROYO)
    public Style4Dialog(Context context, boolean isForce, int theme, DialogInterface.OnShowListener showListener) {
        this.isForce = isForce;
        this.mContext = context;
        dialog = new ProgressDialog(mContext, theme);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setOnShowListener(showListener);
    }

    @Override
    public void onChanged(int progress) {
        try {
            if (dialog != null && !dialog.isShowing() && !isClosed) {
                dialog.setTitle("下载中...");
                if (!isForce) {
                    dialog.setButton("后台加载", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            isClosed = true;
                            dialog.dismiss();
                        }
                    });
                }
                dialog.setCanceledOnTouchOutside(!isForce);
                dialog.setCancelable(!isForce);
                if (CommonUtil.isForeground(mContext, mContext.getClass().getName())) {
                    dialog.show();
                }
            }

            if (progress == 100) {
                dialog.dismiss();
                dialog.cancel();
            } else if (progress == -1) {
                dialog.dismiss();
                dialog.cancel();
            }
            if (!isClosed) {
                dialog.setProgress(progress);
            }
        } catch (Exception e) {
        }
    }
}
