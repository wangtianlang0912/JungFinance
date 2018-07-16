package cn.jungmedia.android.ui.user.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.leon.common.base.BaseFragment;
import com.leon.common.basebean.BaseRespose;
import com.leon.common.commonutils.ToastUitl;
import com.leon.common.ui.counterButton.CounterButton;
import com.leon.common.ui.counterButton.VerificationInfo;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jungmedia.android.R;
import cn.jungmedia.android.app.AppIntent;
import cn.jungmedia.android.ui.user.bean.UserInfo;
import cn.jungmedia.android.ui.user.model.RegisterModelImp;
import cn.jungmedia.android.ui.user.presenter.RegisterPresenterImp;
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
 * @date 2018/3/15. 下午10:58
 *
 *
 */
public class RegisterFragment extends BaseFragment<RegisterPresenterImp, RegisterModelImp> implements UserContract.IRegisterView {
    @Bind(R.id.sendsms_tv)
    CounterButton sendsmsTv;
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

    @OnClick({R.id.verifycode_clear_iv, R.id.pwd_clear_iv, R.id.register_btn, R.id.go_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register_btn:

                register();
                break;
            case R.id.go_login:
                AppIntent.intentToLogin(getActivity());
                getActivity().finish();
                break;
            case R.id.pwd_clear_iv:
                pwdEdit.setText("");
                break;
            case R.id.verifycode_clear_iv:
                verifycodeEdit.setText("");
                break;
        }
    }

    private void register() {
        String phone = mobileEdit.getText().toString();
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

        String pwd = pwdEdit.getText().toString();
        if (TextUtils.isEmpty(pwd)) {
            ToastUitl.showShort("请输入密码");
            return;
        }
        if (!PatternUtil.checkPassword(pwd)) {
            ToastUitl.showShort("密码应为6-24位字母或数字组合");
            return;
        }
        mPresenter.register(phone, code, pwd);
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
    public void returnVerifyCode(BaseRespose<String> result) {
        if (result.success()) {
            sendsmsTv.startTimer();
        } else {
            ToastUitl.showShort(result.msg != null ? result.msg : "验证码发送失败");

        }
    }

    @Override
    public void returnRegisterResponse(BaseRespose<UserInfo> response) {
        if (sendsmsTv != null) {
            sendsmsTv.stopTimerCount();
        }
        if (response != null && response.data.getToken() != null) {
            MyUtils.saveUserInfo(getActivity(), response.data);
            MyUtils.saveToken(getActivity(), response.data.getToken());
            showShortToast(getString(R.string.register_success));
            getActivity().finish();
        } else {
            showShortToast(response != null ? response.msg : "注册失败");
        }

    }

    private void sendVerifyCode() {

        String phone = mobileEdit.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            ToastUitl.showShort("请输入手机号");
            return;
        }
        mPresenter.getVerifyCode(phone);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (sendsmsTv != null) {
            sendsmsTv.stopTimerCount();
        }
    }
}
