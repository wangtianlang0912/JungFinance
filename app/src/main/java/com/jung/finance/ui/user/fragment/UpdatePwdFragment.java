package com.jung.finance.ui.user.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.jung.finance.R;
import com.jung.finance.ui.user.model.UpdatePwdModelImp;
import com.jung.finance.ui.user.presenter.UpdatePwdPresenterImp;
import com.jung.finance.ui.user.presenter.UserContract;
import com.jung.finance.utils.PatternUtil;
import com.leon.common.base.BaseFragment;
import com.leon.common.basebean.BaseRespose;
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
public class UpdatePwdFragment extends BaseFragment<UpdatePwdPresenterImp, UpdatePwdModelImp> implements UserContract.IUpdatePwdView {
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
        mPresenter.setVM(this, mModel);
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

        String oldPwd = oldPwdEdit.getText().toString();
        if (TextUtils.isEmpty(oldPwd)) {
            ToastUitl.showShort("请输入原密码");
            return;
        }
        if (!PatternUtil.checkPassword(oldPwd)) {
            ToastUitl.showShort("密码应为6-24位字母或数字组合");
            return;
        }

        String newPwd = newPwdEdit.getText().toString();
        if (TextUtils.isEmpty(newPwd)) {
            ToastUitl.showShort("请输入新密码");
            return;
        }
        if (!PatternUtil.checkPassword(oldPwd)) {
            ToastUitl.showShort("密码应为6-24位字母或数字组合");
            return;
        }
        String confirmPwd = confirmPwdEdit.getText().toString();
        if (!newPwd.equals(confirmPwd)) {
            ToastUitl.showShort("请确认新密码");
            return;
        }
        mPresenter.submit(oldPwd, newPwd);
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
    public void returnSubmitResponse(BaseRespose response) {
        if (response.success()) {
            ToastUitl.showShort(R.string.update_pwd_succ);
            getActivity().finish();
        } else {
            ToastUitl.showShort(response.msg);
        }
    }
}
