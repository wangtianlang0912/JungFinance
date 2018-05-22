package cn.jungmedia.android.ui.main.presenter;


import com.leon.common.basebean.BaseRespose;
import com.leon.common.baserx.RxSubscriber;

import cn.jungmedia.android.R;
import cn.jungmedia.android.app.AppApplication;
import cn.jungmedia.android.ui.main.contract.MineContract;
import cn.jungmedia.android.ui.user.bean.UserInfo;
import cn.jungmedia.android.utils.MyUtils;

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
        mRxManage.add(mModel.getUserInfo().subscribe(new RxSubscriber<BaseRespose<UserInfo>>(mContext, false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(BaseRespose<UserInfo> data) {

                if (!MyUtils.verifyToken(data)) {
                    AppApplication.getInvalidCallback().onTokenInvalid();
                    return;
                } else {
                    mView.returnUserInfoResponse(data.data);
                }
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
