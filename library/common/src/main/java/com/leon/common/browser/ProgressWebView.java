package com.leon.common.browser;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.leon.common.R;
import com.leon.common.commonutils.DisplayUtil;

/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/3/27. 下午11:26
 *
 *
 */
public class ProgressWebView extends LinearLayout {

    protected WebView webView;
    protected ProgressBar progressbar;


    public ProgressWebView(Context context) {
        this(context, null);
    }

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
        progressbar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        progressbar.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, DisplayUtil.dip2px(3)));
        progressbar.setProgressDrawable(getResources().getDrawable(R.drawable.view_progressbar_horizontal));
        addView(progressbar);

        webView = new WebView(context, attrs);
        addView(webView,new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        webView.setWebChromeClient(new ProgressWebChromeClient());
    }

    public WebView getWebView() {
        return webView;
    }

    public ProgressBar getProgressbar() {
        return progressbar;
    }

    public class ProgressWebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressbar.setVisibility(GONE);
            } else {
                if (progressbar.getVisibility() == GONE)
                    progressbar.setVisibility(VISIBLE);
                progressbar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

    }
}