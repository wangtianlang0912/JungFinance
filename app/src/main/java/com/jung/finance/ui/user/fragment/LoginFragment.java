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
import com.jung.finance.ui.user.bean.UserInfo;
import com.jung.finance.ui.user.model.LoginModelImp;
import com.jung.finance.ui.user.presenter.LoginPresenterImp;
import com.jung.finance.ui.user.presenter.UserContract;
import com.jung.finance.ui.user.utils.MulitEditUtils;
import com.jung.finance.utils.MyUtils;
import com.jung.finance.utils.PatternUtil;
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
 * @date 2018/3/15. 下午10:57
 *
 *
 */
public class LoginFragment extends BaseFragment<LoginPresenterImp, LoginModelImp> implements UserContract.ILoginView {
    @Bind(R.id.mobile_login_tv)
    TextView mobileLoginTv;
    @Bind(R.id.account_login_tv)
    TextView accountLoginTv;
    @Bind(R.id.sendsms_tv)
    TextView sendsmsTv;
    @Bind(R.id.sendsms_layout)
    LinearLayout sendsmsLayout;
    @Bind(R.id.account_edit)
    EditText accountEdit;
    @Bind(R.id.verifycode_clear_iv)
    ImageView verifycodeClearIv;
    @Bind(R.id.verifycode_edit)
    EditText verifycodeEdit;
    @Bind(R.id.mobile_login_layout)
    LinearLayout mobileLoginLayout;
    @Bind(R.id.mobile_clear_iv)
    ImageView mobileClearIv;
    @Bind(R.id.mobile_edit)
    EditText mobileEdit;
    @Bind(R.id.pwd_clear_iv)
    ImageView pwdClearIv;
    @Bind(R.id.pwd_edit)
    EditText pwdEdit;
    @Bind(R.id.account_login_layout)
    LinearLayout accountLoginLayout;
    @Bind(R.id.login_btn)
    Button loginBtn;
    @Bind(R.id.wechat_btn)
    Button wechatBtn;
    @Bind(R.id.register_btn)
    TextView registerBtn;
    @Bind(R.id.forget_pwd_btn)
    TextView forgetPwdBtn;

    private boolean isAccountStatus;

    @Override
    protected int getLayoutResource() {
        return R.layout.fra_login;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        MulitEditUtils.associate(mobileEdit, mobileClearIv);
        MulitEditUtils.associate(pwdEdit, pwdClearIv);
        MulitEditUtils.associate(verifycodeEdit, verifycodeClearIv);
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

    @OnClick({R.id.mobile_login_tv, R.id.account_login_tv, R.id.sendsms_layout, R.id.verifycode_clear_iv, R.id.mobile_clear_iv, R.id.pwd_clear_iv, R.id.login_btn, R.id.wechat_btn, R.id.register_btn, R.id.forget_pwd_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mobile_login_tv:
                mobileLoginTv.setTextColor(getResources().getColor(R.color._f2a924));
                accountLoginTv.setTextColor(getResources().getColor(R.color._333333));
                accountLoginLayout.setVisibility(View.GONE);
                mobileLoginLayout.setVisibility(View.VISIBLE);
                isAccountStatus = false;

                break;
            case R.id.account_login_tv:
                accountLoginTv.setTextColor(getResources().getColor(R.color._f2a924));
                mobileLoginTv.setTextColor(getResources().getColor(R.color._333333));
                accountLoginLayout.setVisibility(View.VISIBLE);
                mobileLoginLayout.setVisibility(View.GONE);
                isAccountStatus = true;

                break;
            case R.id.sendsms_layout:
                sendVerifyCode();
                break;
            case R.id.login_btn:

                if (isAccountStatus) {
                    accountLogin();
                } else {
                    mobileLogin();
                }
                break;
            case R.id.wechat_btn:
                wechatLogin();
                break;
            case R.id.register_btn:
                AppIntent.intentToRegister(getActivity());
                break;
            case R.id.forget_pwd_btn:
                AppIntent.intentToForgetPwd(getActivity());
                break;
        }
    }

    private void sendVerifyCode() {

        String phone = accountEdit.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            ToastUitl.showShort("请输入手机号");
            return;
        }
        mPresenter.getVerifyCode(phone);

    }

    private void wechatLogin() {


    }

    private void mobileLogin() {
        String phone = accountEdit.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            ToastUitl.showShort("请输入手机号");
            return;
        }
        if (!PatternUtil.checkTelPhone2(phone)) {
            ToastUitl.showShort("请输入正确的手机号");
            return;
        }
        String code = verifycodeEdit.getText().toString();
        if (TextUtils.isEmpty(code)) {
            ToastUitl.showShort("请输入验证码");
            return;
        }
        mPresenter.mobileLogin(phone, code);
    }

    private void accountLogin() {

        String phone = mobileEdit.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            ToastUitl.showShort("请输入手机号");
            return;
        }
        if (!PatternUtil.checkTelPhone2(phone)) {
            ToastUitl.showShort("请输入正确的手机号");
            return;
        }
        String pwd = pwdEdit.getText().toString();
        if (TextUtils.isEmpty(pwd)) {
            ToastUitl.showShort("请输入密码");
            return;
        }

        mPresenter.accountLogin(phone, pwd);
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
    public void returnVerifyCode(boolean result) {
        ToastUitl.showShort(result ? "验证码已发送" : "验证码发送失败");
    }

    @Override
    public void returnLoginResponse(UserInfo response) {

        if (response != null && response.getToken() != null) {
            MyUtils.saveUserInfo(getActivity(), response);
        }

    }
}
