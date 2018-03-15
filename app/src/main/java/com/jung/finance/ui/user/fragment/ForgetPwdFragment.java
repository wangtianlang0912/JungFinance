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
 * @date 2018/3/15. 下午11:50
 *
 *
 */
public class ForgetPwdFragment extends BaseFragment {
    @Bind(R.id.mobile_clear_iv)
    ImageView mobileClearIv;
    @Bind(R.id.mobile_edit)
    EditText mobileEdit;
    @Bind(R.id.sendsms_tv)
    TextView sendsmsTv;
    @Bind(R.id.sendsms_layout)
    LinearLayout sendsmsLayout;
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

    @OnClick({R.id.mobile_clear_iv, R.id.sendsms_layout, R.id.pwd_clear_iv, R.id.confirm_pwd_clear_iv, R.id.submit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mobile_clear_iv:
                break;
            case R.id.sendsms_layout:
                break;
            case R.id.pwd_clear_iv:
                break;
            case R.id.confirm_pwd_clear_iv:
                break;
            case R.id.submit_btn:
                break;
        }
    }
}
