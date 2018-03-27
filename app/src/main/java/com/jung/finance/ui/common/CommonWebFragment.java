package com.jung.finance.ui.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.jung.finance.R;
import com.jung.finance.app.AppConstant;
import com.jung.finance.app.AppIntent;
import com.leon.common.base.BaseFragment;
import com.leon.common.browser.HostJsScope;
import com.leon.common.browser.InjectedChromeClient;
import com.leon.common.browser.InnerWebViewClient;
import com.leon.common.browser.ProgressWebView;

import butterknife.Bind;
import butterknife.ButterKnife;


/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/3/27. 下午11:10
 *
 *
 */
public class CommonWebFragment extends BaseFragment {
    @Bind(R.id.common_web_main_web_view)
    ProgressWebView detailwebview;
    private Intent homeIntent;

    @Override
    protected int getLayoutResource() {
        return R.layout.com_web_layout;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {

        detailwebview.getSettings().setJavaScriptEnabled(true);
        detailwebview.canGoBack();
        detailwebview.setVerticalScrollBarEnabled(true);
        detailwebview.requestFocus();
        detailwebview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        detailwebview.setWebViewClient(new InnerWebViewClient(getActivity()));
        detailwebview.setWebChromeClient(new InjectedChromeClient("JsCallBack", InnerHostJsScope.class));
        detailwebview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                detailwebview.requestFocus();
                return false;
            }
        });
        ((CommonActivity) getActivity()).getNtb().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!detailwebview.canGoBack()) {
                    getActivity().finish();
                } else {
                    detailwebview.goBack();
                }
            }
        });

        homeIntent = getActivity().getIntent();
        String urlAddress = homeIntent.getStringExtra(AppConstant.FLAG_DATA);
        detailwebview.loadUrl(urlAddress);
    }

    @Override
    public void onResume() {
        super.onResume();
        detailwebview.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        detailwebview.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!detailwebview.canGoBack()) {
                // detailwebview.clearHistory();
                // detailwebview.clearCache(true);
                getActivity().finish();
            } else {
                detailwebview.goBack();
            }
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public class InnerHostJsScope extends HostJsScope {

        public void getSuccessFeedBack(final WebView webView, String message) {
            alert(webView, message);
        }

        public void getFailureFeedBack(WebView webView, String message) {
            alert(webView, message);
        }

        @Override
        public void getUrl(WebView webView, String param, String url) {
            if (url != null) {

                AppIntent.intentToCommonWeb(getActivity(), param, url);
            }
        }
    }
}
