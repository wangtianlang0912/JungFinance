package com.jung.finance.ui.user.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
 * @TODO 用户信息页面
 *
 * @author niufei
 *
 *
 * @date 2018/3/12. 下午11:02
 *
 *
 */
public class UserInfoFragment extends BaseFragment {


    @Bind(R.id.logo_view)
    ImageView logoView;
    @Bind(R.id.nick_text)
    TextView nickText;
    @Bind(R.id.nick_edit)
    EditText nickEdit;
    @Bind(R.id.desp_text)
    TextView despText;
    @Bind(R.id.desp_edit)
    EditText despEdit;
    @Bind(R.id.submit_btn)
    Button submitBtn;

    @Override
    protected int getLayoutResource() {
        return R.layout.fra_userinfo;
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

    @OnClick(R.id.submit_btn)
    public void onViewClicked() {

    }
}
