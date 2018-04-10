package com.jung.finance.ui.main.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jung.finance.R;
import com.jung.finance.app.AppConstant;
import com.jung.finance.app.AppIntent;
import com.jung.finance.bean.ActivityFavModel;
import com.jung.finance.ui.common.CommonActivity;
import com.jung.finance.ui.main.contract.ActivityDetailContract;
import com.jung.finance.ui.main.model.ActivityDetailModelImp;
import com.jung.finance.ui.main.presenter.ActivityDetailPresenterImp;
import com.jung.finance.ui.news.fragment.ArticleDetailFragment;
import com.jung.finance.utils.MyUtils;
import com.leon.common.base.BaseFragment;
import com.leon.common.browser.InjectedChromeClient;
import com.leon.common.browser.InnerWebViewClient;
import com.leon.common.browser.ProgressWebView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/4/7. 下午4:25
 *
 *
 */
public class ActivityInfoFragment extends BaseFragment<ActivityDetailPresenterImp, ActivityDetailModelImp> implements ActivityDetailContract.View {


    @Bind(R.id.fav_layout)
    LinearLayout favLayout;
    @Bind(R.id.attent_view)
    TextView attentView;
    @Bind(R.id.fav_view)
    ImageView favView;
    int activityId;
    @Bind(R.id.common_web_main_web_view)
    ProgressWebView progressWebView;

    WebView detailwebview;

    @Override
    public void showLoading(String title) {
        showProgressBar();
    }

    @Override
    public void stopLoading() {
        dismissProgressBar();
    }

    @Override
    public void showErrorTip(String msg) {
        showShortToast(msg);
    }

    @Override
    public void returnAttentActivity(boolean result) {

    }

    @Override
    public void returnFavActivityState(ActivityFavModel.Favorite result) {

        if (result != null) {
            favView.setTag(result.getObjectId());
            favView.setImageResource(R.drawable.icon_fav_s);
        } else {

            favView.setImageResource(R.drawable.icon_fav_n);
            favView.setTag(null);
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_detail_layout;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {

        detailwebview = progressWebView.getWebView();
        detailwebview.getSettings().setJavaScriptEnabled(true);
        detailwebview.getSettings().setDefaultTextEncodingName("utf-8");// 设置编码
        detailwebview.setBackgroundColor(Color.argb(0, 0, 0, 0));// 设置背景颜色透明
        detailwebview.canGoBack();
        String userAgent = detailwebview.getSettings().getUserAgentString();
        detailwebview.getSettings().setUserAgentString(userAgent + ";" + "cn.efunding.app");
        detailwebview.setVerticalScrollBarEnabled(true);
        detailwebview.requestFocus();
        detailwebview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        detailwebview.setWebViewClient(new InnerWebViewClient(getActivity()));
        detailwebview.setWebChromeClient(new InjectedChromeClient(progressWebView.getProgressbar(), "JsCallBack", ArticleDetailFragment.InnerHostJsScope.class));
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


        Intent homeIntent = getActivity().getIntent();
        Bundle bundle = homeIntent.getBundleExtra(AppConstant.FLAG_BUNDLE);
        String url = bundle.getString(AppConstant.FLAG_DATA);
        detailwebview.loadUrl(url);
        activityId = bundle.getInt(AppConstant.FLAG_DATA2);
        mPresenter.getFavActivityState(activityId);
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

    @OnClick({R.id.fav_layout, R.id.attent_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fav_layout:
                Object tag = favView.getTag();
                if (tag == null) {
                    favBtnClicked(activityId);
                } else {
                    unFavBtnClicked((int) tag);
                }
                break;
            case R.id.attent_view:
                break;
        }
    }

    private void unFavBtnClicked(int favItemId) {

        if (favItemId <= 0) {
            return;
        }
        if (!MyUtils.isLogin()) {
            AppIntent.intentToLogin(getContext());
            return;
        }
        mPresenter.favActionActivity(favItemId, true);

    }

    private void favBtnClicked(int activityId) {

        if (activityId <= 0) {
            return;
        }
        if (!MyUtils.isLogin()) {
            AppIntent.intentToLogin(getContext());
            return;
        }
        mPresenter.favActionActivity(activityId, false);
    }
}
