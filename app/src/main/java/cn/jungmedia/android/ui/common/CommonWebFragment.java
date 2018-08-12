package cn.jungmedia.android.ui.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.leon.common.base.BaseFragment;
import com.leon.common.browser.HostJsScope;
import com.leon.common.browser.InjectedChromeClient;
import com.leon.common.browser.InnerWebViewClient;
import com.leon.common.browser.ProgressWebView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jungmedia.android.R;
import cn.jungmedia.android.app.AppConstant;
import cn.jungmedia.android.app.AppIntent;
import cn.jungmedia.android.utils.MyUtils;
import cn.jungmedia.android.utils.ShareHelper;


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
    protected ProgressWebView progressWebView;
    protected WebView detailwebview;
    protected Intent homeIntent;

    @Override
    protected int getLayoutResource() {
        return R.layout.com_web_layout;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        detailwebview = progressWebView.getWebView();
        detailwebview.getSettings().setJavaScriptEnabled(true);
        detailwebview.canGoBack();
        String userAgent = detailwebview.getSettings().getUserAgentString();
        detailwebview.getSettings().setUserAgentString(userAgent + ";" + "cn.efunding.app");
        detailwebview.setVerticalScrollBarEnabled(true);
        detailwebview.requestFocus();
        detailwebview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        detailwebview.setWebViewClient(new InnerWebViewClient(getActivity()));
        detailwebview.setWebChromeClient(new MInjectedChromeClient(progressWebView.getProgressbar(), "JsCallBack", InnerHostJsScope.class));
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
        final Bundle bundle = homeIntent.getBundleExtra(AppConstant.FLAG_BUNDLE);

        boolean shareEnable = bundle.getBoolean(AppConstant.FLAG_DATA2, true);
        ((CommonActivity) getActivity()).getNtb().setRightTitleVisibility(shareEnable);
        ((CommonActivity) getActivity()).getNtb().setRightTitle(getString(R.string.share));
        ((CommonActivity) getActivity()).getNtb().setOnRightTextListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlAddress = bundle.getString(AppConstant.FLAG_DATA);
                new ShareHelper().share(getActivity(), "分享自" + MyUtils.getAppName(getActivity()), "", urlAddress);
            }
        });

        loadData();
    }

    protected void loadData() {

        homeIntent = getActivity().getIntent();
        Bundle bundle = homeIntent.getBundleExtra(AppConstant.FLAG_BUNDLE);
        String urlAddress = bundle.getString(AppConstant.FLAG_DATA);
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

    public static class InnerHostJsScope extends HostJsScope {

        public static void getUrl(WebView webView, String param, String url) {
            if (url != null) {

                AppIntent.intentToCommonWeb(webView.getContext(), param, url);
            }
        }
    }

    private class MInjectedChromeClient extends InjectedChromeClient {

        public MInjectedChromeClient(ProgressBar progressbar, String injectedName, Class injectedCls) {
            super(progressbar, injectedName, injectedCls);
        }
    }
}
