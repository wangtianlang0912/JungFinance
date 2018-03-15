package com.jung.finance.ui.user.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
 * @date 2018/3/15. 下午10:55
 *
 *
 */
public class BindMobileFragment extends BaseFragment {
    @Bind(R.id.old_pwd_edit)
    EditText oldPwdEdit;
    @Bind(R.id.sendsms_tv)
    TextView sendsmsTv;
    @Bind(R.id.sendsms_layout)
    LinearLayout sendsmsLayout;
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

    }

    @Override
    protected void initView() {

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

    @OnClick({R.id.sendsms_layout, R.id.save_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sendsms_layout:



                break;
            case R.id.save_btn:
                break;
        }
    }
}
