package cn.jungmedia.android.ui.main.fragment;

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

import com.leon.common.base.BaseFragment;
import com.leon.common.browser.InjectedChromeClient;
import com.leon.common.browser.InnerWebViewClient;
import com.leon.common.browser.ProgressWebView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jungmedia.android.R;
import cn.jungmedia.android.app.AppConstant;
import cn.jungmedia.android.app.AppIntent;
import cn.jungmedia.android.bean.ActivityFavModel;
import cn.jungmedia.android.bean.ActivityModel;
import cn.jungmedia.android.ui.common.CommonActivity;
import cn.jungmedia.android.ui.common.CommonWebFragment;
import cn.jungmedia.android.ui.main.contract.ActivityDetailContract;
import cn.jungmedia.android.ui.main.model.ActivityDetailModelImp;
import cn.jungmedia.android.ui.main.presenter.ActivityDetailPresenterImp;
import cn.jungmedia.android.utils.MyUtils;


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
    @Bind(R.id.action_view)
    TextView actionView;
    @Bind(R.id.fav_view)
    ImageView favView;
    ActivityModel.Activity mActivity;
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
    public void returnFavActivityState(ActivityFavModel.Favorite result, boolean fav, boolean toastRefer) {

        if (toastRefer) {
            favView.setTag(result.getObjectId());
            favView.setImageResource(R.drawable.icon_fav_s);
            if (toastRefer) {
                showShortToast("收藏成功");
            }

        } else {

            favView.setImageResource(R.drawable.icon_fav_n);
            favView.setTag(null);
            if (toastRefer) {
                showShortToast("取消收藏");
            }
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
        detailwebview.setWebChromeClient(new InjectedChromeClient(progressWebView.getProgressbar(), "JsCallBack", CommonWebFragment.InnerHostJsScope.class));
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
        mActivity = (ActivityModel.Activity) bundle.getSerializable(AppConstant.FLAG_DATA2);
        mPresenter.getFavActivityState(mActivity.getObjectId());


        updateActionBtnState(mActivity);

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

    @OnClick({R.id.fav_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fav_layout:
                Object tag = favView.getTag();
                if (tag == null) {
                    favBtnClicked(mActivity.getObjectId());
                } else {
                    unFavBtnClicked((int) tag);
                }
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

    private void updateActionBtnState(ActivityModel.Activity activity) {

        if (activity == null) {
            return;
        }
        if (activity.getStatus() == ActivityModel.Status.IDLE) {
            actionView.setText("未开始");
            actionView.setBackgroundColor(getResources().getColor(R.color._86C789));
            actionView.setOnClickListener(null);
        } else if (activity.getStatus() == ActivityModel.Status.SIGNUP) {
            actionView.setText("报名");
            actionView.setBackgroundColor(getResources().getColor(R.color._86C711));
            actionView.setOnClickListener(getOnSignUpListener(activity));
        } else if (activity.getStatus() == ActivityModel.Status.START) {
            actionView.setText("正在进行中");
            actionView.setBackgroundColor(getResources().getColor(R.color._86C789));
            actionView.setOnClickListener(null);
        } else if (activity.getStatus() == ActivityModel.Status.FINISH) {
            actionView.setText("已结束");
            actionView.setBackgroundColor(getResources().getColor(R.color._86C789));
            actionView.setOnClickListener(null);
        }
    }

    private View.OnClickListener getOnSignUpListener(final ActivityModel.Activity activity) {

        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!MyUtils.isLogin()) {
                    AppIntent.intentToLogin(getContext());
                    return;
                }
                AppIntent.intentToActivitySignup(getActivity(), activity.getObjectId());
            }
        };
    }
}
