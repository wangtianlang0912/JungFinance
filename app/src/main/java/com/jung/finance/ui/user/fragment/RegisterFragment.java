package com.jung.finance.ui.user.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jung.finance.R;
import com.jung.finance.app.AppIntent;
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
 * @date 2018/3/15. 下午10:58
 *
 *
 */
public class RegisterFragment extends BaseFragment {
    @Bind(R.id.sendsms_tv)
    TextView sendsmsTv;
    @Bind(R.id.sendsms_layout)
    LinearLayout sendsmsLayout;
    @Bind(R.id.mobile_edit)
    EditText mobileEdit;
    @Bind(R.id.verifycode_clear_iv)
    ImageView verifycodeClearIv;
    @Bind(R.id.verifycode_edit)
    EditText verifycodeEdit;
    @Bind(R.id.pwd_clear_iv)
    ImageView pwdClearIv;
    @Bind(R.id.pwd_edit)
    EditText pwdEdit;
    @Bind(R.id.register_btn)
    Button registerBtn;
    @Bind(R.id.go_login)
    TextView goLogin;

    @Override
    protected int getLayoutResource() {
        return R.layout.fra_register;
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

    @OnClick({R.id.sendsms_layout, R.id.verifycode_clear_iv, R.id.pwd_clear_iv, R.id.register_btn, R.id.go_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sendsms_layout:
                break;
            case R.id.verifycode_clear_iv:
                break;
            case R.id.pwd_clear_iv:
                break;
            case R.id.register_btn:
                break;
            case R.id.go_login:
                AppIntent.intentToLogin(getActivity());
                getActivity().finish();
                break;
        }
    }
}
