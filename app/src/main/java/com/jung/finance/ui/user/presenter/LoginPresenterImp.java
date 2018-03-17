package com.jung.finance.ui.user.presenter;

import com.jung.finance.R;
import com.jung.finance.ui.user.bean.UserInfo;
import com.jung.finance.ui.user.presenter.UserContract.LoginPresenter;
import com.leon.common.baserx.RxSubscriber;


/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/3/17. 下午7:26
 *
 *
 */
public class LoginPresenterImp extends LoginPresenter {
    @Override
    public void getVerifyCode(String phone) {
        mRxManage.add(mModel.getVerifyCode(phone).subscribe(new RxSubscriber<Boolean>(mContext, false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(Boolean data) {
                mView.returnVerifyCode(data);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void accountLogin(String phone, String pwd) {
        mRxManage.add(mModel.accountLogin(phone, pwd).subscribe(new RxSubscriber<UserInfo>(mContext, false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(UserInfo data) {
                mView.returnLoginResponse(data);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void mobileLogin(String phone, String code) {
        mRxManage.add(mModel.mobileLogin(phone, code).subscribe(new RxSubscriber<UserInfo>(mContext, false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(UserInfo data) {
                mView.returnLoginResponse(data);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
