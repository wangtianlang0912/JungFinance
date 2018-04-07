package com.jung.finance.ui.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jung.finance.R;
import com.jung.finance.app.AppConstant;
import com.jung.finance.app.AppIntent;
import com.jung.finance.ui.main.contract.ActivityDetailContract;
import com.jung.finance.ui.main.model.ActivityDetailModelImp;
import com.jung.finance.ui.main.presenter.ActivityDetailPresenterImp;
import com.jung.finance.utils.MyUtils;
import com.leon.common.base.BaseFragment;

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
    public void returnFavActivityState(boolean result) {
        favView.setImageResource(result ? R.drawable.icon_fav_s : R.drawable.icon_fav_n);
        favView.setTag(result);
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
        Intent homeIntent = getActivity().getIntent();
        Bundle bundle = homeIntent.getBundleExtra(AppConstant.FLAG_BUNDLE);
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
                boolean hasFav = false;
                if (tag != null) {
                    hasFav = (boolean) tag;
                }
                favBtnClicked(hasFav);

                break;
            case R.id.attent_view:
                break;
        }
    }

    private void favBtnClicked(boolean hasFav) {

        if (activityId <= 0) {
            return;
        }
        if (!MyUtils.isLogin()) {
            AppIntent.intentToLogin(getContext());
            return;
        }
        mPresenter.favActionActivity(activityId, hasFav);
    }
}
