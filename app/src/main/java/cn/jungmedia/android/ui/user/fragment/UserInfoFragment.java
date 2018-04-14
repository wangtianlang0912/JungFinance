package com.jung.android.ui.user.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jung.android.ui.user.bean.UserInfo;
import com.jung.android.ui.user.presenter.UserInfoPresenterImp;
import com.jung.finance.R;
import com.jung.android.ui.user.model.UserInfoModelImp;
import com.jung.android.ui.user.presenter.UserContract;
import com.jung.android.utils.MyUtils;
import com.leon.common.base.BaseFragment;
import com.leon.common.commonutils.ImageLoaderUtils;
import com.leon.common.commonutils.ToastUitl;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/***
 *
 * @Copyright 2018
 *
 * @TODO 用户信息页面
 *
 * @author niufei
 *
 *
 * @date 2018/3/12. 下午11:02
 *
 *
 */
public class UserInfoFragment extends BaseFragment<UserInfoPresenterImp, UserInfoModelImp> implements UserContract.IUserInfoView {


    @Bind(R.id.logo_view)
    ImageView logoView;
    @Bind(R.id.nick_text)
    TextView nickText;
    @Bind(R.id.nick_edit)
    EditText nickEdit;
    @Bind(R.id.desp_text)
    TextView despText;
    @Bind(R.id.desp_edit)
    EditText despEdit;
    @Bind(R.id.submit_btn)
    Button submitBtn;

    private UserInfo userInfo;

    @Override
    protected int getLayoutResource() {
        return R.layout.fra_userinfo;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        if (MyUtils.isLogin()) {
            userInfo = MyUtils.getUserInfoFromPreference(getActivity());
            if (userInfo != null && userInfo.getUser() != null) {
                ImageLoaderUtils.displayRound(getContext(), logoView, userInfo.getUser().getLogo());
                nickEdit.setText(userInfo.getUser().getNick());
                despEdit.setText(userInfo.getUser().getRemark());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.submit_btn)
    public void onViewClicked() {

        String nick = nickEdit.getText().toString().trim();
        String desp = despEdit.getText().toString().trim();

        if (userInfo != null && userInfo.getUser() != null) {
            mPresenter.submit(nick, desp, userInfo.getUser().getPhone(), userInfo.getUser().getLogo());
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
        ToastUitl.showShort(msg);
    }

    @Override
    public void returnSubmitResponse(UserInfo response) {
        if (response != null) {
            MyUtils.saveUserInfo(getActivity(), response);
            getActivity().finish();
        }
    }
}
