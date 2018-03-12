package com.jung.finance.ui.setting.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
 * @date 2018/3/12. 下午11:16
 *
 *
 */
public class SettingFragment extends BaseFragment {
    @Bind(R.id.logo_view)
    ImageView logoView;
    @Bind(R.id.nick_view)
    TextView nickView;
    @Bind(R.id.desp_view)
    TextView despView;
    @Bind(R.id.userinfo_layout)
    LinearLayout userinfoLayout;
    @Bind(R.id.account_safe_btn)
    RelativeLayout accountSafeBtn;
    @Bind(R.id.clear_cache_arrow)
    ImageView clearCacheArrow;
    @Bind(R.id.cache_text)
    TextView cacheText;
    @Bind(R.id.clear_cache_layout)
    RelativeLayout clearCacheLayout;
    @Bind(R.id.curr_version_text)
    TextView currVersionText;
    @Bind(R.id.version_layout)
    RelativeLayout versionLayout;
    @Bind(R.id.about_us_layout)
    RelativeLayout aboutUsLayout;
    @Bind(R.id.contact_us_layout)
    RelativeLayout contactUsLayout;
    @Bind(R.id.feedback_layout)
    RelativeLayout feedbackLayout;
    @Bind(R.id.logout_btn)
    Button logoutBtn;

    @Override
    protected int getLayoutResource() {
        return R.layout.fra_setting;
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

    @OnClick({R.id.userinfo_layout, R.id.account_safe_btn, R.id.clear_cache_layout, R.id.version_layout, R.id.about_us_layout, R.id.contact_us_layout, R.id.feedback_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.userinfo_layout:
                break;
            case R.id.account_safe_btn:
                break;
            case R.id.clear_cache_layout:
                break;
            case R.id.version_layout:
                break;
            case R.id.about_us_layout:
                break;
            case R.id.contact_us_layout:
                break;
            case R.id.feedback_layout:
                break;
        }
    }
}
