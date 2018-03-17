package com.jung.finance.ui.user.presenter;


import com.jung.finance.R;
import com.jung.finance.ui.user.bean.UserInfo;
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
 * @date 2018/3/18. 上午12:17
 *
 *
 */
public class UpdatePwdPresenterImp extends UserContract.UpdatePwdPresenter {
    @Override
    public void submit(String oldPwd, String newPwd) {
        mRxManage.add(mModel.submit(oldPwd, newPwd).subscribe(new RxSubscriber<UserInfo>(mContext, false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(UserInfo data) {
                mView.returnSubmitResponse(data);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }


}
