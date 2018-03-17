package com.jung.finance.ui.user.fragment;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.jung.finance.ui.user.model.RegisterModelImp;
import com.jung.finance.ui.user.presenter.RegisterPresenterImp;
import com.jung.finance.ui.user.presenter.UserContract;
import com.jung.finance.ui.user.utils.MulitEditUtils;
import com.leon.common.base.BaseFragment;
import com.leon.common.commonutils.ToastUitl;

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
public class RegisterFragment extends BaseFragment<RegisterPresenterImp, RegisterModelImp> implements UserContract.IRegisterView {
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
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        MulitEditUtils.associate(verifycodeEdit, verifycodeClearIv);
        MulitEditUtils.associate(pwdEdit, pwdClearIv);

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

                sendVerifyCode();
                break;
            case R.id.register_btn:
                break;
            case R.id.go_login:
                AppIntent.intentToLogin(getActivity());
                getActivity().finish();
                break;
        }
    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {

    }

    @Override
    public void returnVerifyCode(String code) {


    }

    @Override
    public void returnRegisterResponse(String response) {

    }

    private void sendVerifyCode() {

        String phone = mobileEdit.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            ToastUitl.showShort("请输入手机号");
            return;
        }
        mPresenter.getVerifyCode(phone);
    }
}
