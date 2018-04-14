package com.jung.android.wxapi.presenter;

import com.jung.finance.R;
import com.jung.android.ui.user.bean.UserInfo;
import com.jung.android.wxapi.contract.WXContract;
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
 * @date 2018/4/12. 下午11:46
 *
 *
 */
public class WxPresenter extends WXContract.Presenter {
    @Override
    public void userLogin(String openid, String nick, String logo) {
        mRxManage.add(mModel.userLogin(openid, nick, logo).subscribe(new RxSubscriber<UserInfo>(mContext, false) {
            @Override
            protected void _onNext(UserInfo data) {
                mView.returnLoginData(data);
                mView.stopLoading();
            }

            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
