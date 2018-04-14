package com.jung.android.ui.main.presenter;


import com.jung.android.ui.main.contract.MineContract;
import com.jung.finance.R;
import com.jung.android.ui.user.bean.UserInfo;
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
 * @date 2018/3/18. 下午2:35
 *
 *
 */
public class MinePresenterImp extends MineContract.MinePresenter {
    @Override
    public void getUserInfo() {
        mRxManage.add(mModel.getUserInfo().subscribe(new RxSubscriber<UserInfo>(mContext, false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(UserInfo data) {
                mView.returnUserInfoResponse(data);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

//    @Override
//    public void getScoreInfo() {
//        mRxManage.add(mModel.getScoreInfo().subscribe(new RxSubscriber<ScoreInfo>(mContext, false) {
//            @Override
//            public void onStart() {
//                super.onStart();
//                mView.showLoading(mContext.getString(R.string.loading));
//            }
//
//            @Override
//            protected void _onNext(ScoreInfo data) {
//                mView.returnScoreInfoResponse(data);
//                mView.stopLoading();
//            }
//
//            @Override
//            protected void _onError(String message) {
//                mView.showErrorTip(message);
//            }
//        }));
//    }
}
