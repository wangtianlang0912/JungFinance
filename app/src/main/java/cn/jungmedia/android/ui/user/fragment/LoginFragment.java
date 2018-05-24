package cn.jungmedia.android.ui.user.fragment;

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

import com.leon.common.base.BaseFragment;
import com.leon.common.basebean.BaseRespose;
import com.leon.common.commonutils.ToastUitl;
import com.leon.common.ui.counterButton.CounterButton;
import com.leon.common.ui.counterButton.VerificationInfo;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jungmedia.android.R;
import cn.jungmedia.android.app.AppApplication;
import cn.jungmedia.android.app.AppConstant;
import cn.jungmedia.android.app.AppIntent;
import cn.jungmedia.android.ui.user.bean.UserInfo;
import cn.jungmedia.android.ui.user.model.LoginModelImp;
import cn.jungmedia.android.ui.user.presenter.LoginPresenterImp;
import cn.jungmedia.android.ui.user.presenter.UserContract;
import cn.jungmedia.android.ui.user.utils.MulitEditUtils;
import cn.jungmedia.android.utils.MyUtils;
import cn.jungmedia.android.utils.PatternUtil;


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
    CounterButton sendsmsTv;
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
        AppApplication.setIsLoginPage(true);

        MulitEditUtils.associate(mobileEdit, mobileClearIv);
        MulitEditUtils.associate(pwdEdit, pwdClearIv);
        MulitEditUtils.associate(verifycodeEdit, verifycodeClearIv);

        sendsmsTv.setTimeCount(60000, 1000);
        sendsmsTv.setOnTimerCountClickListener(new CounterButton.OnTimerCountClickListener() {
            @Override
            public VerificationInfo onClick(View v) {
                sendVerifyCode();
                return null;
            }
        });
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

    @Override
    public void onResume() {
        super.onResume();

        if (MyUtils.isLogin()) {
            getActivity().finish();
        }
    }

    @OnClick({R.id.mobile_login_tv, R.id.account_login_tv, R.id.verifycode_clear_iv, R.id.mobile_clear_iv, R.id.pwd_clear_iv, R.id.login_btn, R.id.wechat_btn, R.id.register_btn, R.id.forget_pwd_btn})
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
                getActivity().finish();
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
        if (!PatternUtil.checkTelPhone2(phone)) {
            ToastUitl.showShort("请输入正确的手机号");
            return;
        }
        mPresenter.getVerifyCode(phone);
    }

    private void wechatLogin() {

        if (AppConstant.wx_api == null) {
            //创建微信api并注册到微信
            AppConstant.wx_api = WXAPIFactory.createWXAPI(getContext(), AppConstant.APP_ID, false);
            AppConstant.wx_api.registerApp(AppConstant.APP_ID);
        }
        if (!AppConstant.wx_api.isWXAppInstalled()) {
            ToastUitl.showShort("请安装微信客户端");
            return;
        }
        //发起登录请求
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = getContext().getPackageName();
        AppConstant.wx_api.sendReq(req);
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
        if (!PatternUtil.checkPassword(pwd)) {
            ToastUitl.showShort("密码应为6-24位字母或数字组合");
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
    public void returnVerifyCode(BaseRespose<String> result) {
        if (result.success()) {
            sendsmsTv.startTimer();
        } else {
            ToastUitl.showShort(result.msg != null ? result.msg : "验证码发送失败");
        }
    }

    @Override
    public void returnLoginResponse(BaseRespose<UserInfo> response) {
        if (sendsmsTv != null) {
            sendsmsTv.stopTimerCount();
        }
        if (response != null && response.data != null && response.data.getToken() != null) {
            MyUtils.saveUserInfo(getActivity(), response.data);
            MyUtils.saveToken(getActivity(), response.data.getToken());
            getActivity().finish();
        } else {
            showShortToast(response.msg);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppApplication.setIsLoginPage(false);

        if (sendsmsTv != null) {
            sendsmsTv.stopTimerCount();
        }
    }
}
