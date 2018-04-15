package cn.jungmedia.android.ui.setting.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leon.common.base.BaseFragment;
import com.leon.common.commonutils.ImageLoaderUtils;
import com.leon.common.commonutils.ToastUitl;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jungmedia.android.R;
import cn.jungmedia.android.api.ApiConstants;
import cn.jungmedia.android.app.AppIntent;
import cn.jungmedia.android.ui.setting.contract.SettingContract;
import cn.jungmedia.android.ui.setting.model.SettingModel;
import cn.jungmedia.android.ui.setting.presenter.SettingPresenter;
import cn.jungmedia.android.ui.user.bean.UserInfo;
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
 * @date 2018/3/12. 下午11:16
 *
 *
 */
public class SettingFragment extends BaseFragment<SettingPresenter, SettingModel> implements SettingContract.View {
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
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        if (MyUtils.isLogin()) {
            UserInfo userInfo = MyUtils.getUserInfoFromPreference(getActivity());
            if (userInfo != null && userInfo.getUser() != null) {
                ImageLoaderUtils.displayRound(getContext(), logoView, userInfo.getUser().getLogo());
                nickView.setText(userInfo.getUser().getNick());
                despView.setText(userInfo.getUser().getRemark());
            }

            logoutBtn.setVisibility(View.VISIBLE);
        } else {
            logoutBtn.setVisibility(View.GONE);
        }

        currVersionText.setText(MyUtils.getAppVersionName(getActivity()));
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

    @OnClick({R.id.userinfo_layout, R.id.account_safe_btn, R.id.clear_cache_layout, R.id.version_layout, R.id.about_us_layout, R.id.contact_us_layout, R.id.feedback_layout, R.id.logout_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.userinfo_layout:
                if (!MyUtils.isLogin()) {
                    AppIntent.intentToLogin(getActivity());
                    return;
                }
                AppIntent.intentToUserInfo(getActivity());
                break;
            case R.id.account_safe_btn:
                if (!MyUtils.isLogin()) {
                    AppIntent.intentToLogin(getActivity());
                    return;
                }
                AppIntent.intentToAccountSafe(getActivity());
                break;
            case R.id.clear_cache_layout:
                break;
            case R.id.version_layout:
                break;
            case R.id.about_us_layout:
                AppIntent.intentToCommonWeb(getActivity(), R.string.about_us, ApiConstants.URL_ABOUT);
                break;
            case R.id.contact_us_layout:
                AppIntent.intentToCommonWeb(getActivity(), R.string.contact_us, ApiConstants.URL_CONTACT);
                break;
            case R.id.feedback_layout:
                break;

            case R.id.logout_btn:

                mPresenter.logout();
                break;
        }
    }

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
    public void returnLogout(boolean result) {
        if (result) {
            MyUtils.clearUser();
            ToastUitl.showShort("注销成功");
            getActivity().finish();
        } else {

            ToastUitl.showShort("注销失败");
        }
    }
}
