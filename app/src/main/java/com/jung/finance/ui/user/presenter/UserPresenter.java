package com.jung.finance.ui.user.presenter;

import com.jung.finance.R;
import com.leon.common.baserx.RxSubscriber;

/**
 * des:图片列表
 * Created by xsf
 * on 2016.09.12:01
 */
public class UserPresenter extends UserContract.RegisterPresenter {

    @Override
    public void getVerifyCode(String phone) {
        mRxManage.add(mModel.getVerifyCode(phone).subscribe(new RxSubscriber<String>(mContext, false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(String data) {
                mView.returnVerifyCode(data);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
