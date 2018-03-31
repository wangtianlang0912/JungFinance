package com.jung.finance.ui.main.presenter;


import com.jung.finance.R;
import com.jung.finance.bean.ActivityModel;
import com.jung.finance.ui.main.contract.ActivityContract;
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
 * @date 2018/3/31. 下午5:46
 *
 *
 */
public class ActivityPresenterImp extends ActivityContract.Presenter {
    @Override
    public void loadActivityList(int startPage) {
        mRxManage.add(mModel.lodeActivityList(startPage).subscribe(new RxSubscriber<ActivityModel>(mContext, false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(ActivityModel activityModel) {
                mView.returnActivityModel(activityModel);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}
