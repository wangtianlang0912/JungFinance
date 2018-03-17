package com.leon.common.ui;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leon.common.R;
import com.leon.common.commonutils.DisplayUtil;


public class DuAlertDialog {


    public Builder createBuilder(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

            return new Builder(context, R.style.Theme_Custom_Dialog_Alert);
        } else {
            return new Builder(context);
        }
    }

    public Builder createBottomBuilder(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

            return new Builder(context, R.style.BottomDialogStyle);
        } else {
            return new Builder(context);
        }
    }


    public ProgressBuilder createProgressBuilder(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

            return new ProgressBuilder(context, R.style.Theme_Custom_Dialog_Alert);
        } else {
            return new ProgressBuilder(context);
        }
    }

    public class Builder extends AlertDialog.Builder {

        private AlertDialog mAlertDialog;

        private Builder(Context context) { //2.3的theme在AlertDialog中设置，builder没有提供入口
            super(context);
        }

        @TargetApi(11)
        private Builder(Context context, int theme) {
            super(context, theme);

        }

        public AlertDialog create() {
            mAlertDialog = super.create();
            mAlertDialog.setOnShowListener(getShowListener());
            return mAlertDialog;
        }
    }

    public class ProgressBuilder extends ProgressDialog.Builder {

        private ProgressDialog mAlertDialog;

        private ProgressBuilder(Context context) { //2.3的theme在AlertDialog中设置，builder没有提供入口
            super(context);
            create(context, 0);
        }

        @TargetApi(11)
        private ProgressBuilder(Context context, int theme) {
            super(context, theme);
            create(context, theme);
        }

        private ProgressDialog create(Context context, int theme) {
            if (theme == 0) {
                mAlertDialog = new ProgressDialog(context);
            } else {
                mAlertDialog = new ProgressDialog(context, theme);
            }
            mAlertDialog.setOnShowListener(getShowListener());
            return mAlertDialog;
        }

        public ProgressDialog getAlertDialog() {
            return mAlertDialog;
        }
    }

    public OnShowListener getShowListener() {

        return new OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {
                setStyle((Dialog) dialog);
            }
        };
    }


    private void setStyle(Dialog context) {
        setBackgroundColor(context, "android:id/titleDivider", R.color.black);
        setBackgroundColor(context, "android:id/titleDividerTop", R.color.alert_divider_color);
        setVisibility(context, "android:id/titleDividerTop", View.GONE);
        setVisibility(context, "android:id/topPanel", View.GONE);
        setContentGravity(context, "android:id/message");
        setContentPannelStyle(context, "android:id/contentPanel");
        setBackgroundColor(context, "android:id/contentPanel", R.drawable.du_dialog_bg_content);
        setBackgroundColor(context, "android:id/buttonPanel", R.drawable.du_dialog_bg_button);
        setBackgroundColor(context, "android:id/customPanel", R.color.white);
    }

    public OnShowListener getVersionDialogShowListener() {

        return new OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {
                setStyleWithTitle((Dialog) dialog);
            }
        };
    }

    /***
     * 版本更新相关样式
     *
     * @param context
     */
    private void setStyleWithTitle(Dialog context) {
        setBackgroundColor(context, "android:id/titleDivider", R.drawable.du_alert_divider_horizontal);
        setLineHeight(context, "android:id/titleDivider", 1);
        setBackgroundColor(context, "android:id/titleDividerTop", R.color.alert_divider_color);
        setBackgroundColor(context, "android:id/topPanel", R.drawable.du_dialog_bg_content);
        setBackgroundColor(context, "android:id/contentPanel", R.color.white);
        setBackgroundColor(context, "android:id/buttonPanel", R.drawable.du_dialog_bg_button);
        setBackgroundColor(context, "android:id/customPanel", R.color.white);

    }

    public OnShowListener getListShowListener() {

        return new OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {
                setListStyle((Dialog) dialog);
            }
        };
    }


    private void setListStyle(Dialog context) {
        setBackgroundColor(context, "android:id/titleDivider", R.color.black);
        setBackgroundColor(context, "android:id/titleDividerTop", R.color.alert_divider_color);
        setLineHeight(context, "android:id/titleDivider", 1);
        setBackgroundColor(context, "android:id/topPanel", R.drawable.du_dialog_bg_content);
        setBackgroundColor(context, "android:id/contentPanel", R.color.white);
        setContentGravity(context, "android:id/message");
        setContentPannelStyle(context, "android:id/contentPanel");
        setBackgroundColor(context, "android:id/customPanel", R.drawable.du_dialog_bg_content);

    }



    private void setTitlePadding(Dialog dialog, String residStr, int padding) {

        View view = getView(dialog, residStr);
        if (view != null) {
            int paddingPx = DisplayUtil.dip2px(padding);
            view.setPadding(paddingPx, paddingPx, paddingPx, paddingPx);
        }
    }

    private void setLineHeight(Dialog dialog, String residStr, int height) {
        View view = getView(dialog, residStr);
        if (view != null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = DisplayUtil.dip2px(height);
        }
    }


    private void setBackgroundDrawable(Dialog dialog, String residStr, int resId) {

        View view = getView(dialog, residStr);
        if (view != null) {

            view.setBackgroundResource(resId);
        }

    }

    private View getView(Dialog dialog, String residStr) {
        try {
            int viewId = dialog.getContext().getResources()
                    .getIdentifier(residStr, null, null);
            return dialog.findViewById(viewId);
        } catch (Exception e) {
        }
        return null;
    }

    private void setContentPannelStyle(Dialog dialog, String residStr) {
        View view = getView(dialog, residStr);
        if (view != null) {
            int padding = DisplayUtil.dip2px(10);
            int paddingH = DisplayUtil.dip2px(15);

            view.setPadding(padding, paddingH, padding, paddingH);
        }
    }

    private void setContentGravity(Dialog dialog, String residStr) {
        View view = getView(dialog, residStr);

        if (view != null) {
            ((TextView) view).setGravity(Gravity.CENTER);
        }


    }

    private void setBackgroundColor(Dialog dialog, String residStr, int resid) {
        View view = getView(dialog, residStr);

        if (view != null) {
            view.setBackgroundResource(resid);
        }
    }

    private void setVisibility(Dialog dialog, String residStr, int visibility) {
        View view = getView(dialog, residStr);

        if (view != null) {
            view.setVisibility(visibility);
        }
    }
}
