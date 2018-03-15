package com.jung.finance.ui.user.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
 * @date 2018/3/15. 下午10:57
 *
 *
 */
public class UpdateMobileFragment extends BaseFragment {
    @Bind(R.id.old_pwd_edit)
    EditText oldPwdEdit;
    @Bind(R.id.new_pwd_edit)
    EditText newPwdEdit;
    @Bind(R.id.confirm_pwd_edit)
    EditText confirmPwdEdit;
    @Bind(R.id.save_btn)
    Button saveBtn;

    @Override
    protected int getLayoutResource() {
        return R.layout.fra_update_pwd;
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

    @OnClick(R.id.save_btn)
    public void onViewClicked() {
    }
}
