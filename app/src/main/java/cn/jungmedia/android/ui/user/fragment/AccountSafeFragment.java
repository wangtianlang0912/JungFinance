package com.jung.android.ui.user.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jung.android.app.AppIntent;
import com.jung.finance.R;
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
 * @date 2018/3/13. 上午12:00
 *
 *
 */
public class AccountSafeFragment extends BaseFragment {
    @Bind(R.id.bind_phone_arrow)
    ImageView bindPhoneArrow;
    @Bind(R.id.bind_phone_txt)
    TextView bindPhoneTxt;
    @Bind(R.id.bind_mobile_layout)
    RelativeLayout bindMobileLayout;
    @Bind(R.id.update_pwd_layout)
    RelativeLayout updatePwdLayout;

    @Override
    protected int getLayoutResource() {
        return R.layout.fra_account_safe;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {

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

    @OnClick({R.id.bind_mobile_layout, R.id.update_pwd_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bind_mobile_layout:

                AppIntent.intentToBindMobile(getActivity());
                break;
            case R.id.update_pwd_layout:
                AppIntent.intentToUpdatePwd(getActivity());
                break;
        }
    }
}
