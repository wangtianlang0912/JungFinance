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
import android.widget.ProgressBar;

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
import cn.sharesdk.onekeyshare.OnekeyShare;


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

        ((CommonActivity) getActivity()).getNtb().setRightTitle(getString(R.string.share));
        ((CommonActivity) getActivity()).getNtb().setRightTitleVisibility(true);
        ((CommonActivity) getActivity()).getNtb().setOnRightTextListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnekeyShare oks = new OnekeyShare();
                //关闭sso授权
                oks.disableSSOWhenAuthorize();
                // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
                oks.setTitle("Yizhi");
                // titleUrl是标题的网络链接，QQ和QQ空间等使用
                oks.setTitleUrl("https://github.com/Horrarndoo/YiZhi");
                // text是分享文本，所有平台都需要这个字段
                oks.setText("每日新闻，精选干货，最新资讯，应有尽有.项目详情链接：https://github.com/Horrarndoo/YiZhi");
                // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
                //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
                // url仅在微信（包括好友和朋友圈）中使用
                //由于微信需要注册AppKey才能演示，这里取消微信分享，个人根据自己的需求注册Appkey使用
                oks.setUrl("https://github.com/Horrarndoo/YiZhi");
                // comment是我对这条分享的评论，仅在人人网和QQ空间使用
                oks.setComment("这个App贼好用，快下载体验吧~");
                // site是分享此内容的网站名称，仅在QQ空间使用
                oks.setSite(getString(R.string.app_name));
                // siteUrl是分享此内容的网站地址，仅在QQ空间使用
                oks.setSiteUrl("https://github.com/Horrarndoo/YiZhi");
                // 启动分享GUI
                oks.show(getActivity());
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

        public void login(WebView webView) {


        }

        public void intentToBloggerInfo(WebView webView) {

        }

    }

    private class MInjectedChromeClient extends InjectedChromeClient {

        public MInjectedChromeClient(ProgressBar progressbar, String injectedName, Class injectedCls) {
            super(progressbar, injectedName, injectedCls);
        }
    }
}
