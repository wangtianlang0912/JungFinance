package cn.jungmedia.android.ui.user.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.leon.common.base.BaseFragment;
import com.leon.common.basebean.BaseRespose;
import com.leon.common.commonutils.ToastUitl;
import com.leon.common.ui.counterButton.CounterButton;
import com.leon.common.ui.counterButton.VerificationInfo;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jungmedia.android.R;
import cn.jungmedia.android.ui.user.model.BindMobileModelImp;
import cn.jungmedia.android.ui.user.presenter.BindMobilePresenterImp;
import cn.jungmedia.android.ui.user.presenter.UserContract;
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
 * @date 2018/3/15. 下午10:55
 *
 *
 */
public class BindMobileFragment extends BaseFragment<BindMobilePresenterImp, BindMobileModelImp> implements UserContract.IBindMobileView {
    @Bind(R.id.old_pwd_edit)
    EditText oldPwdEdit;
    @Bind(R.id.sendsms_tv)
    CounterButton sendsmsTv;
    @Bind(R.id.mobile_edit)
    EditText mobileEdit;
    @Bind(R.id.verifycode_edit)
    EditText verifycodeEdit;
    @Bind(R.id.save_btn)
    Button saveBtn;

    @Override
    protected int getLayoutResource() {
        return R.layout.fra_bind_mobile;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {

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

    @OnClick({R.id.save_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.save_btn:

                submit();
                break;
        }
    }

    private void submit() {
        String pwd = oldPwdEdit.getText().toString();
        if (TextUtils.isEmpty(pwd)) {
            ToastUitl.showShort("请输入密码");
            return;
        }
        if (!PatternUtil.checkPassword(pwd)) {
            ToastUitl.showShort("密码应为6-24位字母或数字组合");
            return;
        }
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
        mPresenter.submit(phone, code, pwd);
    }


    private void sendVerifyCode() {
        String phone = mobileEdit.getText().toString();
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
    public void returnSubmitResponse(BaseRespose response) {
        if (sendsmsTv != null) {
            sendsmsTv.stopTimerCount();
        }
        if (response.success()) {
            ToastUitl.showShort(R.string.update_pwd_succ);
            getActivity().finish();
        } else {
            ToastUitl.showShort(response.msg);
        }
    }
}
