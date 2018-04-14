package cn.jungmedia.android.ui.user.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import cn.jungmedia.android.utils.PatternUtil;
import cn.jungmedia.android.R;
import cn.jungmedia.android.ui.user.bean.UserInfo;
import cn.jungmedia.android.ui.user.model.ForgetPwdModelImp;
import cn.jungmedia.android.ui.user.presenter.ForgetPwdPresenterImp;
import cn.jungmedia.android.ui.user.presenter.UserContract;
import cn.jungmedia.android.ui.user.utils.MulitEditUtils;
import com.leon.common.base.BaseFragment;
import com.leon.common.basebean.BaseRespose;
import com.leon.common.commonutils.ToastUitl;
import com.leon.common.ui.counterButton.CounterButton;
import com.leon.common.ui.counterButton.VerificationInfo;

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
 * @date 2018/3/15. 下午11:50
 *
 *
 */
public class ForgetPwdFragment extends BaseFragment<ForgetPwdPresenterImp, ForgetPwdModelImp> implements UserContract.IForgetPwdView {
    @Bind(R.id.mobile_clear_iv)
    ImageView mobileClearIv;
    @Bind(R.id.mobile_edit)
    EditText mobileEdit;
    @Bind(R.id.sendsms_tv)
    CounterButton sendsmsTv;
    @Bind(R.id.verifycode_edit)
    EditText verifycodeEdit;
    @Bind(R.id.pwd_clear_iv)
    ImageView pwdClearIv;
    @Bind(R.id.pwd_edit)
    EditText pwdEdit;
    @Bind(R.id.confirm_pwd_clear_iv)
    ImageView confirmPwdClearIv;
    @Bind(R.id.confirm_pwd_edit)
    EditText confirmPwdEdit;
    @Bind(R.id.submit_btn)
    Button submitBtn;

    @Override
    protected int getLayoutResource() {
        return R.layout.fra_forgetpwd;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        MulitEditUtils.associate(mobileEdit, mobileClearIv);
        MulitEditUtils.associate(pwdEdit, pwdClearIv);
        MulitEditUtils.associate(confirmPwdEdit, confirmPwdClearIv);
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

    @OnClick({R.id.submit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit_btn:
                submit();
                break;
        }
    }

    private void submit() {

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
        String confirmPwd = confirmPwdEdit.getText().toString();
        if (!pwd.equals(confirmPwd)) {
            ToastUitl.showShort("请确认新密码");
            return;
        }
        mPresenter.submit(phone, code, pwd);
    }

    @Override
    public void returnVerifyCode(BaseRespose<String> result) {
        if (result.success()) {
            sendsmsTv.startTimer();
        } else {
            ToastUitl.showShort("验证码发送失败");

        }
    }

    @Override
    public void returnSubmitResponse(UserInfo response) {
        if (sendsmsTv != null) {
            sendsmsTv.stopTimerCount();
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
}
