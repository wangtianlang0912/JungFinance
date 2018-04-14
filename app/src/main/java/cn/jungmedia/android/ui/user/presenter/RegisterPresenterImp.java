package com.jung.android.ui.user.presenter;

import com.jung.finance.R;
import com.jung.android.ui.user.bean.UserInfo;
import com.leon.common.basebean.BaseRespose;
import com.leon.common.baserx.RxSubscriber;

public class RegisterPresenterImp extends UserContract.RegisterPresenter {

    @Override
    public void getVerifyCode(String phone) {
        mRxManage.add(mModel.getVerifyCode(phone).subscribe(new RxSubscriber<BaseRespose<String>>(mContext, false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(BaseRespose<String> data) {
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
    public void register(String phone, String code, String pwd) {

        mRxManage.add(mModel.register(phone, code, pwd).subscribe(new RxSubscriber<UserInfo>(mContext, false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(UserInfo data) {
                mView.returnRegisterResponse(data);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
