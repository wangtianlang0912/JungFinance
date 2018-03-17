package com.leon.common.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.leon.common.R;


/**
 * 加载进度框
 */
public class ProgressDialog extends Dialog {
    private Dismiss dismiss;

    public interface Dismiss {
        public void onDismiss();
    }

    public ProgressDialog(Context context) {
        super(context, R.style.progress_dialog_style);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View v = inflater.inflate(R.layout.du_progress_dialog, null);
        setContentView(v, new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));

    }

    public Dismiss getDismiss() {
        return dismiss;
    }

    public void setDismiss(Dismiss dismiss) {
        this.dismiss = dismiss;
    }

    @Override
    public void show() {
        super.show();
    }

}
