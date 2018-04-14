package com.jung.android.ui.user.presenter;


import com.jung.android.ui.user.bean.UserInfo;
import com.jung.finance.R;
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
 * @date 2018/3/18. 下午3:18
 *
 *
 */
public class UserInfoPresenterImp extends UserContract.UserInfoPresenter {
    @Override
    public void submit(String nick, String desp, String phone, String logo) {
        mRxManage.add(mModel.submit(nick, desp, phone, logo).subscribe(new RxSubscriber<UserInfo>(mContext, false) {
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
